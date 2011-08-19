package org.chaoticbits.collabcloud.codeprocessor;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

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
	 * Increments the weight for that identifier by the specified number. If the identifier does not exist, starts at 0.0+by.
	 * @param identifier
	 * @param by
	 */
	public void increment(String identifier, double by) {
		weights.put(identifier, get(identifier) + by);
	}

	@Override
	public String toString() {
		String str = "";
		for (Entry<String, Double> entry : weights.entrySet()) {
			str += entry.getKey() + ":\t" + entry.getValue() + "\n";
		}
		return str;
	}

}
