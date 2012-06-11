package org.chaoticbits.collabcloud.visualizer.intersect.awt;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.awt.geom.Rectangle2D;

/**
 * Wrote this test because I don't trust the Rectangle2D intersect method.
 * 
 * @author andy
 * 
 */
public class BoundingBoxTest {

	@org.junit.Test
	public void notIntersectingBothAxes() throws Exception {
		Rectangle2D a = new Rectangle2D.Double(0.0d, 0.0d, 1.0d, 1.0d);
		Rectangle2D b = new Rectangle2D.Double(1.5d, 1.5d, 2.0d, 2.0d);
		assertFalse("boxes do not intersect each other", a.intersects(b));
		assertFalse("reverse is true too", b.intersects(a));
	}

	@org.junit.Test
	public void notIntersectingXAxes() throws Exception {
		Rectangle2D a = new Rectangle2D.Double(0.0d, 1.0d, 1.0d, 2.0d);
		Rectangle2D b = new Rectangle2D.Double(1.5d, 1.5d, 2.0d, 2.0d);
		assertFalse("boxes do not intersect each other", a.intersects(b));
		assertFalse("reverse is true too", b.intersects(a));
	}

	@org.junit.Test
	public void notIntersectingYAxes() throws Exception {
		Rectangle2D a = new Rectangle2D.Double(1.0d, 0.0d, 2.0d, 1.0d);
		Rectangle2D b = new Rectangle2D.Double(1.5d, 1.5d, 2.0d, 2.0d);
		assertFalse("boxes do not intersect each other", a.intersects(b));
		assertFalse("reverse is true too", b.intersects(a));
	}

	@org.junit.Test
	public void doIntersectSameAxes() throws Exception {
		Rectangle2D a = new Rectangle2D.Double(0.0d, 0.0d, 2.0d, 2.0d);
		Rectangle2D b = new Rectangle2D.Double(1.0d, 1.0d, 3.0d, 3.0d);
		assertTrue("boxes do intersect each other", a.intersects(b));
		assertTrue("reverse is true too", b.intersects(a));
	}

	@org.junit.Test
	public void doIntersectJustVertical() throws Exception {
		Rectangle2D a = new Rectangle2D.Double(0.0d, 0.0d, 2.0d, 2.0d);
		Rectangle2D b = new Rectangle2D.Double(0.0d, 1.0d, 2.0d, 3.0d);
		assertTrue("boxes do intersect each other", a.intersects(b));
		assertTrue("reverse is true too", b.intersects(a));
	}

	@org.junit.Test
	public void doIntersectJustHorizontal() throws Exception {
		Rectangle2D a = new Rectangle2D.Double(0.0d, 0.0d, 2.0d, 2.0d);
		Rectangle2D b = new Rectangle2D.Double(1.0d, 0.0d, 3.0d, 2.0d);
		assertTrue("boxes do intersect each other", a.intersects(b));
		assertTrue("reverse is true too", b.intersects(a));
	}
}
