package org.chaoticbits.collabcloud.visualizer;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * A cache of objects that checks if the incoming object hits (e.g. shape intersect) any of the previous
 * shapes. If a hit is not recorded, the object is added to the cache. If a hit is found, then the hit object
 * gets pushed to the top of the list.
 * @author andy
 * 
 * @param <E>
 */
public class LastHitCache<E> {
	private static int hitCounter = 0;

	public static interface IHitCheck<E> {
		public boolean hits(E obj1, E obj2);
	}

	private final Map<E, Integer> lastHit = new HashMap<E, Integer>();
	private LinkedList<E> list = new LinkedList<E>();
	private final IHitCheck<E> checker;

	public LastHitCache(IHitCheck<E> checker) {
		this.checker = checker;
	}

	/**
	 * Check the cache for a hit.
	 * <ul>
	 * <li>If a hit is found, then the hit object gets pushed to the top of the list. obj is not added to the
	 * cache</li>
	 * <li>If a hit is not found, then obj gets added to the top of the cache</li>
	 * </ul>
	 * @param obj
	 * @return
	 */
	public boolean hitNCache(E obj) {
		if (list.isEmpty()) {
			list.add(obj);
			lastHit.put(obj, hitCounter++);
			return false;
		}
		LinkedList<E> newList = new LinkedList<E>();
		do {
			E potentialHit = list.poll();
			if (checker.hits(obj, potentialHit)) {
				newList.addFirst(potentialHit);// add potential to the front of the cache
				newList.addAll(list); // add the tail of the old list
				list = newList; // replace the list
				return true; // and don't add obj to the cache.
			} else {
				newList.addLast(potentialHit);
			}
		} while (!list.isEmpty());
		newList.addFirst(obj);
		list = newList; // replace the list since went all the way through it
		return false;
	}
}
