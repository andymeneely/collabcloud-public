package org.chaoticbits.collabcloud.visualizer;

import static org.junit.Assert.*;

import java.awt.Point;
import java.awt.font.GlyphVector;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class BoundingBoxTest {

	@org.junit.Test
	public void notIntersectingBothAxes() throws Exception {
		BoundingBox a = new BoundingBox(new PointXY(0.0d, 0.0d), new PointXY(1.0d, 1.0d));
		BoundingBox b = new BoundingBox(new PointXY(1.5d, 1.5d), new PointXY(2.0d, 2.0d));
		assertFalse("boxes do not intersect each other", a.intersects(b));
		assertFalse("reverse is true too", b.intersects(a));
	}

	@org.junit.Test
	public void notIntersectingXAxes() throws Exception {
		BoundingBox a = new BoundingBox(new PointXY(0.0d, 1.0d), new PointXY(1.0d, 2.0d));
		BoundingBox b = new BoundingBox(new PointXY(1.5d, 1.5d), new PointXY(2.0d, 2.0d));
		assertFalse("boxes do not intersect each other", a.intersects(b));
		assertFalse("reverse is true too", b.intersects(a));
	}

	@org.junit.Test
	public void notIntersectingYAxes() throws Exception {
		BoundingBox a = new BoundingBox(new PointXY(1.0d, 0.0d), new PointXY(2.0d, 1.0d));
		BoundingBox b = new BoundingBox(new PointXY(1.5d, 1.5d), new PointXY(2.0d, 2.0d));
		assertFalse("boxes do not intersect each other", a.intersects(b));
		assertFalse("reverse is true too", b.intersects(a));
	}

	@org.junit.Test
	public void doIntersectSameAxes() throws Exception {
		BoundingBox a = new BoundingBox(new PointXY(0.0d, 0.0d), new PointXY(2.0d, 2.0d));
		BoundingBox b = new BoundingBox(new PointXY(1.0d, 1.0d), new PointXY(3.0d, 3.0d));
		assertTrue("boxes do intersect each other", a.intersects(b));
		assertTrue("reverse is true too", b.intersects(a));
	}

	@org.junit.Test
	public void doIntersectJustVertical() throws Exception {
		BoundingBox a = new BoundingBox(new PointXY(0.0d, 0.0d), new PointXY(2.0d, 2.0d));
		BoundingBox b = new BoundingBox(new PointXY(0.0d, 1.0d), new PointXY(2.0d, 3.0d));
		assertTrue("boxes do intersect each other", a.intersects(b));
		assertTrue("reverse is true too", b.intersects(a));
	}

	@org.junit.Test
	public void doIntersectJustHorizontal() throws Exception {
		BoundingBox a = new BoundingBox(new PointXY(0.0d, 0.0d), new PointXY(2.0d, 2.0d));
		BoundingBox b = new BoundingBox(new PointXY(1.0d, 0.0d), new PointXY(3.0d, 2.0d));
		assertTrue("boxes do intersect each other", a.intersects(b));
		assertTrue("reverse is true too", b.intersects(a));
	}
}
