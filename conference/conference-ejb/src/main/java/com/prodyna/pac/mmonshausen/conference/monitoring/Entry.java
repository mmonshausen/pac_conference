package com.prodyna.pac.mmonshausen.conference.monitoring;

/**
 * save information about runtimes of service methods
 * 
 * @author Martin Monshausen, PRODYNA AG
 */
public class Entry {
	private final String service;
	private final String method;

	private long count;
	private long sum;
	private long minTime = Long.MAX_VALUE;
	private long maxTime = Long.MIN_VALUE;
	
	public Entry(String service, String method) {
		this.service = service;
		this.method = method;
	}
	
	public void report(long time) {
		if(time < minTime) {
			minTime = time;
		}
		if (time > maxTime) {
			maxTime = time;
		}
		sum += time;
		count++;
	}
	
	public float getAverage() {
		if (count == 0) {
			return 0f;
		}
		return (float) sum / (float) count;
	}

	public long getCount() {
		return count;
	}

	public void setCount(long count) {
		this.count = count;
	}

	public long getSum() {
		return sum;
	}

	public void setSum(long sum) {
		this.sum = sum;
	}

	public long getMinTime() {
		return minTime;
	}

	public void setMinTime(long minTime) {
		this.minTime = minTime;
	}

	public long getMaxTime() {
		return maxTime;
	}

	public void setMaxTime(long maxTime) {
		this.maxTime = maxTime;
	}

	public String getService() {
		return service;
	}

	public String getMethod() {
		return method;
	}
}