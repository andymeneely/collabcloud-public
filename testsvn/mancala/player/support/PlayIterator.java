package org.chaoticbits.mancala.player.support;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.chaoticbits.mancala.Board;
import org.chaoticbits.mancala.Side;

public class PlayIterator implements Iterator<Integer> {

	private List<Integer> plays = new ArrayList<Integer>(Board.SLOT_WIDTH);

	public PlayIterator(Board board, Side side) {
		for (int play = 0; play < Board.SLOT_WIDTH; play++) {
			if (board.get(side, play) > 0)
				plays.add(play);
		}
	}

	@Override
	public boolean hasNext() {
		return !plays.isEmpty();
	}

	@Override
	public Integer next() {
		return plays.remove(0);
	}

	@Override
	public void remove() {
		throw new IllegalStateException("Not supported!");
	}

}
