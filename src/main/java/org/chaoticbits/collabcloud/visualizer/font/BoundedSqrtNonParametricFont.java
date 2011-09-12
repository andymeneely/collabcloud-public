package org.chaoticbits.collabcloud.visualizer.font;

import java.awt.Font;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.chaoticbits.collabcloud.codeprocessor.CloudWeights;
import org.chaoticbits.collabcloud.codeprocessor.ISummaryToken;

public class BoundedSqrtNonParametricFont implements IFontTransformer {

	private final Font initialFont;
	private final Map<ISummaryToken, Integer> rankMap = new HashMap<ISummaryToken, Integer>();
	private double multiplier = 1.0;

	public BoundedSqrtNonParametricFont(Font initialFont, CloudWeights weights, double maxFontSize) {
		this.initialFont = initialFont;
		List<Entry<ISummaryToken, Double>> entries = weights.sortedEntries();
		int rank = entries.size();
		for (Entry<ISummaryToken, Double> entry : entries) {
			rankMap.put(entry.getKey(), rank--);
		}
		multiplier = maxFontSize / Math.cbrt(entries.size());
	}

	public Font transform(ISummaryToken token, Double weight) {
		return initialFont.deriveFont((float) (multiplier * Math.cbrt(rankMap.get(token))));
	}
}
