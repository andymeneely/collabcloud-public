package org.chaoticbits.mancala.player;

import java.util.Scanner;

import org.chaoticbits.mancala.Board;
import org.chaoticbits.mancala.Side;

public class HumanPlayer implements IPlayer {

	private final Side side;
	private Scanner input;

	public HumanPlayer(Side side) {
		this.side = side;
		input = new Scanner(System.in);
	}

	@Override
	public int getPlay(Board state) {
		System.out.print("Your play for side " + side + "[0-5]: ");
		int play = input.nextInt();
		System.out.println("");
		return play;
	}
	
	@Override
	public String getName() {
		return "Human player " + side.pretty();
	}

}
