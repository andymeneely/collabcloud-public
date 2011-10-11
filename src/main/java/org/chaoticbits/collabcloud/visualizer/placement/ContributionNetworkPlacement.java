package org.chaoticbits.collabcloud.visualizer.placement;

import java.awt.Dimension;
import java.awt.Shape;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.util.Set;

import org.chaoticbits.collabcloud.ISummaryToken;

import edu.uci.ics.jung.algorithms.layout.AbstractLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.algorithms.layout.SpringLayout;
import edu.uci.ics.jung.graph.UndirectedGraph;
import edu.uci.ics.jung.graph.UndirectedSparseGraph;

/**
 * Lays out tokens according to having the same parent summarizable, using graph theory layout.
 * 
 * Based upon on all of the tokens, form a network of tokens (using the JUNG2 library) where tokens are
 * connected if they have the same parent. Then run a layout algorithm
 * @author andy
 * 
 */
public class ContributionNetworkPlacement implements IPlaceStrategy {

	private final Set<ISummaryToken> tokens;
	private AbstractLayout<ISummaryToken, Long> layout;
	private final Dimension size;
	private final Point2D center;

	/**
	 * Requires all tokens up front - with parent summarizeables
	 * @param allTokens
	 */
	public ContributionNetworkPlacement(Set<ISummaryToken> allTokens, Dimension size, Point2D center) {
		this.tokens = allTokens;
		this.size = size;
		this.center = center;
	}

	public Point2D getStartingPlace(ISummaryToken token, Shape shape) {
		if (layout == null)
			computeLayout();
		return centered(token);
	}

	/**
	 * Primarily used for unit tests - not needed for typical use
	 * @param layout
	 */
	public void set(AbstractLayout<ISummaryToken, Long> layout) {
		this.layout = layout;
		this.layout.setSize(size);
	}

	/*
	 * Layout will output this into the corner - we need to re-center it
	 */
	private Double centered(ISummaryToken token) {
		double x = layout.getX(token) - size.width + center.getX();
		double y = layout.getY(token) - size.height + center.getY();
		return new Point2D.Double(x, y);
	}

	private void computeLayout() {
		layout = new SpringLayout<ISummaryToken, Long>(initGraph());
		layout.setSize(size);
	}

	private UndirectedGraph<ISummaryToken, Long> initGraph() {
		UndirectedSparseGraph<ISummaryToken, Long> g = new UndirectedSparseGraph<ISummaryToken, Long>();
		long edge = 0;;
		for (ISummaryToken token : tokens)
			g.addVertex(token);
		for (ISummaryToken tokenA : tokens) {
			for (ISummaryToken tokenB : tokens) {
				if (tokenA != tokenB && sameParent(tokenA, tokenB)) {
					g.addEdge(edge++, tokenA, tokenB);
				}
			}
		}
		return g;
	}

	private boolean sameParent(ISummaryToken tokenA, ISummaryToken tokenB) {
		return tokenA.getParentSummarizable() != null && tokenA.getParentSummarizable().equals(tokenB.getParentSummarizable());
	}

}
