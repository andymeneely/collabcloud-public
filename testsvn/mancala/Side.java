package org.chaoticbits.mancala;

public enum Side {
	SOUTH(0, "South"), NORTH(1, "North");

	private final int index;
	private final String pretty;

	private Side(int index, String pretty) {
		this.index = index;
		this.pretty = pretty;
	}

	public int index() {
		return index;
	}

	public Side other() {
		if (this == SOUTH)
			return NORTH;
		else
			return SOUTH;
	}
	
	public String pretty() {
		return pretty;
	}
}
