package com.prodyna.pac.mmonshausen.conference.util;

import java.util.logging.Logger;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.management.MBeanServer;
import javax.management.MBeanServerInvocationHandler;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;

import com.prodyna.pac.mmonshausen.conference.monitoring.MeasuringMXBean;

/**
 * interceptor which intercepts all method calls of service classes and reports and logs
 * information about their runtimes
 * 
 * @author Martin Monshausen, PRODYNA AG
 */
@Interceptor
@Logged
public class MeasuringInterceptor {

	@Inject
	private Logger logger;

	@Inject
	private MBeanServer mBeanServer;

	@AroundInvoke
	public Object onMethodCall(final InvocationContext context)
			throws Exception {
		final String service = context.getMethod().getDeclaringClass()
				.getName();
		final String method = context.getMethod().getName();
		final String parameters = arrayToString(context.getParameters());

		final long startTime = System.nanoTime();

		final Object result = context.proceed();

		final long endTime = System.nanoTime();

		final long duration = (endTime - startTime) / 1000000l;

		logger.info(buildLogMessage(service, method, parameters, duration));
		
		MeasuringMXBean mxBean = getInstance();
		mxBean.report(service, method, duration);

		return result;
	}
	
	private MeasuringMXBean getInstance() throws MalformedObjectNameException {
		ObjectName on = new ObjectName("com.prodyna.pac.mmonshausen.conference:service=MeasuringBean");
		return MBeanServerInvocationHandler.newProxyInstance(mBeanServer, on, MeasuringMXBean.class, false);
	}

	private String buildLogMessage(final String service, final String method,
			final String parameters, final long duration) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("Aufruf ");
		buffer.append(service + ".");
		buffer.append(method);
		buffer.append("(" + parameters + ") ");
		buffer.append("Laufzeit ");
		buffer.append(duration + " ms");
		return buffer.toString();
	}

	private String arrayToString(final Object[] parameters) {
		final StringBuffer result = new StringBuffer();

		for (final Object object : parameters) {
			if (result.length() > 0) {
				result.append(", ");
			}
			result.append(object);
		}
		return result.toString();
	}
}