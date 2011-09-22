package org.chaoticbits.collabcloud.visualizer.spiral;

import java.awt.geom.Point2D;
import java.util.Iterator;

public class EvenSpiralIterator implements Iterable<Point2D>, Iterator<Point2D> {

	private final int maxSteps;
	private Point2D center;
	private double i;
	private double scale;
	private double sqaushDown = 1.0d;
	private double lastTheta = 0.0d;

	/**
	 * Iterate over a center in a spiral
	 * @param center
	 * @param maxSize
	 * @param numSteps
	 */
	public EvenSpiralIterator(Point2D center, double maxSize, int numSteps) {
		this(center, maxSize, numSteps, 1.0);
	}

	/**
	 * Iterate over a center in a spiral, with a squashdown.
	 * @param center
	 * @param maxSize
	 * @param numSteps
	 * @param squashDown
	 *            A squash of 1.0 is a square spiral. A squash of 2.0 is twice as wide as high, and a squash
	 *            of 0.5 is twice as high as wide.
	 */
	public EvenSpiralIterator(Point2D center, double maxSize, int numSteps, double sqaushDown) {
		this.center = center;
		this.sqaushDown = sqaushDown;
		this.maxSteps = numSteps;
		this.i = 1.0d;
		this.scale = maxSize / Math.sqrt(maxSteps);
	}

	/**
	 * Iterate over a center in a spiral, with a squashdown.
	 * @param center
	 * @param maxSize
	 * @param numSteps
	 * @param squashDown
	 *            A squash of 1.0 is a square spiral. A squash of 2.0 is twice as wide as high, and a squash
	 *            of 0.5 is twice as high as wide.
	 */
	public EvenSpiralIterator(double maxSize, int numSteps, double sqaushDown) {
		this(null, maxSize, numSteps, 0.0);
	}

	/**
	 * Resets the iterator to a new center.
	 * @param center
	 */
	public void resetCenter(Point2D center) {
		this.center = center;
		this.i = 1.0d;
	}

	public Iterator<Point2D> iterator() {
		return this;
	}

	public boolean hasNext() {
		return i <= maxSteps;
	}

	public Point2D next() {
		if (center == null)
			throw new IllegalAccessError("Initialize spiral center first.");
		double radius = Math.sqrt(i) * scale;
		double theta = lastTheta + Math.asin(1 / radius) * scale;
		double x = sqaushDown * radius * Math.cos(theta) + center.getX();
		double y = radius * Math.sin(theta) + center.getY();
		i++;
		lastTheta = theta;
		return new Point2D.Double(x, y);
	}

	public void remove() {
		throw new IllegalStateException("Not supported.");
	}

}
