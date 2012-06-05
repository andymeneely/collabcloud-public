package org.chaoticbits.collabcloud.vc.git;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.Set;

import org.chaoticbits.collabcloud.CloudWeights;
import org.chaoticbits.collabcloud.ISummarizable;
import org.chaoticbits.collabcloud.ISummaryToken;
import org.chaoticbits.collabcloud.codeprocessor.java.JavaClassSummarizable;
import org.chaoticbits.collabcloud.vc.Developer;
import org.chaoticbits.collabcloud.vc.DiffToken;
import org.eclipse.jgit.lib.ObjectId;
import org.junit.Test;

public class GitLoaderTest {

	public static final String SECOND_COMMIT_ID = "bac7225dfb6ce2eb84c38f019defad21197514b6";

	public static final File GIT_DIR = new File("testgitrepo/.git");

	private DiffToken timedNegaScout = new DiffToken(new JavaClassSummarizable(new File("mancala/player/TimedNegaScoutPlayer.java")),
			"TimedNegaScoutPlayer", "");
	private DiffToken greedyPlayer = new DiffToken(new JavaClassSummarizable(new File("mancala/player/GreedyPlayer.java")), "GreedyPlayer", "");
	private DiffToken getPlay = new DiffToken(new JavaClassSummarizable(new File("mancala/player/TimedNegaScoutPlayer.java")), "getPlay", "");
	private DiffToken setLog = new DiffToken(null, "setLog", "");
	private DiffToken play = new DiffToken(new JavaClassSummarizable(new File("mancala/player/GreedyPlayer.java")), "play", "");

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
		assertTrue(artifacts.contains(new JavaClassSummarizable(new File("mancala/player/TimedNegaScoutPlayer.java"))));
		assertTrue(artifacts.contains(new JavaClassSummarizable(new File("mancala/player/GreedyPlayer.java"))));
		assertEquals("Only 2 files changed", 2, artifacts.size());
	}

	@Test
	public void pullsCloudWeights() throws Exception {
		GitLoader gitLoader = new GitLoader(GIT_DIR);
		ObjectId since = gitLoader.getRepo().resolve(SECOND_COMMIT_ID);
		gitLoader.markSince(since);

		CloudWeights weights = gitLoader.getCloudWeights();
		// the timedNegaScout cloud weight here is null!! why!?!?!?!
		ISummaryToken next = weights.tokens().iterator().next();
		next.getParentSummarizable();
		assertNotNull("Token " + next + " should not have a null parent", next.getParentSummarizable());
		assertEquals(5.0, weights.get(getPlay), 0.001);
		assertEquals(0.0, weights.get(setLog), 0.001);
		assertEquals(17.0, weights.get(play), 0.001);
		assertEquals(2.0, weights.get(greedyPlayer), 0.001);
		assertEquals(3.0, weights.get(timedNegaScout), 0.001);
	}

	@Test
	public void getFileContribution() throws Exception {
		GitLoader gitLoader = new GitLoader(GIT_DIR);
		ObjectId since = gitLoader.getRepo().resolve(SECOND_COMMIT_ID);
		gitLoader.markSince(since);
		Developer andy = new Developer("Andy Meneely", "andy.meneely@gmail.com");
		JavaClassSummarizable greedy = new JavaClassSummarizable(new File("mancala/player/GreedyPlayer.java"));

		assertTrue("has Andy Meneely", gitLoader.getDevelopers().contains(andy));
		assertTrue("has GreedyPlayer.java", gitLoader.getFilesChanged().contains(greedy));
		Set<ISummarizable> map = gitLoader.getFilesContributed(andy);
		assertTrue("Andy Meneely worked on GreedyPlayer.java", map.contains(greedy));
	}

	@Test
	public void getOnlyFileContribution() throws Exception {
		GitLoader gitLoader = new GitLoader(GIT_DIR);
		ObjectId since = gitLoader.getRepo().resolve(SECOND_COMMIT_ID);
		gitLoader.markSince(since);
		Developer kelly = new Developer("Kelly Doctor", "andy@se.rit.edu");
		JavaClassSummarizable negaScout = new JavaClassSummarizable(new File("mancala/player/TimedNegaScoutPlayer.java"));

		assertTrue("has Kelly", gitLoader.getDevelopers().contains(kelly));
		assertTrue("has TimedNegaScoutPlayer.java", gitLoader.getFilesContributed(kelly).contains(negaScout));
		assertEquals("Kelly contributed to only one file", 1, gitLoader.getFilesContributed(kelly).size());
	}
}
