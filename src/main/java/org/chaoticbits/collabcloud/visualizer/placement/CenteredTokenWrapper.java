package org.chaoticbits.collabcloud.visualizer.placement;

import java.awt.Font;
import java.awt.Shape;
import java.awt.font.FontRenderContext;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import org.chaoticbits.collabcloud.codeprocessor.ISummaryToken;

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

	public Point2D getStartingPlace(ISummaryToken token, Shape shape) {
		Point2D ul = place.getStartingPlace(token, shape);
		Rectangle2D bounds = shape.getBounds2D();
		return new Point2D.Double(ul.getX() - bounds.getWidth() / 2, ul.getY() - bounds.getHeight() / 2);
	}

}
