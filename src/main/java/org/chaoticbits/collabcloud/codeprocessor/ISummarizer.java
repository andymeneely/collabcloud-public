package org.chaoticbits.collabcloud.codeprocessor;

public interface ISummarizer {

	abstract public CloudWeights summarize(ISummarizable artifact);
}
