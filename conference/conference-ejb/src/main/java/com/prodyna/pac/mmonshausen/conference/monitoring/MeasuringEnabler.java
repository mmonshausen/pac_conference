package com.prodyna.pac.mmonshausen.conference.monitoring;

import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import javax.management.InstanceAlreadyExistsException;
import javax.management.MBeanRegistrationException;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.NotCompliantMBeanException;
import javax.management.ObjectName;

/**
 * enables MeasuringBean for JMX usage
 * 
 * @author Martin Monshausen, PRODYNA AG
 */
@Singleton
@Startup
public class MeasuringEnabler {

	@Inject
	private MBeanServer mbServer;

	@Inject
	private Logger logger;

	@PostConstruct
	public void postConstruct() {
		try {
			final ObjectName on = new ObjectName("com.prodyna.pac.mmonshausen.conference:service=MeasuringBean");
			mbServer.registerMBean(new MeasuringBean(), on);
		} catch (final MalformedObjectNameException e) {
			logger.warning(e.getMessage());
		} catch (final InstanceAlreadyExistsException e) {
			logger.warning(e.getMessage());
		} catch (final MBeanRegistrationException e) {
			logger.warning(e.getMessage());
		} catch (final NotCompliantMBeanException e) {
			logger.warning(e.getMessage());
		}
	}

	public void preDestroy() {
		try {
			final ObjectName on = new ObjectName("com.prodyna.pac.mmonshausen.conference:service=MeasuringBean");
			mbServer.unregisterMBean(on);
		} catch(final Exception e) {
			e.printStackTrace();
		}
	}
}
