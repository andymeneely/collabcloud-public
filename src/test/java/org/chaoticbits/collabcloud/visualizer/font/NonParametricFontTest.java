package org.chaoticbits.collabcloud.visualizer.font;

import static org.junit.Assert.assertEquals;

import java.awt.Font;

import org.chaoticbits.collabcloud.CloudWeights;
import org.chaoticbits.collabcloud.ISummaryToken;
import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class NonParametricFontTest {

	private static final int NUM_TOKENS = 10;
	private IMocksControl ctrl = EasyMock.createControl();
	private ISummaryToken[] token;
	private Font font = new Font("Courier New", Font.PLAIN, 100);

	@Before
	public void setUp() {
		if (token == null) {
			token = new ISummaryToken[NUM_TOKENS];
			for (int i = 0; i < NUM_TOKENS; i++)
				token[i] = ctrl.createMock(ISummaryToken.class);
		}
		ctrl.reset();
	}

	@After
	public void verifyMocks() {
		ctrl.verify();
	}

	@Test
	public void squareTheRanks() throws Exception {
		CloudWeights weights = new CloudWeights();
		weights.put(token[0], 1000d); // Rank 7
		weights.put(token[1], 50d);
		weights.put(token[2], 10d); // Rank 5
		weights.put(token[3], 9d);
		weights.put(token[4], 8d);
		weights.put(token[5], 7d);
		weights.put(token[6], 5d); // Rank 1

		int maxFontSize = 49; // no scaling
		IFontTransformer trans = new NonParametricFont(font, weights, MathTransforms.square, maxFontSize);
		ctrl.replay();
		assertEquals(49d, trans.transform(token[0], 1000d).getSize2D(), 0.001);
		assertEquals(25d, trans.transform(token[2], 500d).getSize2D(), 0.001);
		assertEquals(1d, trans.transform(token[6], 1d).getSize2D(), 0.001);
	}
	
	@Test
	public void cubeTheRanks() throws Exception {
		CloudWeights weights = new CloudWeights();
		weights.put(token[0], 1000d); // Rank 4
		weights.put(token[1], 50d);
		//switch up the ranks - make sure its sorting
		weights.put(token[2], 5d); // Rank 1
		weights.put(token[3], 10d); // Rank 2

		int maxFontSize = 64; // no scaling
		IFontTransformer trans = new NonParametricFont(font, weights, MathTransforms.cube, maxFontSize);
		ctrl.replay();
		assertEquals(64d, trans.transform(token[0], 1000d).getSize2D(), 0.001);
		assertEquals(8d, trans.transform(token[3], 500d).getSize2D(), 0.001);
		assertEquals(1d, trans.transform(token[2], 1d).getSize2D(), 0.001);
	}
}
