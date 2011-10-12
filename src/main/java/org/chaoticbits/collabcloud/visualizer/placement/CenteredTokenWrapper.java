package org.chaoticbits.collabcloud.visualizer.placement;

import java.awt.Shape;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import org.chaoticbits.collabcloud.ISummaryToken;

/**
 * Calls the inner placement strategy, but then moves the token up and to the
 * left so that the placement is on the center, not the upper-left
 * 
 * @author Andy
 * 
 */
public class CenteredTokenWrapper implements IPlaceStrategy {
	private final IPlaceStrategy place;

	public CenteredTokenWrapper(IPlaceStrategy place) {
		this.place = place;
	}

	//TODO Remove the Shape from this method
	public Point2D getStartingPlace(ISummaryToken token, Shape shape) {
		Point2D ul = place.getStartingPlace(token, shape);
		Rectangle2D bounds = shape.getBounds2D();
		return new Point2D.Double(ul.getX() - bounds.getWidth() / 2, ul.getY() - bounds.getHeight() / 2);
	}

}
