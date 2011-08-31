package org.chaoticbits.collabcloud.visualizer.color;

import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertEquals;

import java.awt.Color;
import java.util.Random;

import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.junit.Test;

public class RandomGreyTest {

	@Test
	public void testGrey() throws Exception {
		IMocksControl ctrl = EasyMock.createControl();
		Random mock = ctrl.createMock(Random.class);
		
		expect(mock.nextInt(50)).andReturn(0).once();
		expect(mock.nextInt(50)).andReturn(100).once();//yes, not realistic...
		ctrl.replay();
		
		RandomGrey randomGrey = new RandomGrey(mock, 10, 60);
		Color firstGrey = randomGrey.lookup(null, null);
		assertEquals(10, firstGrey.getRed());
		assertEquals(10, firstGrey.getGreen());
		assertEquals(10, firstGrey.getBlue());
		Color secondGrey = randomGrey.lookup(null, null);
		assertEquals(110, secondGrey.getRed());
		assertEquals(110, secondGrey.getGreen());
		assertEquals(110, secondGrey.getBlue());
		
		ctrl.verify();
	}
}
