package org.chaoticbits.collabcloud.codeprocessor;

import static org.junit.Assert.assertEquals;

import org.chaoticbits.collabcloud.CloudWeights;
import org.chaoticbits.collabcloud.ISummaryToken;
import org.chaoticbits.collabcloud.codeprocessor.java.JavaSummaryToken;
import org.junit.Test;

public class CloudWeightsTest {

	@Test
	public void testToString() throws Exception {
		CloudWeights weights = new CloudWeights();
		assertEquals("",weights.toString());
		weights.put(token("a"), 50.123);
		assertEquals("a:\t50.123\n",weights.toString());
	}

	private ISummaryToken token(String string) {
		return new JavaSummaryToken(null, "a", "a", null);
	}
}
