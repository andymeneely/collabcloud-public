package org.chaoticbits.collabcloud.visualizer;

public class BoundingBox {

	private final PointXY upperLeft;
	private PointXY lowerRight;

	public BoundingBox(PointXY upperLeft, PointXY lowerRight) {
		this.upperLeft = upperLeft;
		this.lowerRight = lowerRight;
	}

	public PointXY getUpperLeft() {
		return upperLeft;
	}

	public PointXY getLowerRight() {
		return lowerRight;
	}

	/**
	 * Does a basic check to see if the boundaries are within in each other. Very fast.
	 * @param b
	 *            the other box
	 * @return if they intersect
	 */
	public boolean intersects(BoundingBox b) {
		return in(upperLeft.x(), b.upperLeft.x(), b.lowerRight.x())
				&& in(upperLeft.y(), b.upperLeft.y(), b.lowerRight.y())
				|| (in(b.upperLeft.x(), upperLeft.x(), lowerRight.x()) && in(b.upperLeft.y(), upperLeft.y(),
						lowerRight.y()));
	}

	/**
	 * A simple check within boundaries: boundLeft <= p <= boundRight
	 * @param p
	 * @param boundLeft
	 * @param boundRight
	 * @return
	 */
	private boolean in(double p, double boundLeft, double boundRight) {
		return boundLeft <= p && p <= boundRight;
	}

}
