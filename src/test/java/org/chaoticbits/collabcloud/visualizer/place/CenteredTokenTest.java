package org.chaoticbits.collabcloud.visualizer.place;

import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertEquals;

import java.awt.Shape;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.Random;

import org.chaoticbits.collabcloud.ISummaryToken;
import org.chaoticbits.collabcloud.visualizer.placement.CenteredTokenWrapper;
import org.chaoticbits.collabcloud.visualizer.placement.IPlaceStrategy;
import org.chaoticbits.collabcloud.visualizer.placement.RandomPlacement;
import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.junit.Test;

public class CenteredTokenTest {

	private IMocksControl ctrl;

	@Test
	public void inSquare() throws Exception {
		ctrl = EasyMock.createControl();
		IPlaceStrategy mockPlace = ctrl.createMock(IPlaceStrategy.class);
		Shape shape = ctrl.createMock(Shape.class);
		ISummaryToken token = ctrl.createMock(ISummaryToken.class);
		expect(mockPlace.getStartingPlace(token, shape)).andReturn(new Point2D.Double(500, 600)).once();
		expect(shape.getBounds2D()).andReturn(new Rectangle2D.Double(0, 0, 100, 200)).once();

		IPlaceStrategy place = new CenteredTokenWrapper(mockPlace);
		ctrl.replay();
		Point2D point2d = place.getStartingPlace(token, shape);
		assertEquals(450.0, point2d.getX(), 0.001);
		assertEquals(500.0, point2d.getY(), 0.001);
		ctrl.verify();
	}
}
