package org.chaoticbits.collabcloud.visualizer;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class LastHitCacheTest {

	@Test
	public void nothing() throws Exception {
		LastHitCache<String> cache = new LastHitCache<String>();
		assertFalse(cache.contains("a"));
	}

	@Test
	public void oneThing() throws Exception {
		LastHitCache<String> cache = new LastHitCache<String>();
		cache.add("a");
		assertTrue(cache.contains("a"));
	}

	@Test
	public void twoThings() throws Exception {
		LastHitCache<String> cache = new LastHitCache<String>();
		cache.add("b");
		cache.add("a");
		assertTrue(cache.contains("a"));
	}

	// Put in A,B, and C. Check if B exists. The C and B equals methods should be checked, but not A
	@Test
	public void checksInRecentlyHitOrder() throws Exception {
		LastHitCache<String> cache = new LastHitCache<String>();
		cache.add("a");
		cache.add("b");
		cache.add("c");

		assertTrue("hits are in ascending order", cache.lastHitTime("a") < cache.lastHitTime("b"));
		assertTrue("hits are in ascending order", cache.lastHitTime("b") < cache.lastHitTime("c"));
		assertTrue("cache contains b", cache.contains("b"));
		assertTrue("now in a new order, with b first", cache.lastHitTime("a") < cache.lastHitTime("c"));
		assertTrue("hits are in ascending order", cache.lastHitTime("c") < cache.lastHitTime("b"));
		assertTrue("cache contains b", cache.contains("b"));
	}
}
