package org.chaoticbits.collabcloud;

import java.io.File;

import org.chaoticbits.collabcloud.codeprocessor.IWeightCombiner;
import org.chaoticbits.collabcloud.codeprocessor.IWeightModifier;
import org.chaoticbits.collabcloud.codeprocessor.java.JavaClassSummarizable;
import org.chaoticbits.collabcloud.codeprocessor.java.JavaSummaryToken;
import org.chaoticbits.collabcloud.codeprocessor.java.JavaTokenType;
import org.chaoticbits.collabcloud.vc.DiffToken;
import org.junit.Test;

public class CloudWeightsCombinerTest {

	@Test
	public void combineMultiple() throws Exception {
		ISummarizable summarizableA = new JavaClassSummarizable(new File("a"));
		ISummarizable summarizableB = new JavaClassSummarizable(new File("b"));
		ISummarizable summarizableC = new JavaClassSummarizable(new File("c"));

		CloudWeights source = new CloudWeights();
		source.put(new JavaSummaryToken(summarizableA, "full name A", "token A", JavaTokenType.CLASS), 10d);
		source.put(new JavaSummaryToken(summarizableB, "full name C", "token C", JavaTokenType.CLASS), 20d);
		source.put(new JavaSummaryToken(summarizableB, "full name C", "token C", JavaTokenType.CLASS), 30d);

		CloudWeights vc = new CloudWeights();
		source.put(new DiffToken(summarizableA, "token A", "full name A"), 40d);
		source.put(new DiffToken(summarizableB, "token B", "full name B"), 50d);

		new CloudWeightsCombiner(new IWeightCombiner() {
			public Double combine(Double first, Double second) {
				return first * second;
			}
		}).combine(source, vc);
	}

}
