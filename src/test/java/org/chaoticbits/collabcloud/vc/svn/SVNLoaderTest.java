package org.chaoticbits.collabcloud.vc.svn;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.Set;

import org.chaoticbits.collabcloud.ISummarizable;
import org.chaoticbits.collabcloud.codeprocessor.java.JavaClassSummarizable;
import org.chaoticbits.collabcloud.codeprocessor.java.JavaSummaryToken;
import org.chaoticbits.collabcloud.vc.Developer;
import org.junit.Test;

public class SVNLoaderTest {

	public static final String TESTSVN_REPO = "testsvn/repo";

	private JavaSummaryToken timedNegaScout = new JavaSummaryToken(null, "", "TimedNegaScoutPlayer", null);
	private JavaSummaryToken greedyPlayer = new JavaSummaryToken(null, "", "GreedyPlayer", null);
	private JavaSummaryToken getPlay = new JavaSummaryToken(null, "", "getPlay", null);
	private JavaSummaryToken setLog = new JavaSummaryToken(null, "", "setLog", null);
	private JavaSummaryToken play = new JavaSummaryToken(null, "", "play", null);

	@Test
	public void allThreeDevs() throws Exception {
		SVNLoader gitLoader = new SVNLoader(new File(TESTSVN_REPO), "/trunk/", 1L, 6L);
		Set<Developer> developers = gitLoader.getDevelopers();
		assertEquals("Only 3 developers", 3, developers.size());
		assertTrue("Contains Andy Meneely", developers.contains(new Developer("Andy Meneely", "andy.meneely@gmail.com")));
		assertTrue("Contains Andy Programmer", developers.contains(new Developer("Andy Programmer", "apmeneel@ncsu.edu")));
		assertTrue("Contains Kelly Doctor", developers.contains(new Developer("Kelly Doctor", "andy@se.rit.edu")));
	}

	@Test
	public void bunchOfFiles() throws Exception {
		SVNLoader svnLoader = new SVNLoader(new File(TESTSVN_REPO), "/trunk/", 3L, 6L);
		Set<ISummarizable> artifacts = svnLoader.getFilesChanged();
		assertEquals("Only 2 files changed", 2, artifacts.size());
		assertTrue(artifacts.contains(new JavaClassSummarizable(new File("mancala/player/TimedNegaScoutPlayer.java"))));
		assertTrue(artifacts.contains(new JavaClassSummarizable(new File("mancala/player/GreedyPlayer.java"))));
	}
	//
	// @Test
	// public void updateCloudWeightsMultiply() throws Exception {
	// CloudWeights weights = new CloudWeights(); // Not the real cloud weights - contrived
	// weights.put(timedNegaScout, 1.0);
	// weights.put(greedyPlayer, 1.0);
	// weights.put(getPlay, 1.0); // Is in the diff
	// weights.put(setLog, 1.0); // In the file, not the diff
	// weights.put(play, 0.0); // In the file, not the cloud summary, and in the diff
	//
	// GitLoader gitLoader = new GitLoader(GIT_DIR);
	// ObjectId since = gitLoader.getRepo().resolve(SECOND_COMMIT_ID);
	// gitLoader.markSince(since);
	//
	// gitLoader.crossWithDiff(weights, new MultiplyModifier(2.0));
	// assertEquals("TimedNegaScoutPlayer was there", 8.0, weights.get(timedNegaScout), 0.0001);
	// assertEquals("GreedyPlayer was there", 4.0, weights.get(greedyPlayer), 0.0001);
	// assertEquals("getPlay was hit a lot", 128.0, weights.get(getPlay), 0.0001);
	// assertEquals("setLot was not hit at all", 1.0, weights.get(setLog), 0.0001);
	// assertEquals("play was not there", 0.0, weights.get(play), 0.0001);
	// }
	//
	// @Test
	// public void updateCloudWeightsIncrements() throws Exception {
	// CloudWeights weights = new CloudWeights(); // Not the real cloud weights - contrived
	// weights.put(timedNegaScout, 1.0);
	// weights.put(greedyPlayer, 1.0);
	// weights.put(getPlay, 1.0); // Is in the diff
	// weights.put(setLog, 1.0); // In the file, not the diff
	// // no play...
	//
	// GitLoader gitLoader = new GitLoader(GIT_DIR);
	// ObjectId since = gitLoader.getRepo().resolve(SECOND_COMMIT_ID);
	// gitLoader.markSince(since);
	//
	// gitLoader.crossWithDiff(weights, new IncrementModifier(1.0));
	// assertEquals("TimedNegaScoutPlayer was there", 4.0, weights.get(timedNegaScout), 0.0001);
	// assertEquals("GreedyPlayer was there", 3.0, weights.get(greedyPlayer), 0.0001);
	// assertEquals("setLot was not hit at all", 1.0, weights.get(setLog), 0.0001);
	// assertEquals("getPlay was hit a lot", 8.0, weights.get(getPlay), 0.0001);
	// assertEquals("play was only in the diff, not the cloud summary", 0.0, weights.get(play), 0.0001);
	// }
	//
	// @Test
	// public void getFileContribution() throws Exception {
	// GitLoader gitLoader = new GitLoader(GIT_DIR);
	// ObjectId since = gitLoader.getRepo().resolve(SECOND_COMMIT_ID);
	// gitLoader.markSince(since);
	// Developer andy = new Developer("Andy Meneely", "andy.meneely@gmail.com");
	// JavaClassSummarizable greedy = new JavaClassSummarizable(new
	// File("mancala/player/GreedyPlayer.java"));
	//
	// assertTrue("has Andy Meneely", gitLoader.getDevelopers().contains(andy));
	// assertTrue("has GreedyPlayer.java", gitLoader.getFilesChanged().contains(greedy));
	// Set<ISummarizable> map = gitLoader.getFilesContributed(andy);
	// assertTrue("Andy Meneely worked on GreedyPlayer.java", map.contains(greedy));
	// }
	//
	// @Test
	// public void getOnlyFileContribution() throws Exception {
	// GitLoader gitLoader = new GitLoader(GIT_DIR);
	// ObjectId since = gitLoader.getRepo().resolve(SECOND_COMMIT_ID);
	// gitLoader.markSince(since);
	// Developer kelly = new Developer("Kelly Doctor", "andy@se.rit.edu");
	// JavaClassSummarizable negaScout = new JavaClassSummarizable(new
	// File("mancala/player/TimedNegaScoutPlayer.java"));
	//
	// assertTrue("has Kelly", gitLoader.getDevelopers().contains(kelly));
	// assertTrue("has TimedNegaScoutPlayer.java", gitLoader.getFilesContributed(kelly).contains(negaScout));
	// assertEquals("Kelly contributed to only one file", 1,gitLoader.getFilesContributed(kelly).size());
	// }
}
