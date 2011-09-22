package org.chaoticbits.collabcloud;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class PerformanceProfiler {
	private static PerformanceProfiler instance = null;

	public static PerformanceProfiler getInstance() {
		if (instance == null)
			instance = new PerformanceProfiler();
		return instance;
	}

	private PerformanceProfiler() {}

	private Map<String, Long> totalActivity = new HashMap<String, Long>();
	private Map<String, Long> pendingActivity = new HashMap<String, Long>();

	public void start(String activity) {
		pendingActivity.put(activity, System.currentTimeMillis());
	}

	public void finish(String activity) {
		long now = System.currentTimeMillis();
		updateTotal(activity, timeTaken(activity, now));
		pendingActivity.remove(activity);
	}

	public String report() {
		String str = "";
		Set<Entry<String, Long>> set = totalActivity.entrySet();
		for (Entry<String, Long> entry : set) {
			str += entry.getKey() + ":\t" + entry.getValue();
		}
		return str;
	}

	private void updateTotal(String activity, long timeTaken) {
		Long total = totalActivity.get(activity);
		if (total == null)
			total = 0l;
		total += timeTaken;
		totalActivity.put(activity, total);
	}

	private long timeTaken(String activity, long now) {
		Long time = pendingActivity.get(activity);
		if (time == null)
			return 0l;
		return time - now;
	}
}
