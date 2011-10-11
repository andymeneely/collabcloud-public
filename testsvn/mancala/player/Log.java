package org.chaoticbits.mancala.player;

public class Log {
	public enum Level {
		NONE, BATCH, VERBOSE
	};

	private Level level;

	public Log(Level level) {
		this.level = level;
	}

	public void setLevel(Level level) {
		this.level = level;
	}

	public Level getLevel() {
		return level;
	}

	public void println(Level logLevel, String message) {
		if (logLevel.ordinal() <= this.level.ordinal())
			System.out.println(message);
	}
}
