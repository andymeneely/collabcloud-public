package org.chaoticbits.collabcloud.visualizer;

import java.awt.Shape;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

public class Intersector {

	private final int recursiveDepth;
	private boolean cutOffSmallLeaf = false;
	private double cutOffLeafSize = 1.0d;

	public Intersector() {
		recursiveDepth = 10;
	}

	public Intersector(int recursiveDepth) {
		this.recursiveDepth = recursiveDepth;
	}

	public Intersector(int recursiveDepth, double cutOffLeafSize) {
		this.recursiveDepth = recursiveDepth;
		cutOffSmallLeaf = true;
		this.cutOffLeafSize = cutOffLeafSize;
	}

	public boolean intersect(Shape a, Shape b) {
		Rectangle2D aBounds = a.getBounds2D();
		Rectangle2D bBounds = b.getBounds2D();
		return intersectsRecursive(a, aBounds, b, bBounds, recursiveDepth);
	}

	private boolean intersectsRecursive(Shape a, Rectangle2D aBox, Shape b, Rectangle2D bBox, int depth) {
		if (depth <= 0)
			return boxesIntersect(aBox, bBox); // hit our depth check - call it at whatever the boxes say
		if (cutOffSmallLeaf
				&& (aBox.getWidth() < cutOffLeafSize || aBox.getHeight() < cutOffLeafSize || bBox.getWidth() < cutOffLeafSize || bBox
						.getHeight() < cutOffLeafSize))
			return boxesIntersect(aBox, bBox); // box is very small now - call it at the boxes now
		if (!a.intersects(aBox) || !b.intersects(bBox))
			return false; // One box is just whitespace, no intersect here, prune!!
		if (!boxesIntersect(aBox, bBox))
			return false; // Boxes don't intersect each other, no intersect here, prune!

		// Ok, shapes intersect the boxes and boxes intersect each other - keep diving down
		List<Rectangle2D> aBoxes = makeBoxes(aBox);
		List<Rectangle2D> bBoxes = makeBoxes(bBox);
		// check each sub-box with each other sub-box
		for (Rectangle2D aSubBox : aBoxes)
			for (Rectangle2D bSubBox : bBoxes)
				if (intersectsRecursive(a, aSubBox, b, bSubBox, depth - 1))
					return true; // Found a hit recursively. Done!
		return false; // no such intersections found recursively - we're done!
	}

	private boolean boxesIntersect(Rectangle2D aBox, Rectangle2D bBox) {
		return aBox.intersects(bBox);
	}

	private List<Rectangle2D> makeBoxes(Rectangle2D box) {
		List<Rectangle2D> boxes = new ArrayList<Rectangle2D>(4);
		boxes.add(new Rectangle2D.Double(box.getX(), box.getY(), box.getWidth() / 2.0d, box.getHeight() / 2.0d)); // UL
		boxes.add(new Rectangle2D.Double(box.getCenterX(), box.getY(), box.getWidth() / 2.0d, box.getHeight() / 2.0d));// UR
		boxes.add(new Rectangle2D.Double(box.getX(), box.getCenterY(), box.getWidth() / 2.0d, box.getHeight() / 2.0d)); // LL
		boxes.add(new Rectangle2D.Double(box.getCenterX(), box.getCenterY(), box.getWidth() / 2.0d, box.getHeight() / 2.0d));// LR
		return boxes;
	}
}
