package org.chaoticbits.collabcloud.codeprocessor;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * A simple wrapper for the table of identifiers and mappings to weights
 * 
 * @author andy
 * 
 */
public class CloudWeights {
	private Map<String, Double> weights = new LinkedHashMap<String, Double>();

	public CloudWeights() {}

	public Double get(String identifier) {
		Double weight = weights.get(identifier);
		if (weight == null)
			return 0.0d;
		return weight;
	}

	/**
	 * Increments the weight for that identifier by 1.0. If the identifier does not exist, starts at 1.0.
	 * @param identifier
	 */
	public void increment(String identifier) {
		weights.put(identifier, get(identifier) + 1.0d);
	}

}
