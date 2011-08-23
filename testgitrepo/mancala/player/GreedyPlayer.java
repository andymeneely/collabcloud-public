package org.chaoticbits.mancala.player;

import java.util.Random;

import org.chaoticbits.mancala.Board;
import org.chaoticbits.mancala.Side;

public class GreedyPlayer implements IPlayer {

	private final Side side;
	private final Random tieBreaker;

	public GreedyPlayer(Side side) {
		this.side = side;
		tieBreaker = new Random();
	}

	public GreedyPlayer(Side side, Random tieBreaker) {
		this.side = side;
		this.tieBreaker = tieBreaker;
	}

	@Override
	public int getPlay(Board state) {
		int[] scores = new int[6]; // scores for each play;
		for (int play = 0; play < Board.SLOT_WIDTH; play++) { //A modification for testing
			scores[play] = getScore(state.copy(), play); //Another modification for testing
		}
		return maxIndex(scores);
	}

	private int getScore(Board state, int play) {
		state.play(play);
		int score = state.getCala(side);
		if (side == state.currentTurn()) {
			Board copy = state.copy();
			score += getScore(copy, getPlay(copy));
		}
		return score;
	}

	private int maxIndex(int[] scores) {
		int highest = 0;
		int highestIndex = 0;
		for (int i = 0; i < scores.length; i++) {
			if (scores[i] == highest && tieBreaker.nextBoolean()) {
				highest = scores[i];
				highestIndex = i;
			}
			if (scores[i] > highest) {
				highest = scores[i];
				highestIndex = i;
			}
		}
		return highestIndex;
	}

	@Override
	public String getName() {
		return "Greedy player " + side.pretty();
	}

}
