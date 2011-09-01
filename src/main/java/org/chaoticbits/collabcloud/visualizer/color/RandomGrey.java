package org.chaoticbits.collabcloud.visualizer.color;

import java.awt.Color;
import java.util.Random;

import org.chaoticbits.collabcloud.codeprocessor.CloudWeights;
import org.chaoticbits.collabcloud.codeprocessor.ISummaryToken;

public class RandomGrey implements IColorScheme {

	private final Random rand;
	private final int min;
	private final int max;

	public RandomGrey(Random rand, int min, int max) {
		this.rand = rand;
		this.min = min;
		this.max = max;
	}

	public Color lookup(ISummaryToken token, CloudWeights weights) {
		int grey = rand.nextInt(max - min) + min;
		return new Color(grey, grey, grey);
	}

}
