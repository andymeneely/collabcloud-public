package org.chaoticbits.collabcloud.vc;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.chaoticbits.collabcloud.ISummarizable;
import org.chaoticbits.collabcloud.ISummaryToken;
import org.chaoticbits.collabcloud.codeprocessor.java.JavaSummaryToken;
import org.chaoticbits.collabcloud.codeprocessor.java.JavaTokenType;
import org.chaoticbits.collabcloud.visualizer.placement.INetworkBuilder;

import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.UndirectedSparseGraph;

public class TokenContributionNetwork implements INetworkBuilder {

	private final Map<Developer, Set<ISummarizable>> contributions;

	public TokenContributionNetwork(Map<Developer, Set<ISummarizable>> contributions) {
		this.contributions = contributions;
	}

	public Graph<ISummaryToken, Long> build() {
		Graph<ISummaryToken, Long> graph = new UndirectedSparseGraph<ISummaryToken, Long>();
		long edge = 0;
		for (Entry<Developer, Set<ISummarizable>> entry : contributions.entrySet()) {
			for (ISummarizable summarizable : entry.getValue()) {
				//TODO Need to pull out the tokens from the summarizable
				ISummaryToken token = new JavaSummaryToken(summarizable, summarizable.getFile().getName(), summarizable.getFile().getName(),
						JavaTokenType.CLASS);
				graph.addEdge(edge++, entry.getKey(), token);
			}
		}
		return graph;

	}
}
