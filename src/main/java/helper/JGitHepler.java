package helper;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.ListBranchCommand;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;

import java.io.IOException;
import java.util.List;

/**
 * @Author: Allen Chen
 * @ModuleOwner: Allen Chen
 * @Date:03/28/2022 16:43
 * @Description:
 */
public class JGitHepler {
    public static Repository openJGitCookbookRepository() throws IOException {
        FileRepositoryBuilder builder = new FileRepositoryBuilder();
        return builder
                .readEnvironment() // scan environment GIT_* variables
                .findGitDir() // scan up the file system tree
                .build();
    }

    public static void main(String[] args) throws IOException {
        try (Repository repository = JGitHepler.openJGitCookbookRepository()) {
            try (Git git = new Git(repository)) {
                System.out.println("Listing local branches:");
                List<Ref> call = git.branchList().call();
                for (Ref ref : call) {
                    System.out.println("Branch: " + ref + " " + ref.getName() + " " + ref.getObjectId().getName());
                }

                System.out.println("Now including remote branches:");
                call = git.branchList().setListMode(ListBranchCommand.ListMode.ALL).call();
                for (Ref ref : call) {
                    System.out.println("Branch: " + ref + " " + ref.getName() + " " + ref.getObjectId().getName());
                }
            } catch (GitAPIException e) {
                e.printStackTrace();
            }
        }
    }
}
