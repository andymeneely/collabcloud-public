package org.chaoticbits.collabcloud.codeprocessor;

public class MultiplyModifier implements IWeightModifier {
	private final Double multiplier;

	public MultiplyModifier(Double multiplier) {
		this.multiplier = multiplier;
	}

	public Double modify(Double value) {
		return value * multiplier;
	}

}
