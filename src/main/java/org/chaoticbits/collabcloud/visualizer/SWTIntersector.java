package org.chaoticbits.collabcloud.visualizer;

import java.awt.geom.Rectangle2D;

import org.eclipse.draw2d.Ellipse;
import org.eclipse.draw2d.Polyline;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.graphics.Path;

public class SWTIntersector extends HierarchicalBoxIntersector<Path> {

	public SWTIntersector() {
		super();
	}

	public SWTIntersector(int recursiveDepth) {
		super(recursiveDepth);
	}

	public SWTIntersector(int recursiveDepth, double cutOffLeafSize) {
		super(recursiveDepth, cutOffLeafSize);
	}

	@Override
	protected Rectangle2D getBounds2D(Path path) {
		float[] b = new float[4];
		path.getBounds(b);
		return new Rectangle2D.Float(b[0], b[1], b[2], b[3]);
	}

	@Override
	protected boolean intersects(Path path, Rectangle2D box) {
		
		throw new IllegalStateException("unimplemented!");
	}

}
