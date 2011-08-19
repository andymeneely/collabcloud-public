package org.chaoticbits.collabcloud.codeprocessor;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Map.Entry;

/**
 * A simple wrapper for the table of identifiers and mappings to weights
 * 
 * @author andy
 * 
 */
public class CloudWeights {
	private Map<String, Double> weights = new LinkedHashMap<String, Double>();
	private HashSet<String> excludeWords;

	public CloudWeights() {
		excludeWords = new HashSet<String>();
		Scanner scanner = new Scanner(getClass().getResourceAsStream("excludewords"));
		while (scanner.hasNextLine())
			excludeWords.add(scanner.nextLine());
	}

	public Double get(String identifier) {
		Double weight = weights.get(identifier);
		if (weight == null)
			return 0.0d;
		return weight;
	}

	/**
	 * Increments the weight for that identifier by the specified number. If the identifier does not exist,
	 * starts at 0.0+by. Checks the excludewords list as well
	 * @param identifier
	 * @param by
	 */
	public void increment(String identifier, double by) {
		if (!excludeWords.contains(identifier))
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
