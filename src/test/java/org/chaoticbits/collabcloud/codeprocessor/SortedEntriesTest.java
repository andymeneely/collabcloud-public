package org.chaoticbits.collabcloud.codeprocessor;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.Map.Entry;

import org.chaoticbits.collabcloud.CloudWeights;
import org.chaoticbits.collabcloud.ISummaryToken;
import org.chaoticbits.collabcloud.codeprocessor.java.JavaSummaryToken;
import org.junit.Test;

public class SortedEntriesTest {

	@Test
	public void sortedEntries() throws Exception {
		CloudWeights weights = new CloudWeights();
		weights.increment(token("second"), 20);
		weights.increment(token("first"), 25);
		weights.increment(token("fourth"), 10);
		weights.increment(token("third"), 15);
		List<Entry<ISummaryToken, Double>> sortedEntries = weights.sortedEntries();
		assertEquals("first", sortedEntries.get(0).getKey().getToken());
		assertEquals("check the value", 25.0, sortedEntries.get(0).getValue(), 0.00001);

		assertEquals("second", sortedEntries.get(1).getKey().getToken());
		assertEquals("check the value", 20.0, sortedEntries.get(1).getValue(), 0.00001);

		assertEquals("third", sortedEntries.get(2).getKey().getToken());
		assertEquals("check the value", 15.0, sortedEntries.get(2).getValue(), 0.00001);

		assertEquals("fourth", sortedEntries.get(3).getKey().getToken());
		assertEquals("check the value", 10.0, sortedEntries.get(3).getValue(), 0.00001);
	}

	private JavaSummaryToken token(String token) {
		return new JavaSummaryToken(null, null, token, null);
	}
}
