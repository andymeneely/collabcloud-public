package org.chaoticbits.mancala.player;

import org.chaoticbits.mancala.Board;

public interface IPlayer {

	public int getPlay(Board state);

	public String getName();
}
