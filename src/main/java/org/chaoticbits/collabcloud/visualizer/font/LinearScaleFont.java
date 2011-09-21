package org.chaoticbits.collabcloud.visualizer.font;

import java.awt.Font;
import java.util.Map.Entry;
import java.util.Set;

import org.chaoticbits.collabcloud.CloudWeights;
import org.chaoticbits.collabcloud.ISummaryToken;

public class LinearScaleFont implements IFontTransformer {
	private static org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(LinearScaleFont.class);
	private final double multiplier;
	private final Font initialFont;

	public LinearScaleFont(Font initialFont, CloudWeights weights, double maxFontSize) {
		this.initialFont = initialFont;
		Set<Entry<ISummaryToken, Double>> unsortedEntries = weights.unsortedEntries();
		double max = 0.0d;
		for (Entry<ISummaryToken, Double> entry : unsortedEntries) {
			if (max < entry.getValue())
				max = entry.getValue();
		}
		multiplier = maxFontSize / max;
	}

	public Font transform(ISummaryToken token, Double weight) {
		float fontSize = (float) (multiplier * weight);
		log.trace(token.getToken() + " gets font " + fontSize);
		return initialFont.deriveFont(fontSize);
	}
}
