package org.chaoticbits.collabcloud.visualizer.intersect.swt;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.eclipse.draw2d.graph.Path;
import org.junit.Test;

import edu.uci.ics.jung.visualization.transform.shape.Intersector;

public class PathIntersectTest {
//	@Test
//	public void amSimpleNoIntersect() throws Exception {
//		Path first = Draw2DWordExample.AM_SIMPLE_NOT_INTERSECT.getFirst();
//		Path second = Draw2DWordExample.AM_SIMPLE_NOT_INTERSECT.getSecond();
//		assertFalse("no intersect, can tell by the outer bounding boxes", new Intersector().hits(first, second));
//	}
//
//	@Test
//	public void amTougherNoIntersect() throws Exception {
//		Path first = Draw2DWordExample.AM_INSIDE_NOT_INTERSECT.getFirst();
//		Path second = Draw2DWordExample.AM_INSIDE_NOT_INTERSECT.getSecond();
//		assertFalse("a tougher intersection to find", new Intersector().hits(first, second));
//	}
//
//	@Test
//	public void amIntersect() throws Exception {
//		Path first = Draw2DWordExample.AM_INTERSECT.getFirst();
//		Path second = Draw2DWordExample.AM_INTERSECT.getSecond();
//		assertTrue("they do intersect", new Intersector().hits(first, second));
//	}
//
//	@Test
//	public void amIntersect2() throws Exception {
//		Path first = Draw2DWordExample.AM_INTERSECT2.getFirst();
//		Path second = Draw2DWordExample.AM_INTERSECT2.getSecond();
//		assertTrue("they also intersect", new Intersector().hits(first, second));
//	}
//
//	@Test
//	public void amNoIntersectReallyClose() throws Exception {
//		Path first = Draw2DWordExample.AM_REALLY_CLOSE_NO_INTERSECT.getFirst();
//		Path second = Draw2DWordExample.AM_REALLY_CLOSE_NO_INTERSECT.getSecond();
//		assertTrue("they do intersect at a low depth", new Intersector(5).hits(first, second));
//		assertFalse("they don't intersect at a high depth", new Intersector(6).hits(first, second));
//	}
//
//	@Test
//	public void fullWordNoIntersect() throws Exception {
//		Path first = Draw2DWordExample.APPLES_ORANGES_NO_INTERSECT.getFirst();
//		Path second = Draw2DWordExample.APPLES_ORANGES_NO_INTERSECT.getSecond();
//		assertTrue("they do intersect at a low depth", new Intersector(7).hits(first, second));
//		assertFalse("they don't intersect at a high depth", new Intersector(8).hits(first, second));
//	}
//
//	@Test
//	public void fullWordIntersect() throws Exception {
//		Path first = Draw2DWordExample.APPLES_ORANGES_INTERSECT.getFirst();
//		Path second = Draw2DWordExample.APPLES_ORANGES_INTERSECT.getSecond();
//		assertTrue("they do intersect at a low depth", new Intersector(10).hits(first, second));
//	}
//
//	// disable this timeout on debug...
//	@Test(timeout = 1000)
//	public void cutoffLeafTest() throws Exception {
//		Path first = Draw2DWordExample.AM_INSIDE_NOT_INTERSECT.getFirst();
//		Path second = Draw2DWordExample.AM_INSIDE_NOT_INTERSECT.getSecond();
//		Intersector intersector = new Intersector(1000, 0.7d);
//		assertFalse("a tougher intersection to not find with the cutoff leaf", intersector.hits(first, second));
//	}
//
//	// disable this timeout on debug...
//	@Test(timeout = 1000)
//	public void cutoffLeafReallyCloseNoHit() throws Exception {
//		Path first = Draw2DWordExample.AM_REALLY_CLOSE_NO_INTERSECT.getFirst();
//		Path second = Draw2DWordExample.AM_REALLY_CLOSE_NO_INTERSECT.getSecond();
//		Intersector intersector = new Intersector(1000, 0.7d);
//		assertFalse("a tougher intersection to not find with the cutoff leaf", intersector.hits(first, second));
//	}
//
//	// disable this timeout on debug...
//	@Test(timeout = 1000)
//	public void cutoffLeafReallyCloseHits() throws Exception {
//		Path first = Draw2DWordExample.AM_REALLY_CLOSE_INTERSECT.getFirst();
//		Path second = Draw2DWordExample.AM_REALLY_CLOSE_INTERSECT.getSecond();
//		Intersector intersector = new Intersector(1000, 0.5d);
//		assertTrue("a tougher intersection to find, uses the cutoff leaf", intersector.hits(first, second));
//	}
}
