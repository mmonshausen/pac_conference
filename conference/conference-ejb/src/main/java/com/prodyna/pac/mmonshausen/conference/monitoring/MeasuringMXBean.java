package com.prodyna.pac.mmonshausen.conference.monitoring;

import java.util.List;

/**
 * interface for MXBean
 * 
 * @author Martin Monshausen, PRODYNA AG
 */
public interface MeasuringMXBean {
	public void reset();
	
	public void report(String service, String method, long time);
	
	public List<Entry> getAll();
	
	public int getCount();
	
	public Entry getSumEntry();
	public long getSumTime();
	
	public Entry getMinEntry();
	public long getMinTime();
	
	public Entry getMaxEntry();
	public long getMaxTime();
	
	public Entry getAverageEntry();
	public float getAverageTime();
}
