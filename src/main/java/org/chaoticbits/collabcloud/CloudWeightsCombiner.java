package org.chaoticbits.collabcloud;

import org.chaoticbits.collabcloud.codeprocessor.IWeightCombiner;

public class CloudWeightsCombiner {

	private final IWeightCombiner iWeightCombiner;

	public CloudWeightsCombiner(IWeightCombiner iWeightCombiner) {
		this.iWeightCombiner = iWeightCombiner;
	}

	public CloudWeights combine(CloudWeights sourceCode, CloudWeights versionControl){
		CloudWeights combined = new CloudWeights();
		
		return combined;
	}
	
}
