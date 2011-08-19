package org.chaoticbits.collabcloud.codeprocessor;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.Map.Entry;

import org.junit.Test;

public class SortedEntriesTest {

	@Test
	public void sortedEntries() throws Exception {
		CloudWeights weights = new CloudWeights();
		weights.increment("second", 20);
		weights.increment("first", 25);
		weights.increment("fourth", 10);
		weights.increment("third", 15);
		List<Entry<String, Double>> sortedEntries = weights.sortedEntries();
		assertEquals("first", sortedEntries.get(0).getKey());
		assertEquals("check the value", 25.0, sortedEntries.get(0).getValue(),0.00001);
		
		assertEquals("second", sortedEntries.get(1).getKey());
		assertEquals("check the value", 20.0, sortedEntries.get(1).getValue(),0.00001);
		
		assertEquals("third", sortedEntries.get(2).getKey());
		assertEquals("check the value", 15.0, sortedEntries.get(2).getValue(),0.00001);
		
		assertEquals("fourth", sortedEntries.get(3).getKey());
		assertEquals("check the value", 10.0, sortedEntries.get(3).getValue(),0.00001);
	}
}
