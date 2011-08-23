package org.chaoticbits.mancala.player.support;

abstract public class MinimaxStrategy {

	abstract public int getMinimax(int a, int b);

	abstract public int getInitBestScore();

	public static Max MAX = new Max();
	public static Min MIN = new Min();

	public static class Max extends MinimaxStrategy {

		@Override
		public int getInitBestScore() {
			return Integer.MIN_VALUE;
		}

		@Override
		public int getMinimax(int a, int b) {
			return Math.max(a, b);
		}
	}

	public static class Min extends MinimaxStrategy {

		@Override
		public int getInitBestScore() {
			return Integer.MAX_VALUE;
		}

		@Override
		public int getMinimax(int a, int b) {
			return Math.min(a, b);
		}

	}
}
