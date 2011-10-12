package org.chaoticbits.collabcloud.visualizer.avatars;

import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.awt.Font;
import java.awt.Shape;
import java.awt.font.GlyphVector;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import org.chaoticbits.collabcloud.CloudWeights;
import org.chaoticbits.collabcloud.ISummaryToken;
import org.chaoticbits.collabcloud.codeprocessor.java.JavaClassSummarizable;
import org.chaoticbits.collabcloud.codeprocessor.java.JavaSummaryToken;
import org.chaoticbits.collabcloud.codeprocessor.java.JavaTokenType;
import org.chaoticbits.collabcloud.vc.Developer;
import org.chaoticbits.collabcloud.vc.DiffToken;
import org.chaoticbits.collabcloud.visualizer.font.IFontTransformer;
import org.chaoticbits.collabcloud.visualizer.placement.IPlaceStrategy;
import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TokenBoundaryRendererTest {

	private final IMocksControl ctrl = EasyMock.createStrictControl();
	private final IPlaceStrategy placeStrategy = ctrl.createMock(IPlaceStrategy.class);
	private final IFontTransformer fontTrans = ctrl.createMock(IFontTransformer.class);
	private final JavaClassSummarizable summarizable = ctrl.createMock(JavaClassSummarizable.class);
	private final CloudWeights weights = ctrl.createMock(CloudWeights.class);
	private final Font mockFont = ctrl.createMock(Font.class);
	private final GlyphVector glyph = ctrl.createMock(GlyphVector.class);
	private TokenBoundaryRenderer tokenBoundaryRenderer;

	@Before
	public void resetMocks() {
		ctrl.reset();
		tokenBoundaryRenderer = new TokenBoundaryRenderer(fontTrans, placeStrategy, weights);
	}

	@After
	public void verifyMocks() {
		ctrl.verify();
	}

	@Test
	public void developerSquare() throws Exception {
		Developer developer = new Developer("abc", "def@ghi.com");
		expect(placeStrategy.getStartingPlace(developer, null)).andReturn(new Point2D.Double(50, 60));
		ctrl.replay();

		Shape boundary = developer.accept(tokenBoundaryRenderer);
		assertTrue("Boundary is a rectangle", boundary instanceof Rectangle2D);
		Rectangle2D rect = (Rectangle2D) boundary;
		assertEquals(80, rect.getWidth(), 0.001);
		assertEquals(80, rect.getHeight(), 0.001);
		assertEquals(50, rect.getX(), 0.001);
		assertEquals(60, rect.getY(), 0.001);
	}

	@Test
	public void javaToken() throws Exception {
		ISummaryToken token = new JavaSummaryToken(summarizable, "some full name", "token", JavaTokenType.CLASS);
		expect(weights.get(token)).andReturn(55d).once();
		expect(fontTrans.transform(token, 55d)).andReturn(mockFont).once();
		expect(mockFont.createGlyphVector(TokenBoundaryRenderer.DEFAULT_FONT_RENDER_CONTEXT, "token")).andReturn(glyph).once();
		expect(glyph.getOutline()).andReturn(null).once();
		expect(placeStrategy.getStartingPlace(token, null)).andReturn(new Point2D.Double(50d, 60d)).once();
		expect(glyph.getOutline(50f, 60f)).andReturn(null).once();
		ctrl.replay();

		Shape boundary = token.accept(tokenBoundaryRenderer);
		assertNull("Boundary should be null based on our mocks", boundary);
		// any other expectation here is too hard to assert - assume it went fine
	}
	
	@Test
	public void diffToken() throws Exception {
		try{
			new DiffToken(summarizable, null, null).accept(tokenBoundaryRenderer);
			fail("exception should have been thrown");
		} catch(IllegalAccessError e){
			assertEquals(TokenBoundaryRenderer.DIFF_NOT_SUPPORTED, e.getMessage());
		}
		ctrl.replay();
	}
}
