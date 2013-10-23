package com.prodyna.pac.mmonshausen.conference.util;

import java.util.logging.Logger;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

import com.prodyna.pac.mmonshausen.conference.monitoring.MeasuringMXBean;

/**
 * 
 * @author Martin Monshausen, PRODYNA AG
 */
@Interceptor
@Logged
public class MeasuringInterceptor {

	@Inject
	private Logger logger;
	
	@Inject
	private MeasuringMXBean mxBean;

	@AroundInvoke
	public Object onMethodCall(final InvocationContext context) throws Exception {
		final String service = context.getMethod().getDeclaringClass().getName();
		final String method = context.getMethod().getName();
		final String parameters = arrayToString(context.getParameters());

		final long startTime = System.nanoTime();

		final Object result = context.proceed();

		final long endTime = System.nanoTime();

		final long duration = (endTime - startTime) / 1000000l;
		
		logger.info(buildLogMessage(service, method, parameters, duration));
		mxBean.report(service, method, duration);
		
		return result;
	}

	private String buildLogMessage(final String service, final String method,
			final String parameters, final long duration) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("Aufruf ");
		buffer.append(service+ ".");
		buffer.append(method);
		buffer.append("(" +parameters+") ");
		buffer.append("Laufzeit ");
		buffer.append(duration + " ms");
		return buffer.toString();
	}

	private String arrayToString(final Object[] parameters) {
		final StringBuffer result = new StringBuffer();
		
		for (final Object object : parameters) {
			if(result.length() > 0) {
				result.append(", ");
			}
			result.append(object);
		}
		return result.toString();
	}
}