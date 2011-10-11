package org.chaoticbits.mancala.player;

import java.util.Random;

import org.chaoticbits.mancala.Board;
import org.chaoticbits.mancala.Side;

public class RandomPlayer implements IPlayer {

	private final Random rnd;
	private final Side side;

	public RandomPlayer(Random rnd, Side side) {
		this.rnd = rnd;
		this.side = side;
	}

	@Override
	public int getPlay(Board state) {
		int play = 0;
		do {
			play = rnd.nextInt(Board.SLOT_WIDTH);
		} while (state.get(side, play) == 0);
		return play;
	}
	
	@Override
	public String getName() {
		return "Random player " + side.pretty();
	}

}
