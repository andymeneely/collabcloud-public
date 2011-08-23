package org.chaoticbits.collabcloud.vc.git;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.LogCommand;
import org.eclipse.jgit.diff.DiffFormatter;
import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.errors.StopWalkException;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.revwalk.filter.RevFilter;
import org.eclipse.jgit.storage.file.FileRepository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.junit.Test;

public class GetDevelopersTest {

	private static final int SECONDS_PER_DAY = 86400;
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

	@Test
	public void lastTwoDaysRevWalk() throws Exception {
		FileRepository repo = new FileRepositoryBuilder().setGitDir(GIT_DIR).readEnvironment().findGitDir().build();
		ObjectId until = repo.resolve("HEAD");

		RevWalk rw = new RevWalk(repo);
		rw.markStart(rw.parseCommit(until));
		rw.setRevFilter(new RevFilter() {
			@Override
			public boolean include(RevWalk walker, RevCommit cmit) throws StopWalkException, MissingObjectException,
					IncorrectObjectTypeException, IOException {
				int commitTime = cmit.getCommitTime();
				if (commitTime == 1313673905)
					return false;
				long since = System.currentTimeMillis() / 1000 - commitTime;
				return since < SECONDS_PER_DAY * 2;
			}

			@Override
			public RevFilter clone() {
				return this;
			}
		});
		// for (RevCommit revCommit : rw) {
		// System.out.println(revCommit.getCommitTime() + "\t" + revCommit.getFullMessage());
		// }
	}

	@Test
	public void printDiff() throws Exception {
		FileRepository repo = new FileRepositoryBuilder().setGitDir(GIT_DIR).readEnvironment().findGitDir().build();
		ObjectId until = repo.resolve("HEAD");
		RevCommit headCommit = new RevWalk(repo).parseCommit(until);
		DiffFormatter df = new DiffFormatter(System.out);
		df.setRepository(repo);
		df.format(headCommit.getParent(0), headCommit);
		System.out.flush();
	}
}
