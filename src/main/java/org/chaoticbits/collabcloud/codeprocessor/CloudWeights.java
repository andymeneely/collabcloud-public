package org.chaoticbits.collabcloud.codeprocessor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Map.Entry;
import java.util.Set;

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

	/**
	 * Multiplies the weight for that identifier by the specified number. If the identifier does not exist,
	 * starts at 0.0*by. Checks the excludewords list as well
	 * @param identifier
	 * @param by
	 */
	public void multiply(String identifier, double by) {
		if (!excludeWords.contains(identifier))
			weights.put(identifier, get(identifier) * by);
	}

	@Override
	public String toString() {
		String str = "";
		for (Entry<String, Double> entry : weights.entrySet()) {
			str += entry.getKey() + ":\t" + entry.getValue() + "\n";
		}
		return str;
	}

	/**
	 * Return a list of entries in the map, sorted by the weight, descending.
	 * @return
	 */
	public List<Entry<String, Double>> sortedEntries() {
		List<Entry<String, Double>> entries = new ArrayList<Map.Entry<String, Double>>();
		entries.addAll(weights.entrySet());
		Collections.sort(entries, new Comparator<Entry<String, Double>>() {
			public int compare(Entry<String, Double> o1, Entry<String, Double> o2) {
				return o2.getValue().compareTo(o1.getValue());
			}
		});
		return entries;
	}

	/**
	 * Return a list of entries in the map, as is
	 * @return
	 */
	public Set<Entry<String, Double>> unsortedEntries() {
		return weights.entrySet();
	}
}
