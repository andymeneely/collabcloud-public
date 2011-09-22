package org.chaoticbits.collabcloud.visualizer.font;

import java.awt.Font;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.collections15.Transformer;
import org.chaoticbits.collabcloud.CloudWeights;
import org.chaoticbits.collabcloud.ISummaryToken;

public class NonParametricFont implements IFontTransformer {

	private final Font initialFont;
	private final Map<ISummaryToken, Double> rankMap;
	private double multiplier = 1.0;
	private final Transformer<Double, Double> rankTransform;

	public NonParametricFont(Font initialFont, CloudWeights weights, Transformer<Double, Double> rankTransform, double maxFontSize) {
		this.initialFont = initialFont;
		this.rankTransform = rankTransform;
		rankMap = new HashMap<ISummaryToken, Double>(weights.unsortedEntries().size());
		List<Entry<ISummaryToken, Double>> entries = weights.sortedEntries();
		double rank = entries.size();
		for (Entry<ISummaryToken, Double> entry : entries) {
			rankMap.put(entry.getKey(), rank--);
		}
		multiplier = maxFontSize / rankTransform.transform((double) entries.size());
	}

	public Font transform(ISummaryToken token, Double weight) {
		return initialFont.deriveFont((float) (multiplier * rankTransform.transform(rankMap.get(token))));
	}
}
