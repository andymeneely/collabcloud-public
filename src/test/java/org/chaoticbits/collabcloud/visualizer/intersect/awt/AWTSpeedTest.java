package org.chaoticbits.collabcloud.visualizer.intersect.awt;

import static org.junit.Assert.assertTrue;

import java.awt.Shape;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.chaoticbits.collabcloud.visualizer.AWTIntersector;
import org.junit.Before;
import org.junit.Test;

public class AWTSpeedTest {
	private List<Shape> shapes;
	private int NUM_CHECKS = 1000;
	private int NUM_SHAPES = 1000;
	private Random rand = new Random(123456);

	@Before
	public void init() {
		shapes = new ArrayList<Shape>(NUM_SHAPES);
		for (int i = 0; i < NUM_SHAPES; i++) {
			String string = (char) ((char) rand.nextInt(26) + 'a') + "";
			float x = rand.nextFloat() * 100f;
			float y = rand.nextFloat() * 100f;
			Shape shape = AWTWordExample.makeShape(AWTWordExample.getFont(), AWTWordExample.getFRC(), string, x, y);
			// System.out.println(string + "@" + x + "," + y);
			shapes.add(shape);
		}
	}

	@Test
	public void tonsOfWords() throws Exception {
		// normal recursive depth, normal leaf cutoff
		AWTIntersector intersector = new AWTIntersector(10, 2.0d);
		int numHits = 0;
		long start = System.currentTimeMillis();
		for (int i = 0; i < NUM_CHECKS; i++) {
			boolean hit = intersector.hits(shapes.get(rand.nextInt(NUM_SHAPES)), shapes.get(rand.nextInt(NUM_SHAPES)));
			if (hit)
				numHits++;
		}
		long timeTaken = System.currentTimeMillis() - start;
		assertTrue("Should take less than 200ms", timeTaken < 200);
		System.out.println("Intersection took: " + timeTaken + "ms, " + numHits + " hits out of " + NUM_CHECKS
				+ " checks");
	}
}
