package org.chaoticbits.collabcloud.visualizer.intersect.awt;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.awt.Shape;

import org.chaoticbits.collabcloud.visualizer.AWTIntersector;
import org.junit.Test;

public class AWTShapeIntersectTest {

	@Test
	public void amSimpleNoIntersect() throws Exception {
		Shape first = AWTWordExample.AM_SIMPLE_NOT_INTERSECT.getFirst();
		Shape second = AWTWordExample.AM_SIMPLE_NOT_INTERSECT.getSecond();
		assertFalse("no intersect, can tell by the outer bounding boxes", new AWTIntersector().hits(first, second));
	}

	@Test
	public void amTougherNoIntersect() throws Exception {
		Shape first = AWTWordExample.AM_INSIDE_NOT_INTERSECT.getFirst();
		Shape second = AWTWordExample.AM_INSIDE_NOT_INTERSECT.getSecond();
		assertFalse("a tougher intersection to find", new AWTIntersector().hits(first, second));
	}

	@Test
	public void amIntersect() throws Exception {
		Shape first = AWTWordExample.AM_INTERSECT.getFirst();
		Shape second = AWTWordExample.AM_INTERSECT.getSecond();
		assertTrue("they do intersect", new AWTIntersector().hits(first, second));
	}

	@Test
	public void amIntersect2() throws Exception {
		Shape first = AWTWordExample.AM_INTERSECT2.getFirst();
		Shape second = AWTWordExample.AM_INTERSECT2.getSecond();
		assertTrue("they also intersect", new AWTIntersector().hits(first, second));
	}

	@Test
	public void amNoIntersectReallyClose() throws Exception {
		Shape first = AWTWordExample.AM_REALLY_CLOSE_NO_INTERSECT.getFirst();
		Shape second = AWTWordExample.AM_REALLY_CLOSE_NO_INTERSECT.getSecond();
		assertTrue("they do intersect at a low depth", new AWTIntersector(5).hits(first, second));
		assertFalse("they don't intersect at a high depth", new AWTIntersector(6).hits(first, second));
	}

	@Test
	public void fullWordNoIntersect() throws Exception {
		Shape first = AWTWordExample.APPLES_ORANGES_NO_INTERSECT.getFirst();
		Shape second = AWTWordExample.APPLES_ORANGES_NO_INTERSECT.getSecond();
		assertTrue("they do intersect at a low depth", new AWTIntersector(7).hits(first, second));
		assertFalse("they don't intersect at a high depth", new AWTIntersector(8).hits(first, second));
	}

	@Test
	public void fullWordIntersect() throws Exception {
		Shape first = AWTWordExample.APPLES_ORANGES_INTERSECT.getFirst();
		Shape second = AWTWordExample.APPLES_ORANGES_INTERSECT.getSecond();
		assertTrue("they do intersect at a low depth", new AWTIntersector(10).hits(first, second));
	}

	// disable this timeout on debug...
	@Test(timeout = 1000)
	public void cutoffLeafTest() throws Exception {
		Shape first = AWTWordExample.AM_INSIDE_NOT_INTERSECT.getFirst();
		Shape second = AWTWordExample.AM_INSIDE_NOT_INTERSECT.getSecond();
		AWTIntersector intersector = new AWTIntersector(1000, 0.7d);
		assertFalse("a tougher intersection to not find with the cutoff leaf", intersector.hits(first, second));
	}

	// disable this timeout on debug...
	@Test(timeout = 1000)
	public void cutoffLeafReallyCloseNoHit() throws Exception {
		Shape first = AWTWordExample.AM_REALLY_CLOSE_NO_INTERSECT.getFirst();
		Shape second = AWTWordExample.AM_REALLY_CLOSE_NO_INTERSECT.getSecond();
		AWTIntersector intersector = new AWTIntersector(1000, 0.7d);
		assertFalse("a tougher intersection to not find with the cutoff leaf", intersector.hits(first, second));
	}

	// disable this timeout on debug...
	@Test(timeout = 1000)
	public void cutoffLeafReallyCloseHits() throws Exception {
		Shape first = AWTWordExample.AM_REALLY_CLOSE_INTERSECT.getFirst();
		Shape second = AWTWordExample.AM_REALLY_CLOSE_INTERSECT.getSecond();
		AWTIntersector intersector = new AWTIntersector(1000, 0.5d);
		assertTrue("a tougher intersection to find, uses the cutoff leaf", intersector.hits(first, second));
	}
}
