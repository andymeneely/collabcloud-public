package org.chaoticbits.collabcloud.vc.git;

import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;

import java.util.Collections;
import java.util.Map.Entry;

import org.chaoticbits.collabcloud.codeprocessor.CloudWeights;
import org.chaoticbits.collabcloud.codeprocessor.ISummaryToken;
import org.chaoticbits.collabcloud.codeprocessor.IWeightModifier;
import org.chaoticbits.collabcloud.codeprocessor.MultiplyModifier;
import org.chaoticbits.collabcloud.codeprocessor.java.JavaSummaryToken;
import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class GitDiffParserTest {

	private final IMocksControl ctrl = EasyMock.createControl();
	private CloudWeights weights = ctrl.createMock(CloudWeights.class);
	private IWeightModifier modifier = new MultiplyModifier(2.0);
	private Entry<ISummaryToken, Double> entry = ctrl.createMock(Entry.class);
	private GitDiffParser parser;

	@Before
	public void resetMocks() {
		parser = new GitDiffParser();
		ctrl.reset();
	}

	@After
	public void verifyMocks() {
		ctrl.verify();
	}

	@Test
	public void skipsDiffLine() throws Exception {
		ctrl.replay();
		GitDiffParser parser = new GitDiffParser();
		parser.processTextLine(weights, modifier, "diff");
		ctrl.verify();
	}

	@Test
	public void skipsIndexLine() throws Exception {
		ctrl.replay();
		GitDiffParser parser = new GitDiffParser();
		parser.processTextLine(weights, modifier, "index");
		ctrl.verify();
	}

	@Test
	public void skipsAtLines() throws Exception {
		ctrl.replay();
		GitDiffParser parser = new GitDiffParser();
		parser.processTextLine(weights, modifier, "@@");
	}

	@Test
	public void skipsAllHeaderLines() throws Exception {
		ctrl.replay();
		parser.processTextLine(weights, modifier, "diff --git a/mancala/player/GreedyPlayer.java b/mancala/player/GreedyPlayer.java");
		parser.processTextLine(weights, modifier, "index 7ace1e4..db95c9c 100644");
		parser.processTextLine(weights, modifier, "@@ -24,7 +24,7 @@");
	}

	@Test
	public void hitsCodeLine() throws Exception {
		expect(weights.unsortedEntries()).andReturn(Collections.singleton(entry)).once();
		JavaSummaryToken token = token("getPlay");
		expect(entry.getKey()).andReturn(token).anyTimes();
		expect(entry.getValue()).andReturn(3.0).anyTimes();
		weights.put(token, 6.0);
		expectLastCall().once();
		ctrl.replay();
		parser.processTextLine(weights, modifier, " 	public int getPlay(Board state) {");
	}

	@Test
	public void hitsCodeLineTokenizes() throws Exception {
		expect(weights.unsortedEntries()).andReturn(Collections.singleton(entry)).once();
		expect(entry.getKey()).andReturn(token("get")).anyTimes();
		ctrl.replay();
		parser.processTextLine(weights, modifier, " 	public int getPlay(Board state) {");
	}

	@Test
	public void tougherLines() throws Exception {
		expect(weights.unsortedEntries()).andReturn(Collections.singleton(entry)).once();
		JavaSummaryToken token = token("play");
		expect(entry.getKey()).andReturn(token).anyTimes();
		expect(entry.getValue()).andReturn(3.0).anyTimes();
		weights.put(token, 6.0);
		expectLastCall().times(3);
		ctrl.replay();
		parser.processTextLine(weights, modifier, "-		for (int play = 0; play < Board.SLOT_WIDTH; play++) { //A modification for testing");
	}

	private JavaSummaryToken token(String str) {
		return new JavaSummaryToken(null, null, str, null);
	}

}