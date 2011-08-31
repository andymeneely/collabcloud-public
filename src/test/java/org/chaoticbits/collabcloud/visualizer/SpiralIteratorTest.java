package org.chaoticbits.collabcloud.visualizer;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.awt.geom.Point2D;
import java.util.Iterator;

import junit.framework.Assert;

import org.junit.Test;

public class SpiralIteratorTest {

	@Test
	public void iterateOver100Width() throws Exception {
		SpiralIterator itr = new SpiralIterator(new Point2D.Double(250, 250), 100.0d, 4);
		assertTrue(itr.hasNext());
		assertEquals("start at center", new Point2D.Double(250, 250), itr.next());
		assertTrue(itr.hasNext());
		assertEquals("larger", new Point2D.Double(274.78, 246.69), itr.next());
		assertTrue(itr.hasNext());
		assertEquals("larger", new Point2D.Double(298.25, 236.88), itr.next());
		assertTrue(itr.hasNext());
		assertEquals("largest", new Point2D.Double(319.13, 220.92), itr.next());
		assertFalse("No more", itr.hasNext());
	}

	private void assertEquals(String message, Point2D exp, Point2D actual) {
		org.junit.Assert.assertEquals(message + "(x)", exp.getX(), actual.getX(), 0.01);
		org.junit.Assert.assertEquals(message + "(y)", exp.getY(), actual.getY(), 0.01);
	}

	@Test
	public void isItsOwnIterator() throws Exception {
		SpiralIterator itr = new SpiralIterator(new Point2D.Double(250, 250), 100.0d, 4);
		assertSame("Uses itself for an iterator", itr, itr.iterator());
	}

	@Test
	public void removeNotSupported() throws Exception {
		Iterator<Point2D> iterator = new SpiralIterator(new Point2D.Double(250, 250), 100.0d, 4).iterator();
		try {
			iterator.remove();
			fail("Exception should have been thrown.");
		} catch (IllegalStateException e) {
			Assert.assertEquals("Not supported.", e.getMessage());
		}
	}
	
	@Test
	public void squashdown() throws Exception {
		SpiralIterator itr = new SpiralIterator(new Point2D.Double(250, 250), 100.0d, 4, 2.5);
		assertTrue(itr.hasNext());
		assertEquals("start at center", new Point2D.Double(250, 250), itr.next());
		assertTrue(itr.hasNext());
		assertEquals("larger, but squashed", new Point2D.Double(311.95, 246.69), itr.next());
	}
}
