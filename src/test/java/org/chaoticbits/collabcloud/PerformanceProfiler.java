package org.chaoticbits.collabcloud;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
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
	private Map<String, Long> activityCounts = new HashMap<String, Long>();

	public void start(String activity) {
		increment(activity);
		pendingActivity.put(activity, System.currentTimeMillis());
	}

	private void increment(String activity) {
		Long count = activityCounts.get(activity);
		if (count == null)
			count = 0L;
		activityCounts.put(activity, count + 1);
	}

	public void finish(String activity) {
		long now = System.currentTimeMillis();
		updateTotal(activity, timeTaken(activity, now));
		pendingActivity.remove(activity);
	}

	public String report() {
		String str = "";
		Set<Entry<String, Long>> set = totalActivity.entrySet();
		if (!set.isEmpty()) {
			List<Entry<String, Long>> list = new ArrayList<Entry<String, Long>>(set.size());
			list.addAll(set);
			Collections.sort(list, new Comparator<Entry<String, Long>>() {
				public int compare(Entry<String, Long> o1, Entry<String, Long> o2) {
					return -1 * o1.getValue().compareTo(o2.getValue());
				}
			});
			str += "===Performance Results===\n";
			for (Entry<String, Long> entry : list) {
				str += entry.getKey() + ":\t" + entry.getValue() + "ms\t" + activityCounts.get(entry.getKey()) + "x\n";
			}
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
		Long started = pendingActivity.get(activity);
		if (started == null)
			return 0l;
		return now - started;
	}
}
