package org.chaoticbits.collabcloud.codeprocessor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.Set;

/**
 * A simple wrapper for the table of identifiers and mappings to weights
 * 
 * @author andy
 * 
 */
public class CloudWeights {
	private final Map<ISummaryToken, Double> weights = new LinkedHashMap<ISummaryToken, Double>();
	private final HashSet<String> excludeWords;

	public CloudWeights() {
		excludeWords = new HashSet<String>();
		Scanner scanner = new Scanner(getClass().getResourceAsStream("excludewords"));
		while (scanner.hasNextLine())
			excludeWords.add(scanner.nextLine());
	}

	/**
	 * Return the weight for the given summary token
	 * @param token
	 * @return
	 */
	public Double get(ISummaryToken token) {
		Double weight = weights.get(token);
		if (weight == null)
			return 0.0d;
		return weight;
	}

	/**
	 * Increments the weight for that identifier by the specified number. If the identifier does not exist,
	 * starts at 0.0+by. Checks the excludewords list as well
	 * @param token
	 * @param by
	 */
	public void increment(ISummaryToken token, double by) {
		if (!excludeWords.contains(token.getToken()))
			weights.put(token, get(token) + by);
	}

	/**
	 * Multiplies the weight for that identifier by the specified number. If the identifier does not exist,
	 * starts at 0.0*by. Checks the excludewords list as well
	 * @param token
	 * @param by
	 */
	public void put(ISummaryToken token, Double value) {
		weights.put(token, value);
	}

	@Override
	public String toString() {
		String str = "";
		for (Entry<ISummaryToken, Double> entry : weights.entrySet()) {
			str += entry.getKey().getFullName() + ":\t" + entry.getValue() + "\n";
		}
		return str;
	}

	/**
	 * Return a list of entries in the map, sorted by the weight, descending.
	 * @return
	 */
	public List<Entry<ISummaryToken, Double>> sortedEntries() {
		List<Entry<ISummaryToken, Double>> entries = new ArrayList<Map.Entry<ISummaryToken, Double>>();
		entries.addAll(weights.entrySet());
		Collections.sort(entries, new Comparator<Entry<ISummaryToken, Double>>() {
			public int compare(Entry<ISummaryToken, Double> o1, Entry<ISummaryToken, Double> o2) {
				return o2.getValue().compareTo(o1.getValue());
			}
		});
		return entries;
	}

	/**
	 * Return a list of entries in the map, as is
	 * @return
	 */
	public Set<Entry<ISummaryToken, Double>> unsortedEntries() {
		return weights.entrySet();
	}
}
