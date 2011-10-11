package org.chaoticbits.mancala;

import java.util.Arrays;
import java.util.Formatter;
import java.util.Iterator;

import org.chaoticbits.mancala.player.support.PlayIterator;

public class Board implements Iterable<Integer> {

	/**
	 * Format: <br>
	 * -------B------- <br>
	 * --5 4 3 2 1 0-- <br>
	 * B-------------A <br>
	 * --0 1 2 3 4 5-- <br>
	 * -------A------- <br>
	 */

	public static final int INIT_STONES = 4;

	public static final int SLOT_WIDTH = 6;

	private int[] slots = new int[2 * SLOT_WIDTH + 2];

	private Side currentTurn;

	public Board() {
		for (int j = 0; j < slots.length; j++) {
			slots[j] = INIT_STONES;
		}
		// cala gets zero
		slots[SLOT_WIDTH] = 0;
		slots[SLOT_WIDTH * 2 + 1] = 0;
		currentTurn = Side.SOUTH;
	}

	// TODO Use an annotation to enforce the length of the array
	public Board(int[] initialBoard, Side turn) {
		slots = initialBoard;
		currentTurn = turn;
	}

	// TODO Use an annotation to enforce slot in [0,5]
	public int get(Side p, int slot) {
		return slots[slot + p.index() * (SLOT_WIDTH + 1)];
	}

	public Board play(int startSlot) {
		int numStones = get(currentTurn, startSlot);
		int extendedSlot = toExtendedSlotIndex(currentTurn, startSlot);
		slots[extendedSlot] = 0; // zero out the current one
		while (numStones > 0) {
			extendedSlot = (extendedSlot + 1) % slots.length;
			if (extendedSlot != calaIndex(currentTurn.other())) {
				slots[extendedSlot]++;
				numStones--;
			}
		}
		capture(extendedSlot);
		if (!landedOnMyCala(extendedSlot))
			currentTurn = currentTurn.other();
		return this;
	}

	private void capture(int landed) {
		if (landedOnCapture(landed)) {
			int opponentsSlot = opponentsSlot(landed);
			slots[calaIndex(currentTurn)] = slots[calaIndex(currentTurn)] + slots[opponentsSlot] + 1;
			slots[opponentsSlot] = 0;
			slots[landed] = 0;
		}
	}

	private boolean landedOnCapture(int landed) {
		// must be one since we just dropped it there
		// must be on our own side
		// cannot be our cala
		// must have at least one on the other side
		return (slots[landed] == 1)
				&& landed == (landed % (SLOT_WIDTH + 1)) + (SLOT_WIDTH + 1) * currentTurn.index() // 
				&& landed != calaIndex(currentTurn) && slots[opponentsSlot(landed)] > 0;
	}

	private int opponentsSlot(int landed) {
		return toExtendedSlotIndex(currentTurn.other(), // 
				(SLOT_WIDTH - 1) - (landed % (SLOT_WIDTH + 1)));
	}

	private boolean landedOnMyCala(int extendedSlot) {
		return extendedSlot == calaIndex(currentTurn);
	}

	private int calaIndex(Side p) {
		return toExtendedSlotIndex(p, SLOT_WIDTH);
	}

	public int getCala(Side p) {
		return slots[toExtendedSlotIndex(p, SLOT_WIDTH)];
	}

	private int toExtendedSlotIndex(Side p, int slot) {
		return slot + p.index() + p.index() * SLOT_WIDTH;
	}

	@Override
	public String toString() {
		String str = "-------North-------\n  ";
		for (int j = slots.length - 2; j > SLOT_WIDTH; j--) { // in reverse order
			str += new Formatter(new StringBuilder()).format("%3d", slots[j]).toString();
		}
		str += "\nN " + slots[SLOT_WIDTH * 2 + 1] + "                  " + slots[SLOT_WIDTH] + " S\n  ";
		for (int i = 0; i < SLOT_WIDTH; i++) {
			str += new Formatter(new StringBuilder()).format("%3d", slots[i]).toString();
		}
		str += "\n-------South-------";
		return str;
	}

	public Side currentTurn() {
		return currentTurn;
	}

	public boolean gameOver() {
		Side.values();
		for (Side p : Side.values()) {
			boolean allZeros = true;
			for (int i = 0; i < SLOT_WIDTH; i++)
				allZeros = allZeros && get(p, i) == 0;
			if (allZeros)
				return true;
		}
		return false;
	}

	public Board copy() {
		return new Board(Arrays.copyOf(slots, slots.length), currentTurn);
	}

	@Override
	public boolean equals(Object obj) {
		return obj != null && obj.getClass().equals(this.getClass()) && this.equals((Board) obj);
	}

	private boolean equals(Board other) {
		return currentTurn == other.currentTurn && Arrays.equals(slots, other.slots);
	}

	@Override
	public int hashCode() {
		return Arrays.hashCode(slots) * (currentTurn.index() + 1);
	}

	public void finalizeScores() {
		for (Side p : Side.values()) {
			for (int i = 0; i < SLOT_WIDTH; i++) {
				int actualIndex = i + (SLOT_WIDTH + 1) * p.index();
				slots[calaIndex(p)] += slots[actualIndex];
				slots[actualIndex] = 0;
			}
		}
	}

	@Override
	public Iterator<Integer> iterator() {
		return new PlayIterator(this, currentTurn);
	}
}
