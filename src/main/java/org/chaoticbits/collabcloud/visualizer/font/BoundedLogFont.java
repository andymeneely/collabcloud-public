package org.chaoticbits.collabcloud.visualizer.font;

import java.awt.Font;
import java.util.Map.Entry;
import java.util.Set;

import org.chaoticbits.collabcloud.codeprocessor.CloudWeights;
import org.chaoticbits.collabcloud.codeprocessor.ISummaryToken;

public class BoundedLogFont implements IFontTransformer {

	private final double additive;
	private final double multiplier;
	private final Font initialFont;

	public BoundedLogFont(Font initialFont, CloudWeights weights, double maxFontSize) {
		this.initialFont = initialFont;
		Set<Entry<ISummaryToken, Double>> unsortedEntries = weights.unsortedEntries();
		double max = 0.0d;
		double minBelowZero = 0.0;
		for (Entry<ISummaryToken, Double> entry : unsortedEntries) {
			if (max < entry.getValue())
				max = entry.getValue();
			if (entry.getValue() < minBelowZero)
				minBelowZero = entry.getValue();
		}
		multiplier = maxFontSize / Math.log(max);
		additive = minBelowZero;
	}

	public Font transform(Double weight) {
		return initialFont.deriveFont((float) (multiplier * (Math.log(weight + additive))));
	}
}
