package org.chaoticbits.collabcloud.visualizer.font;

import static org.junit.Assert.assertEquals;

import java.awt.Font;

import org.chaoticbits.collabcloud.codeprocessor.CloudWeights;
import org.chaoticbits.collabcloud.codeprocessor.ISummaryToken;
import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.junit.Before;
import org.junit.Test;

public class BoundedNonParametricSqrtFontTest {

	private IMocksControl ctrl;
	private Font font = new Font("Courier New", Font.PLAIN, 100);

	@Before
	public void setUp() {
		ctrl = EasyMock.createControl();
	}

	@Test
	public void boundedNonParametric() throws Exception {
		CloudWeights weights = new CloudWeights();
		ISummaryToken a = ctrl.createMock(ISummaryToken.class);
		ISummaryToken b = ctrl.createMock(ISummaryToken.class);
		ISummaryToken c = ctrl.createMock(ISummaryToken.class);
		ISummaryToken d = ctrl.createMock(ISummaryToken.class);
		ISummaryToken e = ctrl.createMock(ISummaryToken.class);
		weights.put(a, 5d);
		weights.put(b, 4d);
		weights.put(c, 3d);
		weights.put(d, 2d);
		weights.put(e, 1d);
		IFontTransformer trans = new BoundedSqrtNonParametricFont(font, weights, 75);
		ctrl.replay();
		assertEquals(75d, trans.transform(a, null).getSize2D(), 0.001);
		assertEquals(67.082d, trans.transform(b, null).getSize2D(), 0.001);
		assertEquals(58.095d, trans.transform(c, 1d).getSize2D(), 0.001);
		assertEquals(47.434d, trans.transform(d, 1d).getSize2D(), 0.001);
		assertEquals(33.541d, trans.transform(e, 1d).getSize2D(), 0.001);
		ctrl.verify();
	}
}
