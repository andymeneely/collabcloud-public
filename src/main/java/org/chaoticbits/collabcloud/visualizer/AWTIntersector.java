package org.chaoticbits.collabcloud.visualizer;

import java.awt.Shape;
import java.awt.geom.Rectangle2D;

public class AWTIntersector extends HierarchicalBoxIntersector<Shape> {

	public AWTIntersector() {
		super();
	}

	public AWTIntersector(int recursiveDepth) {
		super(recursiveDepth);
	}

	public AWTIntersector(int recursiveDepth, double cutOffLeafSize) {
		super(recursiveDepth, cutOffLeafSize);
	}

	@Override
	protected Rectangle2D getBounds2D(Shape path) {
		return path.getBounds2D();
	}

	@Override
	protected boolean intersects(Shape path, Rectangle2D box) {
		return path.intersects(box);
	}

}
