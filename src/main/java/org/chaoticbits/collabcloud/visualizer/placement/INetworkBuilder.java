package org.chaoticbits.collabcloud.visualizer.placement;

import org.chaoticbits.collabcloud.ISummaryToken;

import edu.uci.ics.jung.graph.Graph;

public interface INetworkBuilder {
	public Graph<ISummaryToken, Long> build();
}
