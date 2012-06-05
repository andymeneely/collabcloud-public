package org.chaoticbits.collabcloud.visualizer.command;

public class VisualizerConfigException extends Exception {

	private static final long serialVersionUID = -5408645320524559616L;

	public VisualizerConfigException(String message) {
		super(message);
	}

	public VisualizerConfigException(Exception e) {
		super("Error while initalizing visualizer configuration,"
				+ " see cause below", e);
	}
}
