package com.prodyna.pac.mmonshausen.conference.monitoring;

import java.util.List;

import com.prodyna.pac.mmonshausen.conference.util.MeasuringInterceptor;

/**
 * interface for JMX bean<br>
 * using this JMX bean it is possible to get information about services elapsed
 * times and some statistical information about e.q. average and worst elapsed
 * time of services methods<br>
 * information about elapsed times is reported by {@link MeasuringInterceptor}
 * 
 * @author Martin Monshausen, PRODYNA AG
 */
public interface MeasuringMXBean {
	
	/**
	 * reset stored information about service methods elapsed time and all
	 * statistical information
	 */
	public void reset();
	
	/**
	 * method to report elapsed times
	 * 
	 * @param service
	 *            service class name for which elapsed time is reported
	 * @param method
	 *            method name for which elapsed time is reported
	 * @param time
	 *            elapsed time to report
	 */
	public void report(String service, String method, long time);
	
	/**
	 * get all reportings for all service methods
	 * 
	 * @return list of {@link Entry} representing information about different
	 *         service methodds
	 */
	public List<Entry> getAll();
	
	/**
	 * return amount of services for which elapsed times were reported
	 * 
	 * @return amount of services
	 */
	public int getCount();
	
	/**
	 * return {@link Entry} representing information about service method with
	 * most summarized elapsed time
	 * 
	 * @return {@link Entry} with most elpased time at all
	 */
	public Entry getSumEntry();
	
	/**
	 * return elapsed time amount of service method with most summarized elapsed time
	 * 
	 * @return amount of elapsed time of service method with most summarized elapsed time
	 */
	public long getSumTime();
	
	/**
	 * return {@link Entry} representing service method with least elapsed time
	 * for one call
	 * 
	 * @return {@link Entry} with least elapsed time
	 */
	public Entry getMinEntry();
	
	/**
	 * return amount of elapsed time of service method with least elapsed time
	 * of one call
	 * 
	 * @return amount of elapsed time of service method with least elapsed time
	 *         of one call
	 */
	public long getMinTime();
	
	/**
	 * return {@link Entry} representing service method with most elapsed time
	 * for one call
	 * 
	 * @return {@link Entry} representing service method with most elapsed time
	 *         for one call
	 */
	public Entry getMaxEntry();
	
	/**
	 * return amount of elapsed time of service method with most elapsed time
	 * for one call
	 * 
	 * @return amount of elapsed of service method with most elapsed time of one
	 *         call
	 */
	public long getMaxTime();
	
	/**
	 * return {@link Entry} representing service method with most elapsed time
	 * in average
	 * 
	 * @return {@link Entry} representing service method with most elapsed time
	 *         in average
	 */
	public Entry getAverageEntry();
	
	/**
	 * return amount of elapsed time of service method with most elapsed time in average
	 * @return amount of elapsed time of service method with most elapsed time in average
	 */
	public float getAverageTime();
}