package org.chaoticbits.collabcloud.vc.git;

import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.junit.Assert.assertEquals;

import java.util.Collections;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.chaoticbits.collabcloud.CloudWeights;
import org.chaoticbits.collabcloud.ISummarizable;
import org.chaoticbits.collabcloud.ISummaryToken;
import org.chaoticbits.collabcloud.codeprocessor.java.JavaSummaryToken;
import org.chaoticbits.collabcloud.vc.Developer;
import org.chaoticbits.collabcloud.vc.DiffParser;
import org.chaoticbits.collabcloud.vc.DiffToken;
import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class GitDiffParserTest {

	private final IMocksControl ctrl = EasyMock.createControl();
	private CloudWeights weights = ctrl.createMock(CloudWeights.class);
	@SuppressWarnings("unchecked")
	private Entry<ISummaryToken, Double> entry = ctrl.createMock(Entry.class);
	@SuppressWarnings("unchecked")
	private Map<Developer, Set<ISummarizable>> contributions = ctrl.createMock(Map.class);
	private Developer dev = ctrl.createMock(Developer.class);
	private ISummarizable summarizable = ctrl.createMock(ISummarizable.class);
	private DiffParser parser;

	@Before
	public void resetMocks() {
		parser = new DiffParser();
		ctrl.reset();
	}

	@After
	public void verifyMocks() {
		ctrl.verify();
	}

	@Test
	public void skipsDiffLine() throws Exception {
		ctrl.replay();

		DiffParser parser = new DiffParser();
		parser.processTextLine("diff", weights, contributions, dev, summarizable);
	}

	@Test
	public void skipsIndexLine() throws Exception {
		ctrl.replay();

		DiffParser parser = new DiffParser();
		parser.processTextLine("index", weights, contributions, dev, summarizable);
	}

	@Test
	public void skipsAtLines() throws Exception {
		ctrl.replay();

		DiffParser parser = new DiffParser();
		parser.processTextLine("@@", weights, contributions, dev, summarizable);
	}

	@Test
	public void skipsAllHeaderLines() throws Exception {
		ctrl.replay();

		parser.processTextLine("diff --git a/mancala/player/GreedyPlayer.java b/mancala/player/GreedyPlayer.java", weights, contributions, dev,
				summarizable);
		parser.processTextLine("index 7ace1e4..db95c9c 100644", weights, contributions, dev, summarizable);
		parser.processTextLine("@@ -24,7 +24,7 @@", weights, contributions, dev, summarizable);
	}

	@Test
	public void hitsCodeLine() throws Exception {
		CloudWeights weights = new CloudWeights();
		ctrl.replay();
		parser.processTextLine(" 	public int getPlay(Board state) {", weights, contributions, dev, summarizable);
		assertEquals(0.0, weights.get(token("get")), 0.001);
		assertEquals(1.0, weights.get(token("getPlay")), 0.001);
	}

	@Test
	public void tougherLines() throws Exception {
		CloudWeights weights = new CloudWeights();
		ctrl.replay();

		parser.processTextLine("-		for (int play = 0; play < Board.SLOT_WIDTH; play++) { //A modification for testing", weights, contributions,
				dev, summarizable);
		assertEquals(3.0, weights.get(token("play")), 0.001);
	}

	private ISummaryToken token(String str) {
		return new DiffToken(summarizable, str, str);
	}

}