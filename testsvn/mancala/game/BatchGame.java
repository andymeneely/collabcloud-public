package org.chaoticbits.mancala.game;

import static org.chaoticbits.mancala.Side.NORTH;
import static org.chaoticbits.mancala.Side.SOUTH;

import java.util.Random;

import org.chaoticbits.mancala.player.Log;
import org.chaoticbits.mancala.player.TimedMinimaxPlayer;
import org.chaoticbits.mancala.player.TimedNegaScoutPlayer;

public class BatchGame {
	public static void main(String[] args) {
		Random rnd = new Random();
		int[] wins = new int[2];
		int games = 30;
		for (int i = 0; i < games; i++) {
			// Game game = new Game(new RandomPlayer(rnd, NORTH), new GreedyPlayer(SOUTH, rnd));
			// Game game = new Game(new RandomPlayer(rnd, NORTH), new MinimaxPlayer(SOUTH, rnd, 6));
			// Game game = new Game(new GreedyPlayer(NORTH, rnd), new MinimaxPlayer(SOUTH, rnd, 5));
			// Game game = new Game(new MinimaxPlayer(NORTH, rnd, 8), new TimedMinimaxPlayer(SOUTH,
			TimedNegaScoutPlayer north = new TimedNegaScoutPlayer(NORTH, rnd, 700);
			north.setLog(new Log(Log.Level.VERBOSE));

			TimedMinimaxPlayer south = new TimedMinimaxPlayer(SOUTH, rnd, 500);
			south.setLog(new Log(Log.Level.VERBOSE));

			Game game = new Game(north, south);
			game.run();
			wins[game.getWinner().index()]++;
		}
		System.out.println("North: " + wins[NORTH.index()] + " / " + games);
		System.out.println("South: " + wins[SOUTH.index()] + " / " + games);
	}
}
