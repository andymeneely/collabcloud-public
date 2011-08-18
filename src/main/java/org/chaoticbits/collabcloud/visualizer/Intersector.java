package org.chaoticbits.collabcloud.visualizer;

import java.awt.Shape;
import java.awt.geom.Rectangle2D;

public class Intersector {

	private final int recursiveDepth;

	public Intersector() {
		recursiveDepth = 20;
	}

	public Intersector(int recursiveDepth) {
		this.recursiveDepth = recursiveDepth;
	}

	public boolean intersect(Shape a, Shape b) {
		Rectangle2D aBounds = a.getBounds2D();
		Rectangle2D bBounds = b.getBounds2D();
		return intersectsRecursive(a, aBounds, b, bBounds, recursiveDepth);
	}

	private boolean intersectsRecursive(Shape a, Rectangle2D aBox, Shape b, Rectangle2D bBox, int depth) {
		if (depth <= 0)
			return true; // hit our depth check - call it a yes
		if (!a.intersects(aBox) || !b.intersects(bBox))
			return false; // One box is just whitespace, no intersect here, prune!!
		if (!aBox.intersects(bBox))
			return false; // Boxes don't intersect each other, no intersect here, prune!

		// Ok, shapes intersect the boxes and boxes intersect each other - keep diving down
		Rectangle2D aBoxUL = new Rectangle2D.Double(aBox.getX(), aBox.getY(), aBox.getWidth() / 2.0d, aBox.getHeight() / 2.0d);
		Rectangle2D bBoxUL = new Rectangle2D.Double(bBox.getX(), bBox.getY(), bBox.getWidth() / 2.0d, bBox.getHeight() / 2.0d);

		Rectangle2D aBoxUR = new Rectangle2D.Double(aBox.getCenterX(), aBox.getY(), aBox.getWidth() / 2.0d, aBox.getHeight() / 2.0d);
		Rectangle2D bBoxUR = new Rectangle2D.Double(bBox.getCenterX(), bBox.getY(), bBox.getWidth() / 2.0d, bBox.getHeight() / 2.0d);

		Rectangle2D aBoxLL = new Rectangle2D.Double(aBox.getX(), aBox.getCenterY(), aBox.getWidth() / 2.0d, aBox.getHeight() / 2.0d);
		Rectangle2D bBoxLL = new Rectangle2D.Double(bBox.getX(), bBox.getCenterY(), bBox.getWidth() / 2.0d, bBox.getHeight() / 2.0d);

		Rectangle2D aBoxLR = new Rectangle2D.Double(aBox.getCenterX(), aBox.getCenterY(), aBox.getWidth() / 2.0d, aBox.getHeight() / 2.0d);
		Rectangle2D bBoxLR = new Rectangle2D.Double(bBox.getCenterX(), bBox.getCenterY(), bBox.getWidth() / 2.0d, bBox.getHeight() / 2.0d);

		boolean intersectsRecursiveUL = intersectsRecursive(a, aBoxUL, b, bBoxUL, depth - 1);
		boolean intersectsRecursiveUR = intersectsRecursive(a, aBoxUR, b, bBoxUR, depth - 1);
		boolean intersectsRecursiveLL = intersectsRecursive(a, aBoxLL, b, bBoxLL, depth - 1);
		boolean intersectsRecursiveLR = intersectsRecursive(a, aBoxLR, b, bBoxLR, depth - 1);
		return intersectsRecursiveUL || intersectsRecursiveUR || intersectsRecursiveLL || intersectsRecursiveLR;
	}
}
