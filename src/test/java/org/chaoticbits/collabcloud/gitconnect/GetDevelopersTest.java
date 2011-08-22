package org.chaoticbits.collabcloud.gitconnect;

import static org.junit.Assert.assertEquals;

import java.io.File;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.LogCommand;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.storage.file.FileRepository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.junit.Test;

public class GetDevelopersTest {

	private static final File GIT_DIR = new File("c:/Users/andy.SE/workspaces/workspace/CollabCloud/.git");

	@Test
	public void getLastDeveloperThisRepo() throws Exception {
		FileRepositoryBuilder builder = new FileRepositoryBuilder();
		FileRepository repo = builder.setGitDir(GIT_DIR).readEnvironment().findGitDir().build();
		Git git = new Git(repo);
		LogCommand log = git.log();
		Iterable<RevCommit> revs = log.call();
		RevCommit head = revs.iterator().next();
		String email = head.getAuthorIdent().getEmailAddress();
		assertEquals("andy.meneely@gmail.com", email);
	}
}
