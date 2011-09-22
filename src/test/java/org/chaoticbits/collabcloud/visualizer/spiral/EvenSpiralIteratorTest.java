package org.chaoticbits.collabcloud.visualizer.spiral;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.awt.geom.Point2D;
import java.util.Iterator;

import junit.framework.Assert;

import org.chaoticbits.collabcloud.visualizer.spiral.EvenSpiralIterator;
import org.junit.Test;

public class EvenSpiralIteratorTest {

	@Test
	public void iterateOver100Width() throws Exception {
		EvenSpiralIterator itr = new EvenSpiralIterator(new Point2D.Double(250, 250), 100.0d, 4);
		assertTrue(itr.hasNext());
		assertPointEquals("not starting start at the center", new Point2D.Double(277.0123, 292.075), itr.next());
		assertTrue(itr.hasNext());
		assertPointEquals("larger", new Point2D.Double(240.38, 320.053), itr.next());
		assertTrue(itr.hasNext());
		assertPointEquals("larger", new Point2D.Double(193.302, 315.46), itr.next());
		assertTrue(itr.hasNext());
		assertPointEquals("largest", new Point2D.Double(156.305, 284.95), itr.next());
		assertFalse("No more", itr.hasNext());
	}

	private void assertPointEquals(String message, Point2D exp, Point2D actual) {
		org.junit.Assert.assertEquals(message + "(x)", exp.getX(), actual.getX(), 0.01);
		org.junit.Assert.assertEquals(message + "(y)", exp.getY(), actual.getY(), 0.01);
	}

	@Test
	public void isItsOwnIterator() throws Exception {
		EvenSpiralIterator itr = new EvenSpiralIterator(new Point2D.Double(250, 250), 100.0d, 4);
		assertSame("Uses itself for an iterator", itr, itr.iterator());
	}

	@Test
	public void removeNotSupported() throws Exception {
		Iterator<Point2D> iterator = new EvenSpiralIterator(new Point2D.Double(250, 250), 100.0d, 4).iterator();
		try {
			iterator.remove();
			fail("Exception should have been thrown.");
		} catch (IllegalStateException e) {
			Assert.assertEquals("Not supported.", e.getMessage());
		}
	}
	
	@Test
	public void squashdown() throws Exception {
		EvenSpiralIterator itr = new EvenSpiralIterator(new Point2D.Double(250, 250), 100.0d, 4, 2.5);
		itr.next();
		assertTrue(itr.hasNext());
		assertPointEquals("larger, but squashed", new Point2D.Double(225.96, 320.05), itr.next());
	}
	
	@Test
	public void initCenter() throws Exception {
		EvenSpiralIterator itr = new EvenSpiralIterator(100.0d, 4, 2.5);
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
		EvenSpiralIterator itr = new EvenSpiralIterator(new Point2D.Double(250, 250), 100.0d, 4);
		assertPointEquals("reset", new Point2D.Double(277.012, 292.075), itr.next());
		itr.resetCenter(new Point2D.Double(150, 150));
		assertPointEquals("larger, different center", new Point2D.Double(129.18, 195.46), itr.next());
	}
}
