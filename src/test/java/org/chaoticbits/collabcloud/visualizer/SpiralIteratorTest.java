package org.chaoticbits.collabcloud.visualizer;

import static org.junit.Assert.assertEquals;
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
		assertPointEquals("start at center", new Point2D.Double(250, 250), itr.next());
		assertTrue(itr.hasNext());
		assertPointEquals("larger", new Point2D.Double(274.78, 246.69), itr.next());
		assertTrue(itr.hasNext());
		assertPointEquals("larger", new Point2D.Double(298.25, 236.88), itr.next());
		assertTrue(itr.hasNext());
		assertPointEquals("largest", new Point2D.Double(319.13, 220.92), itr.next());
		assertFalse("No more", itr.hasNext());
	}

	private void assertPointEquals(String message, Point2D exp, Point2D actual) {
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
		assertPointEquals("start at center", new Point2D.Double(250, 250), itr.next());
		assertTrue(itr.hasNext());
		assertPointEquals("larger, but squashed", new Point2D.Double(311.95, 246.69), itr.next());
	}
	
	@Test
	public void initCenter() throws Exception {
		SpiralIterator itr = new SpiralIterator(100.0d, 4, 2.5);
		try{
			itr.next();
			fail("exception should have been thrown");
		}catch(IllegalAccessError error){
			assertEquals("Initialize spiral center first.", error.getMessage());
		}
		itr.resetCenter(new Point2D.Double(250, 250));
		itr.next(); //no exception!
	}
	
	@Test
	public void resetCenter() throws Exception {
		SpiralIterator itr = new SpiralIterator(new Point2D.Double(250, 250), 100.0d, 4);
		assertPointEquals("start at center", new Point2D.Double(250, 250), itr.next());
		assertPointEquals("larger", new Point2D.Double(274.78, 246.69), itr.next());
		itr.resetCenter(new Point2D.Double(150, 150));
		assertPointEquals("start at a different center", new Point2D.Double(150, 150), itr.next());
		assertPointEquals("larger, different center", new Point2D.Double(174.78, 146.69), itr.next());
	}
}
