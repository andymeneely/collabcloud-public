package org.chaoticbits.collabcloud.visualizer.color;

import java.awt.Color;

import org.chaoticbits.collabcloud.CloudWeights;
import org.chaoticbits.collabcloud.ISummaryToken;

public interface IColorScheme {

	abstract public Color lookup(ISummaryToken token, CloudWeights weights); 
	
}
