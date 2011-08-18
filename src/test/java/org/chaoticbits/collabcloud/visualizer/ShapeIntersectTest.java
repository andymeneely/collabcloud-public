package org.chaoticbits.collabcloud.visualizer;

import static org.junit.Assert.*;

import java.awt.Shape;

import org.junit.Test;

public class ShapeIntersectTest {

	@Test
	public void amSimpleNoIntersect() throws Exception {
		Shape first = TwoLetterExamples.AM_SIMPLE_NOT_INTERSECT.getFirst();
		Shape second = TwoLetterExamples.AM_SIMPLE_NOT_INTERSECT.getSecond();
		assertFalse("no intersect, can tell by the outer bounding boxes",
				new Intersector().intersect(first, second));
	}

	@Test
	public void amTougherNoIntersect() throws Exception {
		Shape first = TwoLetterExamples.AM_INSIDE_NOT_INTERSECT.getFirst();
		Shape second = TwoLetterExamples.AM_INSIDE_NOT_INTERSECT.getSecond();
		assertFalse("a tougher intersection to find", new Intersector().intersect(first, second));
	}

	@Test
	public void amIntersect() throws Exception {
		Shape first = TwoLetterExamples.AM_INTERSECT.getFirst();
		Shape second = TwoLetterExamples.AM_INTERSECT.getSecond();
		assertTrue("they do intersect", new Intersector().intersect(first, second));
	}
}
