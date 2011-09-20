package org.chaoticbits.collabcloud.vc.git;

import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;

import java.util.Collections;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.chaoticbits.collabcloud.CloudWeights;
import org.chaoticbits.collabcloud.ISummarizable;
import org.chaoticbits.collabcloud.ISummaryToken;
import org.chaoticbits.collabcloud.codeprocessor.IWeightModifier;
import org.chaoticbits.collabcloud.codeprocessor.MultiplyModifier;
import org.chaoticbits.collabcloud.codeprocessor.java.JavaSummaryToken;
import org.chaoticbits.collabcloud.vc.Developer;
import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class GitDiffParserTest {

	private final IMocksControl ctrl = EasyMock.createControl();
	private CloudWeights weights = ctrl.createMock(CloudWeights.class);
	private IWeightModifier modifier = new MultiplyModifier(2.0);
	@SuppressWarnings("unchecked")
	private Entry<ISummaryToken, Double> entry = ctrl.createMock(Entry.class);
	@SuppressWarnings("unchecked")
	private Map<Developer, Set<ISummarizable>> contributions = ctrl.createMock(Map.class);
	private Developer dev = ctrl.createMock(Developer.class);
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
		parser.processTextLine("diff", weights, modifier, contributions, dev);
	}

	@Test
	public void skipsIndexLine() throws Exception {
		ctrl.replay();
		
		GitDiffParser parser = new GitDiffParser();
		parser.processTextLine("index", weights, modifier, contributions, dev);
	}

	@Test
	public void skipsAtLines() throws Exception {
		ctrl.replay();
		
		GitDiffParser parser = new GitDiffParser();
		parser.processTextLine("@@", weights, modifier, contributions, dev);
	}

	@Test
	public void skipsAllHeaderLines() throws Exception {
		ctrl.replay();
		
		parser.processTextLine("diff --git a/mancala/player/GreedyPlayer.java b/mancala/player/GreedyPlayer.java", weights, modifier,
				contributions,dev);
		parser.processTextLine("index 7ace1e4..db95c9c 100644", weights, modifier, contributions, dev);
		parser.processTextLine("@@ -24,7 +24,7 @@", weights, modifier, contributions, dev);
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
		parser.processTextLine(" 	public int getPlay(Board state) {", weights, modifier, contributions, dev);
	}

	@Test
	public void hitsCodeLineTokenizes() throws Exception {
		expect(weights.unsortedEntries()).andReturn(Collections.singleton(entry)).once();
		expect(entry.getKey()).andReturn(token("get")).anyTimes();
		ctrl.replay();
		
		parser.processTextLine(" 	public int getPlay(Board state) {", weights, modifier, contributions, dev);
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
		
		parser.processTextLine("-		for (int play = 0; play < Board.SLOT_WIDTH; play++) { //A modification for testing", weights, modifier,
				contributions, dev);
	}

	private JavaSummaryToken token(String str) {
		return new JavaSummaryToken(null, null, str, null);
	}

}