package org.chaoticbits.collabcloud.visualizer.place;

import java.awt.geom.Point2D;

import org.chaoticbits.collabcloud.codeprocessor.ISummaryToken;

public interface IPlaceStrategy {

	abstract public Point2D getStartingPlace(ISummaryToken token);
}
