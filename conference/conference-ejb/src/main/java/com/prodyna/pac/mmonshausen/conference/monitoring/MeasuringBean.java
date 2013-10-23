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
	private HashMap<String, Entry> entries = new HashMap<String, Entry>();
	
	@Override
	public void reset() {
		entries.clear();
	}

	@Override
	public synchronized void report(String service, String method, long time) {
		String key = service + ":" + method;
		
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
		Collection<Entry> entryCollection = entries.values();
		
		long sum = 0;
		Entry sumEntry = null;
		for(Entry entry : entryCollection) {
			long currSum = entry.getSum();
			if(currSum > sum) {
				sum = currSum;
				sumEntry = entry;
			}
		}
		return sumEntry;
	}
	
	@Override
	public long getSumTime() {
		Entry sumEntry = getSumEntry();
		return sumEntry.getSum();
	}
	

	@Override
	public Entry getMinEntry() {
		Collection<Entry> entryCollection = entries.values();
		
		long minTime = -1;
		Entry minEntry = null;
		for(Entry entry : entryCollection) {
			long currTime = entry.getMinTime();
			if((currTime < minTime) || (minTime == -1)) {
				minTime = currTime;
				minEntry = entry;
			}
		}
		return minEntry;
	}
	
	@Override
	public long getMinTime() {
		Entry minEntry = getMinEntry();
		return minEntry.getMinTime();
	}
	
	@Override
	public Entry getMaxEntry() {
		Collection<Entry> entryCollection = entries.values();
		
		long worstTime = 0;
		Entry worstEntry = null;
		for(Entry entry : entryCollection) {
			long maxTime = entry.getMaxTime();
			if(maxTime > worstTime) {
				worstTime = maxTime;
				worstEntry = entry;
			}
		}
		return worstEntry;
	}
	
	@Override
	public long getMaxTime() {
		Entry maxEntry = getMaxEntry();
		return maxEntry.getMaxTime();
	}
	
	@Override
	public Entry getAverageEntry() {
		Collection<Entry> entryCollection = entries.values();
		
		float average = 0f;
		Entry averageEntry = null;
		for(Entry entry : entryCollection) {
			float averageTime = entry.getAverage();
			if(averageTime > average) {
				average = averageTime;
				averageEntry = entry;
			}
		}
		return averageEntry;
	}

	@Override
	public float getAverageTime() {
		Entry averageEntry = getAverageEntry();
		return averageEntry.getAverage();
	}
}
