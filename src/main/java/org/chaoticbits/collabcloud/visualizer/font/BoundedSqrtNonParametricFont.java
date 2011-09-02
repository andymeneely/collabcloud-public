package org.chaoticbits.collabcloud.visualizer.font;

import java.awt.Font;

import org.chaoticbits.collabcloud.codeprocessor.CloudWeights;

public class BoundedSqrtNonParametricFont implements IFontTransformer {

	private final double additive;
	private final double multiplier;
	private final Font initialFont;

	public BoundedSqrtNonParametricFont(Font initialFont, CloudWeights weights, double maxFontSize) {
		this.initialFont = initialFont;
//		List<Entry<ISummaryToken, Double>> entries = weights.sortedEntries();
//		double rank = entries.size();
//		for (Entry<ISummaryToken, Double> entry : unsortedEntries) {
//			if (max < entry.getValue())
//				max = entry.getValue();
//			if (entry.getValue() < minBelowZero)
//				minBelowZero = entry.getValue();
//		}
//		multiplier = maxFontSize / Math.sqrt(max);
//		additive = minBelowZero;
		throw new IllegalStateException("unimplemented!");
	}

	public Font transform(Double weight) {
		return initialFont.deriveFont((float) (multiplier * (Math.sqrt(weight + additive))));
	}
}
