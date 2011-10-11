package org.chaoticbits.mancala.game;

import static org.chaoticbits.mancala.Side.NORTH;
import static org.chaoticbits.mancala.Side.SOUTH;

import java.util.HashMap;
import java.util.Map;

import org.chaoticbits.mancala.Board;
import org.chaoticbits.mancala.Side;
import org.chaoticbits.mancala.player.IPlayer;
import org.chaoticbits.mancala.player.Log;
import static org.chaoticbits.mancala.player.Log.Level.*;

public class Game implements Runnable {

	private Map<Side, IPlayer> players = new HashMap<Side, IPlayer>();
	private Board board;
	private Side winner;
	private Log log;

	public Game(IPlayer north, IPlayer south) {
		players.put(NORTH, north);
		players.put(SOUTH, south);
		this.log = new Log(BATCH);
		board = new Board();
	}

	public Game(IPlayer north, IPlayer south, Log.Level level) {
		players.put(NORTH, north);
		players.put(SOUTH, south);
		this.log = new Log(level);
		board = new Board();
	}

	@Override
	public void run() {
		log.println(VERBOSE, "Running the game...");
		log.println(VERBOSE, board.toString() + "\n");
		while (!board.gameOver()) {
			Side turn = board.currentTurn();
			int play = players.get(turn).getPlay(board.copy());
			board.play(play);
			log.println(VERBOSE, turn + " plays slot " + play);
			log.println(VERBOSE, board.toString() + "\n");
		}
		board.finalizeScores();
		log.println(VERBOSE, "Game over!! \n" + board.toString());
		String northName = players.get(NORTH).getName();
		String southName = players.get(SOUTH).getName();
		if (board.getCala(NORTH) > board.getCala(SOUTH)) {
			log.println(BATCH, northName + ":\t " + board.getCala(NORTH) + "\t" + southName + ":\t"
					+ board.getCala(SOUTH) + "\t" + northName + " WINS!");
			setWinner(NORTH);
		} else {
			log.println(BATCH, northName + ":\t " + board.getCala(NORTH) + "\t" + southName + ":\t"
					+ board.getCala(SOUTH) + "\t" + southName + " WINS!");
			setWinner(SOUTH);
		}
	}

	private void setWinner(Side winner) {
		this.winner = winner;
	}

	public Side getWinner() {
		return winner;
	}

}
