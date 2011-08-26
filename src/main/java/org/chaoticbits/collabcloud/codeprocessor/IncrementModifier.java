package org.chaoticbits.collabcloud.codeprocessor;

public class IncrementModifier implements IWeightModifier {

	private final Double by;

	public IncrementModifier(Double by) {
		this.by = by;
	}

	public Double modify(Double value) {
		return value + by;
	}

}
