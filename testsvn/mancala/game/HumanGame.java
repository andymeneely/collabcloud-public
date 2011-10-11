package org.chaoticbits.mancala.game;

import static org.chaoticbits.mancala.Side.NORTH;
import static org.chaoticbits.mancala.Side.SOUTH;

import java.util.Random;

import org.chaoticbits.mancala.player.HumanPlayer;
import org.chaoticbits.mancala.player.IPlayer;
import org.chaoticbits.mancala.player.Log;
import org.chaoticbits.mancala.player.TimedMinimaxPlayer;

public class HumanGame {

	private static Random rnd = new Random();

	public static void main(String[] args) {
		// MinimaxPlayer opponent = new MinimaxPlayer(NORTH, rnd, 7);
		IPlayer opponent = new TimedMinimaxPlayer(NORTH, rnd, 1000);
		Game game = new Game(opponent, new HumanPlayer(SOUTH), Log.Level.VERBOSE);
		game.run();
	}
}
