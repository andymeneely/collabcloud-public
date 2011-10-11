package org.chaoticbits.mancala.player;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.chaoticbits.mancala.Board;
import org.chaoticbits.mancala.Side;
import org.chaoticbits.mancala.player.Log.Level;
import org.chaoticbits.mancala.player.support.MinimaxStrategy;

public class TimedAlphaBetaPlayer implements IPlayer {

	private static final int MAX_DEPTH = 30;
	private static final long TIME_TOLERANCE_MS = 50;
	private final Side side;
	private final Map<Side, MinimaxStrategy> minimax = new HashMap<Side, MinimaxStrategy>();
	private final Random tieBreaker;
	private final long timeLimit;
	private long startTime;
	private long visited;
	public long totalVisited;
	private Log log;

	public TimedAlphaBetaPlayer(Side side, long timeLimit) {
		this(side, new Random(), timeLimit);
	}

	public TimedAlphaBetaPlayer(Side side, Random tieBreaker, long timeLimit) {
		this.side = side;
		this.tieBreaker = tieBreaker;
		this.timeLimit = timeLimit;
		this.log = new Log(Level.NONE);
		minimax.put(side, MinimaxStrategy.MAX);
		minimax.put(side.other(), MinimaxStrategy.MIN);
	}

	public void setLog(Log log) {
		this.log = log;
	}

	@Override
	public int getPlay(Board state) {
		int bestScore = Integer.MIN_VALUE;
		int bestPlay = -1;
		startTime = System.currentTimeMillis();
		visited = 0;
		totalVisited = 0;
		int depthAchieved = 1;
		// iterative deepening
		for (int depth = 3; depth < MAX_DEPTH && timeLeft() > TIME_TOLERANCE_MS; depth++) {
			// log.println(Level.VERBOSE, "Time Left: " + timeLeft() + ", depth: " + depth);
			int bestPlayThisDepth = -1;
			int bestScoreThisDepth = Integer.MIN_VALUE;
			for (Integer play : state) {
				Board copy = state.copy().play(play);
				int score = getScore(copy, depth, Integer.MIN_VALUE, Integer.MAX_VALUE);
				if (score == bestScoreThisDepth && tieBreaker.nextBoolean()) {
					bestScoreThisDepth = score;
					bestPlayThisDepth = play;
				} else if (score > bestScoreThisDepth) {
					bestScoreThisDepth = score;
					bestPlayThisDepth = play;
				}
			}
			bestPlay = bestPlayThisDepth;
			// log.println(Level.VERBOSE, "\tVisits for depth " + depth + ": " + visited);
			// log.println(Level.VERBOSE, "\tTotal visits: " + totalVisited);
			visited = 0;
			depthAchieved = depth;
		}
		log.println(Level.VERBOSE, "\t " + getName() + " got to level " + depthAchieved);
		return bestPlay;
	}

	private int getScore(Board state, int depth, int alpha, int beta) {
		visited++;
		totalVisited++;
		if (state.gameOver()) {
			state.finalizeScores();
			return evaluate(state);
		}
		if (depth == 0 || timeLeft() < TIME_TOLERANCE_MS) {
			return evaluate(state);
		}

		if (state.currentTurn() == side) {
			for (Integer play : state) {
				int score = getScore(state.copy().play(play), depth - 1, alpha, beta);
				alpha = Math.max(alpha, score);
				if (alpha >= beta)
					return alpha;
			}
			return alpha;
		} else {
			for (Integer play : state) {
				int score = getScore(state.copy().play(play), depth - 1, alpha, beta);
				beta = Math.min(beta, score);
				if (alpha >= beta)
					return beta;
			}
			return beta;
		}
	}

	private long timeLeft() {
		return timeLimit - (System.currentTimeMillis() - startTime);
	}

	private int evaluate(Board state) {
		return state.getCala(side) - state.getCala(side.other());
	}

	@Override
	public String getName() {
		return "Timed Alpha Beta player " + side.pretty();
	}
}
