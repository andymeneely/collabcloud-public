package org.chaoticbits.mancala.player;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.chaoticbits.mancala.Board;
import org.chaoticbits.mancala.Side;
import org.chaoticbits.mancala.player.Log.Level;
import org.chaoticbits.mancala.player.support.MinimaxStrategy;

public class TimedMinimaxPlayer implements IPlayer {
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

	public TimedMinimaxPlayer(Side side, long timeLimit) {
		this(side, new Random(), timeLimit);
	}

	public TimedMinimaxPlayer(Side side, Random tieBreaker, long timeLimit) {
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
		for (int depth = 1; depth < MAX_DEPTH && timeLeft() > TIME_TOLERANCE_MS; depth++) { 
//			log.println(Level.VERBOSE, "Time Left: " + timeLeft() + ", depth: " + depth);
			for (Integer play : state) {
				Board copy = state.copy().play(play);
				int score = getScore(copy, depth);
				if (score == bestScore && tieBreaker.nextBoolean()) {
					bestScore = score;
					bestPlay = play;
				} else if (score > bestScore) {
					bestScore = score;
					bestPlay = play;
				}
			}
//			log.println(Level.VERBOSE, "\tVisits for depth " + depth + ": " + visited);
//			log.println(Level.VERBOSE, "\tTotal visits: " + totalVisited);
			visited = 0;
			depthAchieved = depth;
		}
		log.println(Level.VERBOSE, "\t " + getName() + " got to level " + depthAchieved);
		return bestPlay;
	}

	private int getScore(Board state, int depth) {
		visited++;
		totalVisited++;
		if (state.gameOver()) {
			state.finalizeScores();
			return evaluate(state);
		}
		if (depth == 0 || timeLeft() < TIME_TOLERANCE_MS) {
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

	private long timeLeft() {
		return timeLimit - (System.currentTimeMillis() - startTime);
	}

	private int evaluate(Board state) {
		return state.getCala(side) - state.getCala(side.other());
	}
	
	@Override
	public String getName() {
		return "Timed Minimax player " + side.pretty();
	}
}
