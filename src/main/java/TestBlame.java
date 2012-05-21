import java.io.File;
import java.io.IOException;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.blame.BlameResult;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.PersonIdent;
import org.eclipse.jgit.storage.file.FileRepository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;

public class TestBlame {
	public static void main(String[] args) throws IOException {
		FileRepository repo = new FileRepositoryBuilder()
				.setGitDir(new File("c:/local/data/php/php-src/.git"))
				.readEnvironment().findGitDir().build();
		Git git = new Git(repo);
		ObjectId head = repo.resolve(Constants.HEAD);
		BlameResult blameResult = git.blame()
				.setStartCommit(head)
				.setFilePath("ext/standard/array.c").call();
		PersonIdent sourceAuthor = blameResult.getSourceAuthor(89);
		PersonIdent sourceCommitter = blameResult.getSourceCommitter(89);
		System.out.println(sourceAuthor);
		System.out.println(sourceCommitter);
	}
}
