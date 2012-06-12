package org.chaoticbits.collabcloud.visualizer;

import java.awt.Shape;
import java.awt.geom.Rectangle2D;
import java.util.HashMap;
import java.util.Map;

public class AWTIntersector extends HierarchicalBoxIntersector<Shape> {
	private Map<ShapeBox, Boolean> cache = new HashMap<ShapeBox, Boolean>();

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

	// public static long boxIntersectMS = 0;

	@Override
	protected boolean intersects(Shape path, Rectangle2D box) {
		// long start = System.currentTimeMillis();
		ShapeBox shapeBox = new ShapeBox();
		shapeBox.box = box;
		shapeBox.shape = path;
		if (cache.containsKey(shapeBox))
			return cache.get(shapeBox);
		boolean intersects = path.intersects(box);
		cache.put(shapeBox, intersects);
		// boxIntersectMS += (System.currentTimeMillis() - start);
		return intersects;
	}

	private class ShapeBox {
		private Shape shape;
		private Rectangle2D box;

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getOuterType().hashCode();
			result = prime * result + ((box == null) ? 0 : box.hashCode());
			result = prime * result + ((shape == null) ? 0 : shape.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			ShapeBox other = (ShapeBox) obj;
			if (!getOuterType().equals(other.getOuterType()))
				return false;
			if (box == null) {
				if (other.box != null)
					return false;
			} else if (!box.equals(other.box))
				return false;
			if (shape == null) {
				if (other.shape != null)
					return false;
			} else if (!shape.equals(other.shape))
				return false;
			return true;
		}

		private AWTIntersector getOuterType() {
			return AWTIntersector.this;
		}

	}

}
