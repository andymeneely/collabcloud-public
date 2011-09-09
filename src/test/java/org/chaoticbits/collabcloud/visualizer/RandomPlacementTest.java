package org.chaoticbits.collabcloud.visualizer;

import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertEquals;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.Random;

import org.chaoticbits.collabcloud.visualizer.placement.RandomPlacement;
import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.junit.Test;

public class RandomPlacementTest {

	private IMocksControl ctrl;

	@Test
	public void inSquare() throws Exception {
		ctrl = EasyMock.createControl();
		Random rand = ctrl.createMock(Random.class);
		RandomPlacement p = new RandomPlacement(rand, new Rectangle2D.Double(80, 50, 10, 20));

		expect(rand.nextDouble()).andReturn(0.0);
		expect(rand.nextDouble()).andReturn(1.0);
		ctrl.replay();
		Point2D point2d = p.getStartingPlace(null, null);
		assertEquals(80.0, point2d.getX(), 0.001);
		assertEquals(70.0, point2d.getY(), 0.001);
		ctrl.verify();
	}
}
