package org.chaoticbits.collabcloud.visualizer;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class LastHitCache<E> implements Comparator<E> {

	private final Map<E, Long> lastHit = new HashMap<E, Long>();
	private final PriorityQueue<E> priorityQ = new PriorityQueue<E>(100, this);
	private long tieBreaker = 0;

	public boolean contains(E obj) {
		boolean contains = priorityQ.contains(obj);
		if (contains)
			lastHit.put(obj, System.currentTimeMillis() + (tieBreaker++));
		return contains;
	}

	public void add(E obj) {
		lastHit.put(obj, System.currentTimeMillis() + (tieBreaker++));
		priorityQ.add(obj);
	}

	public int compare(E o1, E o2) {
		Long o1HitTime = lastHit.get(o1);
		Long o2HitTime = lastHit.get(o2);
		checkNull(o1, o1HitTime);
		checkNull(o2, o2HitTime);
		return o1HitTime.compareTo(o2HitTime);
	}

	public Long lastHitTime(E obj) {
		return lastHit.get(obj);
	}

	private void checkNull(E o1, Long o1HitTime) throws IllegalAccessError {
		if (o1HitTime == null)
			throw new IllegalAccessError("No hit time recorded for " + o1.toString());
	}
}
