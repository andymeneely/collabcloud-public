package org.chaoticbits.mancala.player;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.chaoticbits.mancala.Board;
import org.chaoticbits.mancala.Side;
import org.chaoticbits.mancala.player.support.MinimaxStrategy;

public class MinimaxPlayer implements IPlayer {

	private final Side side;
	private final int MAX_DEPTH;
	private final Map<Side, MinimaxStrategy> minimax = new HashMap<Side, MinimaxStrategy>();
	private final Random tieBreaker;

	public MinimaxPlayer(Side side, int depth) {
		this(side, new Random(), depth);
	}

	public MinimaxPlayer(Side side, Random tieBreaker, int depth) {
		this.side = side;
		this.tieBreaker = tieBreaker;
		this.MAX_DEPTH = depth;
		minimax.put(side, MinimaxStrategy.MAX);
		minimax.put(side.other(), MinimaxStrategy.MIN);
	}

	@Override
	public int getPlay(Board state) {
		int bestScore = Integer.MIN_VALUE;
		int bestPlay = -1;
		for (Integer play : state) {
			Board copy = state.copy().play(play);
			int score = getScore(copy, MAX_DEPTH);
			if (score == bestScore && tieBreaker.nextBoolean()) {
				bestScore = score;
				bestPlay = play;
			} else if (score > bestScore) {
				bestScore = score;
				bestPlay = play;
			}
		}
		return bestPlay;
	}

	private int getScore(Board state, int depth) {
		if (state.gameOver()) {
			state.finalizeScores();
			return evaluate(state);
		}
		if (depth == 0) {
			return evaluate(state);
		}

		MinimaxStrategy strategy = minimax.get(state.currentTurn());
		int bestScore = strategy.getInitBestScore();

		for (Integer play : state) {
			Board copy = state.copy().play(play);
			bestScore = strategy.getMinimax(getScore(copy, depth - 1), bestScore);
		}

		return bestScore;
	}

	private int evaluate(Board state) {
		return state.getCala(side) - state.getCala(side.other());
	}

	@Override
	public String getName() {
		return "Minimax player " + side.pretty();
	}
}
