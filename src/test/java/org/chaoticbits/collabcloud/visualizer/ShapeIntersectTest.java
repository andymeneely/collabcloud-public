package org.chaoticbits.collabcloud.visualizer;

import static org.junit.Assert.*;

import java.awt.Shape;

import org.junit.Test;

public class ShapeIntersectTest {

	@Test
	public void amSimpleNoIntersect() throws Exception {
		Shape first = WordExample.AM_SIMPLE_NOT_INTERSECT.getFirst();
		Shape second = WordExample.AM_SIMPLE_NOT_INTERSECT.getSecond();
		assertFalse("no intersect, can tell by the outer bounding boxes", new Intersector().intersect(first, second));
	}

	@Test
	public void amTougherNoIntersect() throws Exception {
		Shape first = WordExample.AM_INSIDE_NOT_INTERSECT.getFirst();
		Shape second = WordExample.AM_INSIDE_NOT_INTERSECT.getSecond();
		assertFalse("a tougher intersection to find", new Intersector().intersect(first, second));
	}

	@Test
	public void amIntersect() throws Exception {
		Shape first = WordExample.AM_INTERSECT.getFirst();
		Shape second = WordExample.AM_INTERSECT.getSecond();
		assertTrue("they do intersect", new Intersector().intersect(first, second));
	}

	@Test
	public void amIntersect2() throws Exception {
		Shape first = WordExample.AM_INTERSECT2.getFirst();
		Shape second = WordExample.AM_INTERSECT2.getSecond();
		assertTrue("they also intersect", new Intersector().intersect(first, second));
	}

	@Test
	public void amNoIntersectReallyClose() throws Exception {
		Shape first = WordExample.AM_REALLY_CLOSE_NO_INTERSECT.getFirst();
		Shape second = WordExample.AM_REALLY_CLOSE_NO_INTERSECT.getSecond();
		assertTrue("they do intersect at a low depth", new Intersector(5).intersect(first, second));
		assertFalse("they don't intersect at a high depth", new Intersector(6).intersect(first, second));
	}
	
	@Test
	public void fullWordNoIntersect() throws Exception {
		Shape first = WordExample.APPLES_ORANGES_NO_INTERSECT.getFirst();
		Shape second = WordExample.APPLES_ORANGES_NO_INTERSECT.getSecond();
		assertTrue("they do intersect at a low depth", new Intersector(7).intersect(first, second));
		assertFalse("they don't intersect at a high depth", new Intersector(8).intersect(first, second));
	}
	
	@Test
	public void fullWordIntersect() throws Exception {
		Shape first = WordExample.APPLES_ORANGES_INTERSECT.getFirst();
		Shape second = WordExample.APPLES_ORANGES_INTERSECT.getSecond();
		assertTrue("they do intersect at a low depth", new Intersector(10).intersect(first, second));
	}
}
