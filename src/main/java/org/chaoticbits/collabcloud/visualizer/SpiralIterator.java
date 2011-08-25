package org.chaoticbits.collabcloud.visualizer;

import java.awt.geom.Point2D;
import java.util.Iterator;

public class SpiralIterator implements Iterable<Point2D>, Iterator<Point2D> {

	private final int maxSteps;
	private final Point2D center;
	private double i;
	private double step;

	/**
	 * Iterate over a center in a spiral
	 * @param center
	 * @param maxSize
	 * @param numSteps
	 */
	public SpiralIterator(Point2D center, double maxSize, int numSteps) {
		this.center = center;
		this.maxSteps = numSteps;
		this.i = 0.0d;
		this.step = maxSize / numSteps;
	}

	public Iterator<Point2D> iterator() {
		return this;
	}

	public boolean hasNext() {
		return i < maxSteps;
	}

	public Point2D next() {
		double rTheta = i * step;
		double x = rTheta * Math.cos(rTheta) + center.getX();
		double y = rTheta * Math.sin(rTheta) + center.getY();
		i++;
		return new Point2D.Double(x, y);
	}

	public void remove() {
		throw new IllegalStateException("Not supported.");
	}

}
