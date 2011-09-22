package org.chaoticbits.collabcloud.visualizer.font;

import org.apache.commons.collections15.Transformer;

public class MathTransforms {

	/**
	 * Square the number
	 * @return
	 */
	public static final Transformer<Double, Double> square = new Transformer<Double, Double>() {
		public Double transform(Double input) {
			return input * input;
		}
	};

	/**
	 * Cube the number
	 * @return
	 */
	public static final Transformer<Double, Double> cube = new Transformer<Double, Double>() {
		public Double transform(Double input) {
			return input * input * input;
		}
	};

	/**
	 * Cube the number
	 * @return
	 */
	public static final Transformer<Double, Double> fourthPower = new Transformer<Double, Double>() {
		public Double transform(Double input) {
			return input * input * input * input;
		}
	};

}
