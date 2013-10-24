package com.prodyna.pac.mmonshausen.conference.monitoring;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

/**
 * bean which implements the MXBean interface and returns monitoring information
 * 
 * @author Martin Monshausen, PRODYNA AG
 */
public class MeasuringBean implements MeasuringMXBean {
	private final HashMap<String, Entry> entries = new HashMap<String, Entry>();
	
	@Override
	public void reset() {
		entries.clear();
	}

	@Override
	public synchronized void report(final String service, final String method, final long time) {
		final String key = service + ":" + method;
		
		Entry entry = entries.get(key);
		if(entry == null) {
			entry = new Entry(service, method);
			entries.put(key, entry);
		}
		entry.report(time);
	}

	@Override
	public List<Entry> getAll() {
		return new ArrayList<Entry> (entries.values());
	}

	@Override
	public int getCount() {
		return entries.size();
	}
	
	@Override
	public Entry getSumEntry() {
		final Collection<Entry> entryCollection = entries.values();
		
		long sum = 0;
		Entry sumEntry = null;
		for(final Entry entry : entryCollection) {
			final long currSum = entry.getSum();
			if(currSum > sum) {
				sum = currSum;
				sumEntry = entry;
			}
		}
		return sumEntry;
	}
	
	@Override
	public long getSumTime() {
		final Entry sumEntry = getSumEntry();
		return sumEntry.getSum();
	}
	

	@Override
	public Entry getMinEntry() {
		final Collection<Entry> entryCollection = entries.values();
		
		long minTime = -1;
		Entry minEntry = null;
		for(final Entry entry : entryCollection) {
			final long currTime = entry.getMinTime();
			if((currTime < minTime) || (minTime == -1)) {
				minTime = currTime;
				minEntry = entry;
			}
		}
		return minEntry;
	}
	
	@Override
	public long getMinTime() {
		final Entry minEntry = getMinEntry();
		return minEntry.getMinTime();
	}
	
	@Override
	public Entry getMaxEntry() {
		final Collection<Entry> entryCollection = entries.values();
		
		long worstTime = 0;
		Entry worstEntry = null;
		for(final Entry entry : entryCollection) {
			final long maxTime = entry.getMaxTime();
			if(maxTime > worstTime) {
				worstTime = maxTime;
				worstEntry = entry;
			}
		}
		return worstEntry;
	}
	
	@Override
	public long getMaxTime() {
		final Entry maxEntry = getMaxEntry();
		return maxEntry.getMaxTime();
	}
	
	@Override
	public Entry getAverageEntry() {
		final Collection<Entry> entryCollection = entries.values();
		
		float average = 0f;
		Entry averageEntry = null;
		for(final Entry entry : entryCollection) {
			final float averageTime = entry.getAverage();
			if(averageTime > average) {
				average = averageTime;
				averageEntry = entry;
			}
		}
		return averageEntry;
	}

	@Override
	public float getAverageTime() {
		final Entry averageEntry = getAverageEntry();
		return averageEntry.getAverage();
	}
}
