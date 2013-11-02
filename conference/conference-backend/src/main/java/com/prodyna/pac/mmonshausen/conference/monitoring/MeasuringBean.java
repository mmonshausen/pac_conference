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
	
	/*
	 * (non-Javadoc)
	 * @see com.prodyna.pac.mmonshausen.conference.monitoring.MeasuringMXBean#reset()
	 */
	@Override
	public void reset() {
		entries.clear();
	}

	/*
	 * (non-Javadoc)
	 * @see com.prodyna.pac.mmonshausen.conference.monitoring.MeasuringMXBean#report(java.lang.String, java.lang.String, long)
	 */
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

	/*
	 * (non-Javadoc)
	 * @see com.prodyna.pac.mmonshausen.conference.monitoring.MeasuringMXBean#getAll()
	 */
	@Override
	public List<Entry> getAll() {
		return new ArrayList<Entry> (entries.values());
	}

	/*
	 * (non-Javadoc)
	 * @see com.prodyna.pac.mmonshausen.conference.monitoring.MeasuringMXBean#getCount()
	 */
	@Override
	public int getCount() {
		return entries.size();
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.prodyna.pac.mmonshausen.conference.monitoring.MeasuringMXBean#getSumEntry()
	 */
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
	
	/*
	 * (non-Javadoc)
	 * @see com.prodyna.pac.mmonshausen.conference.monitoring.MeasuringMXBean#getSumTime()
	 */
	@Override
	public long getSumTime() {
		final Entry sumEntry = getSumEntry();
		return sumEntry.getSum();
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.prodyna.pac.mmonshausen.conference.monitoring.MeasuringMXBean#getMinEntry()
	 */
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
	
	/*
	 * (non-Javadoc)
	 * @see com.prodyna.pac.mmonshausen.conference.monitoring.MeasuringMXBean#getMinTime()
	 */
	@Override
	public long getMinTime() {
		final Entry minEntry = getMinEntry();
		return minEntry.getMinTime();
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.prodyna.pac.mmonshausen.conference.monitoring.MeasuringMXBean#getMaxEntry()
	 */
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
	
	/*
	 * (non-Javadoc)
	 * @see com.prodyna.pac.mmonshausen.conference.monitoring.MeasuringMXBean#getMaxTime()
	 */
	@Override
	public long getMaxTime() {
		final Entry maxEntry = getMaxEntry();
		return maxEntry.getMaxTime();
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.prodyna.pac.mmonshausen.conference.monitoring.MeasuringMXBean#getAverageEntry()
	 */
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

	/*
	 * (non-Javadoc)
	 * @see com.prodyna.pac.mmonshausen.conference.monitoring.MeasuringMXBean#getAverageTime()
	 */
	@Override
	public float getAverageTime() {
		final Entry averageEntry = getAverageEntry();
		return averageEntry.getAverage();
	}
}