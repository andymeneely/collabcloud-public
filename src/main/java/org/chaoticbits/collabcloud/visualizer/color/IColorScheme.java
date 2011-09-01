package org.chaoticbits.collabcloud.visualizer.color;

import java.awt.Color;

import org.chaoticbits.collabcloud.codeprocessor.CloudWeights;
import org.chaoticbits.collabcloud.codeprocessor.ISummaryToken;

public interface IColorScheme {

	abstract public Color lookup(ISummaryToken token, CloudWeights weights); 
	
}
