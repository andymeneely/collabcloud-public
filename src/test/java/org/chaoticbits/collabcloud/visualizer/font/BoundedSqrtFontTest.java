package org.chaoticbits.collabcloud.visualizer.font;

import static org.junit.Assert.assertEquals;

import java.awt.Font;

import org.chaoticbits.collabcloud.CloudWeights;
import org.chaoticbits.collabcloud.ISummaryToken;
import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.junit.Before;
import org.junit.Test;

public class BoundedSqrtFontTest {

	private IMocksControl ctrl;
	private Font font = new Font("Courier New", Font.PLAIN, 100);

	@Before
	public void setUp() {
		ctrl = EasyMock.createControl();
	}

	@Test
	public void boundedLogFontNoNegatives() throws Exception {
		CloudWeights weights = new CloudWeights();
		weights.put(ctrl.createMock(ISummaryToken.class), 1000d);
		weights.put(ctrl.createMock(ISummaryToken.class), 100d);
		weights.put(ctrl.createMock(ISummaryToken.class), 10d);
		IFontTransformer trans = new BoundedSqrtFont(font, weights, 75);
		ctrl.replay();
		assertEquals(75d, trans.transform(null, 1000d).getSize2D(), 0.001);
		assertEquals(53.033d, trans.transform(null, 500d).getSize2D(), 0.001);
		assertEquals(2.371d, trans.transform(null, 1d).getSize2D(), 0.001);
		ctrl.verify();
	}

	@Test
	public void boundedLogFontWithNegatives() throws Exception {
		CloudWeights weights = new CloudWeights();
		weights.put(ctrl.createMock(ISummaryToken.class), 1000d);
		weights.put(ctrl.createMock(ISummaryToken.class), 100d);
		weights.put(ctrl.createMock(ISummaryToken.class), -10d);
		IFontTransformer trans = new BoundedSqrtFont(font, weights, 75);
		ctrl.replay();
		assertEquals(74.624d, trans.transform(null, 1000d).getSize2D(), 0.001);
		ctrl.verify();
	}
}
