package org.chaoticbits.collabcloud.visualizer.placement;

import java.awt.Shape;
import java.awt.geom.Point2D;

import org.chaoticbits.collabcloud.codeprocessor.ISummaryToken;

public interface IPlaceStrategy {

	abstract public Point2D getStartingPlace(ISummaryToken token, Shape shape);
}
