package org.chaoticbits.collabcloud.visualizer.place;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.assertEquals;

import java.awt.Dimension;
import java.awt.geom.Point2D;
import java.util.HashSet;
import java.util.Set;

import org.chaoticbits.collabcloud.ISummaryToken;
import org.chaoticbits.collabcloud.visualizer.placement.ContributionNetworkPlacement;
import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.junit.Before;
import org.junit.Test;

import edu.uci.ics.jung.algorithms.layout.AbstractLayout;

@SuppressWarnings("unchecked")
public class ContributionNetworkPlacementTest {
	private final IMocksControl ctrl = EasyMock.createControl();;
	private AbstractLayout<ISummaryToken, Long> layout = ctrl.createMock(AbstractLayout.class);
	private ISummaryToken tokenA = ctrl.createMock(ISummaryToken.class);
	private ISummaryToken tokenB = ctrl.createMock(ISummaryToken.class);

	@Before
	public void initMocks() {
		ctrl.reset();
	}

	@Test
	public void inSquare() throws Exception {
		Set<ISummaryToken> tokens = set(tokenA, tokenB);
		expect(layout.getX(tokenA)).andReturn(1000.0).once();
		expect(layout.getY(tokenA)).andReturn(2000.0).once();
		layout.setSize(new Dimension(100, 200));
		expectLastCall().anyTimes();
		ContributionNetworkPlacement place = new ContributionNetworkPlacement(tokens, new Dimension(100, 200), new Point2D.Double(300, 400));
		ctrl.replay();
		place.set(layout);
		Point2D p = place.getStartingPlace(tokenA, null);
		assertEquals(1200, p.getX(), 0.01);
		assertEquals(2200, p.getY(), 0.01);
		ctrl.verify();
	}

	private Set<ISummaryToken> set(ISummaryToken... tokens) {
		Set<ISummaryToken> set = new HashSet<ISummaryToken>();
		for (ISummaryToken token : tokens) {
			set.add(token);
		}
		return set;
	}
}
