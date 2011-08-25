package org.chaoticbits.collabcloud.visualizer;

import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.chaoticbits.collabcloud.visualizer.LastHitCache.IHitCheck;
import org.easymock.classextension.EasyMock;
import org.easymock.classextension.IMocksControl;
import org.junit.Before;
import org.junit.Test;

public class LastHitCacheTest {

	private IMocksControl ctrl;

	@Before
	public void setUp() {
		ctrl = EasyMock.createStrictControl();
	}

	@Test
	public void firstThing() throws Exception {
		@SuppressWarnings("unchecked")
		IHitCheck<String> mockHitChecker = (IHitCheck<String>) ctrl.createMock(IHitCheck.class);
		ctrl.replay();
		LastHitCache<String> cache = new LastHitCache<String>(mockHitChecker);
		assertFalse("nothing in the cache", cache.hitNCache("a"));
		ctrl.verify();
	}

	@Test
	public void firstThingNowAdded() throws Exception {
		@SuppressWarnings("unchecked")
		IHitCheck<String> mockHitChecker = (IHitCheck<String>) ctrl.createMock(IHitCheck.class);
		expect(mockHitChecker.hits("a", "a")).andReturn(true).once();
		ctrl.replay();
		LastHitCache<String> cache = new LastHitCache<String>(mockHitChecker);
		assertFalse("nothing in the cache", cache.hitNCache("a"));
		assertTrue("something in the cache this time", cache.hitNCache("a"));
		ctrl.verify();
	}

	@Test
	public void checksInRecentlyHitOrder() throws Exception {
		@SuppressWarnings("unchecked")
		IHitCheck<String> mockHitChecker = (IHitCheck<String>) ctrl.createMock(IHitCheck.class);
		expect(mockHitChecker.hits("b", "a")).andReturn(false).once();
		expect(mockHitChecker.hits("c", "b")).andReturn(false).once();
		expect(mockHitChecker.hits("c", "a")).andReturn(false).once();
		ctrl.replay();
		LastHitCache<String> cache = new LastHitCache<String>(mockHitChecker);
		assertFalse("nothing in the cache", cache.hitNCache("a"));
		assertFalse("something in the cache this time", cache.hitNCache("b"));
		assertFalse("a and b in the cache this time", cache.hitNCache("c"));
		ctrl.verify();
	}

	@Test
	public void deepCheckInOrder() throws Exception {
		@SuppressWarnings("unchecked")
		IHitCheck<String> mockHitChecker = (IHitCheck<String>) ctrl.createMock(IHitCheck.class);
		// adding b
		expect(mockHitChecker.hits("b", "a")).andReturn(false).once();
		// adding c - checks against b, then a
		expect(mockHitChecker.hits("c", "b")).andReturn(false).once();
		expect(mockHitChecker.hits("c", "a")).andReturn(false).once();
		// adding b again - checks against c, then b, but not a
		expect(mockHitChecker.hits("b", "c")).andReturn(false).once();
		expect(mockHitChecker.hits("b", "b")).andReturn(true).once();
		// adding b again again - checks b the first time
		expect(mockHitChecker.hits("b", "b")).andReturn(true).once();
		ctrl.replay();
		LastHitCache<String> cache = new LastHitCache<String>(mockHitChecker);
		assertFalse("nothing in the cache", cache.hitNCache("a"));
		assertFalse("something in the cache this time", cache.hitNCache("b"));
		assertFalse("a and b in the cache this time", cache.hitNCache("c"));
		assertTrue("b again - should not check a ", cache.hitNCache("b"));
		assertTrue("b again again - should not check anything else first ", cache.hitNCache("b"));
		ctrl.verify();
	}

}
