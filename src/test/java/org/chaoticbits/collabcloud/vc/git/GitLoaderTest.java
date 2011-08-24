package org.chaoticbits.collabcloud.vc.git;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.Set;

import org.chaoticbits.collabcloud.Developer;
import org.chaoticbits.collabcloud.codeprocessor.CloudWeights;
import org.chaoticbits.collabcloud.codeprocessor.ISummarizable;
import org.chaoticbits.collabcloud.codeprocessor.java.JavaClassArtifact;
import org.eclipse.jgit.lib.ObjectId;
import org.junit.Test;

public class GitLoaderTest {

	public static final String SECOND_COMMIT_ID = "bac7225dfb6ce2eb84c38f019defad21197514b6";

	private static final File GIT_DIR = new File("testgitrepo/.git");

	@Test
	public void allThreeDevs() throws Exception {
		GitLoader gitLoader = new GitLoader(GIT_DIR);
		ObjectId since = gitLoader.getRepo().resolve(SECOND_COMMIT_ID);
		gitLoader.markSince(since);
		Set<Developer> developers = gitLoader.getDevelopers();
		assertTrue(developers.contains(new Developer("Andy Meneely", "andy.meneely@gmail.com")));
		assertTrue(developers.contains(new Developer("Andy Programmer", "apmeneel@ncsu.edu")));
		assertTrue(developers.contains(new Developer("Kelly Doctor", "andy@se.rit.edu")));
		assertEquals("Only 3 developers", 3, developers.size());
	}

	@Test
	public void bunchOfFiles() throws Exception {
		GitLoader gitLoader = new GitLoader(GIT_DIR);
		ObjectId since = gitLoader.getRepo().resolve(SECOND_COMMIT_ID);
		gitLoader.markSince(since);
		Set<ISummarizable> artifacts = gitLoader.getFilesChanged();
		assertTrue(artifacts.contains(new JavaClassArtifact(new File("mancala/player/TimedNegaScoutPlayer.java"))));
		assertTrue(artifacts.contains(new JavaClassArtifact(new File("mancala/player/GreedyPlayer.java"))));
		assertEquals("Only 2 files changed", 2, artifacts.size());
	}

	@Test
	public void updateCloudWeights() throws Exception {
		CloudWeights weights = new CloudWeights(); // Not the real cloud weights - contrived
		weights.increment("TimedNegaScoutPlayer", 1.0);
		weights.increment("GreedyPlayer", 1.0);
		weights.increment("getPlay", 1.0); // Is in the diff
		weights.increment("setLog", 1.0); // In the file, not the diff
		weights.increment("play", 0.0); // In the file, not the cloud summary, and in the diff

		GitLoader gitLoader = new GitLoader(GIT_DIR);
		ObjectId since = gitLoader.getRepo().resolve(SECOND_COMMIT_ID);
		gitLoader.markSince(since);

		gitLoader.crossWithDiff(weights);
		assertEquals("getPlay was hit a lot", 128.0, weights.get("getPlay"), 0.0001);
		assertEquals("setLot was not hit at all", 1.0, weights.get("setLog"), 0.0001);
		assertEquals("play was not there", 0.0, weights.get("play"), 0.0001);
		assertEquals("GreedyPlayer was there", 64.0, weights.get("GreedyPlayer"), 0.0001);
		assertEquals("TimedNegaScoutPlayer was there", 512.0, weights.get("TimedNegaScoutPlayer"), 0.0001);
	}

	// private static final int SECONDS_PER_DAY = 86400;
	// @Test
	// public void getLastDeveloperThisRepo() throws Exception {
	// FileRepositoryBuilder builder = new FileRepositoryBuilder();
	// FileRepository repo = builder.setGitDir(GIT_DIR).readEnvironment().findGitDir().build();
	// Git git = new Git(repo);
	// LogCommand log = git.log();
	// Iterable<RevCommit> revs = log.call();
	// RevCommit head = revs.iterator().next();
	// String email = head.getAuthorIdent().getEmailAddress();
	// assertEquals("andy.meneely@gmail.com", email);
	// }
	//
	// @Test
	// public void lastTwoDaysRevWalk() throws Exception {
	// FileRepository repo = new
	// FileRepositoryBuilder().setGitDir(GIT_DIR).readEnvironment().findGitDir().build();
	// ObjectId until = repo.resolve("HEAD");
	//
	// RevWalk rw = new RevWalk(repo);
	// rw.markStart(rw.parseCommit(until));
	// rw.setRevFilter(new RevFilter() {
	// @Override
	// public boolean include(RevWalk walker, RevCommit cmit) throws StopWalkException,
	// MissingObjectException,
	// IncorrectObjectTypeException, IOException {
	// int commitTime = cmit.getCommitTime();
	// if (commitTime == 1313673905)
	// return false;
	// long since = System.currentTimeMillis() / 1000 - commitTime;
	// return since < SECONDS_PER_DAY * 2;
	// }
	//
	// @Override
	// public RevFilter clone() {
	// return this;
	// }
	// });
	// // for (RevCommit revCommit : rw) {
	// // System.out.println(revCommit.getCommitTime() + "\t" + revCommit.getFullMessage());
	// // }
	// }
	//
	// @Test
	// public void printDiff() throws Exception {
	// FileRepository repo = new
	// FileRepositoryBuilder().setGitDir(GIT_DIR).readEnvironment().findGitDir().build();
	// ObjectId until = repo.resolve("HEAD");
	// RevCommit headCommit = new RevWalk(repo).parseCommit(until);
	// DiffFormatter df = new DiffFormatter(System.out);
	// df.setRepository(repo);
	// df.format(headCommit.getParent(0), headCommit);
	// System.out.flush();
	// }
}
