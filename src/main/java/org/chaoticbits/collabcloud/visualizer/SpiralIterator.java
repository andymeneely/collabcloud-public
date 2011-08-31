package org.chaoticbits.collabcloud.visualizer;

import java.awt.geom.Point2D;
import java.util.Iterator;

public class SpiralIterator implements Iterable<Point2D>, Iterator<Point2D> {

	private final int maxSteps;
	private Point2D center;
	private double i;
	private double step;
	private double sqaushDown = 1.0d;

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

	/**
	 * Iterate over a center in a spiral, with a squashdown.
	 * @param center
	 * @param maxSize
	 * @param numSteps
	 * @param squashDown
	 *            A squash of 1.0 is a square spiral. A squash of 2.0 is twice as wide as high, and a squash
	 *            of 0.5 is twice as high as wide.
	 */
	public SpiralIterator(Point2D center, double maxSize, int numSteps, double sqaushDown) {
		this.center = center;
		this.sqaushDown = sqaushDown;
		this.maxSteps = numSteps;
		this.i = 0.0d;
		this.step = maxSize / numSteps;
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
	public SpiralIterator(double maxSize, int numSteps, double sqaushDown) {
		this.sqaushDown = sqaushDown;
		this.maxSteps = numSteps;
		this.i = 0.0d;
		this.step = maxSize / numSteps;
	}
	
	public void initCenter(Point2D center){
		if(this.center!=null)
			throw new IllegalAccessError("Center already intialized!");
		this.center = center;
	}

	public Iterator<Point2D> iterator() {
		return this;
	}

	public boolean hasNext() {
		return i < maxSteps;
	}

	public Point2D next() {
		if(center==null)
			throw new IllegalAccessError("Initialize spiral center first.");
		double rTheta = i * step;
		double x = sqaushDown * rTheta * Math.cos(rTheta) + center.getX();
		double y = rTheta * Math.sin(rTheta) + center.getY();
		i++;
		return new Point2D.Double(x, y);
	}

	public void remove() {
		throw new IllegalStateException("Not supported.");
	}

}
