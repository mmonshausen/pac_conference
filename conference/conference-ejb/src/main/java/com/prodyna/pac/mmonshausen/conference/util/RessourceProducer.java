package com.prodyna.pac.mmonshausen.conference.util;

import java.lang.management.ManagementFactory;
import java.util.logging.Logger;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.jms.QueueConnectionFactory;
import javax.management.MBeanServer;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * class which provides producers for use in classes<br>
 * <p>em allows to easily get an entity manager</p>
 * <p>produceLogger injects logger correctly initialized for class used in</p>
 * <p>produceMBeanServer injects an MBeanServer used to expose MBean<p>
 * <p>produceContext injects an InitialContext<p>
 * <p>produceQCF injects an QueueConnectionFactory used to send messages to queues</p> 
 * 
 * @author Martin Monshausen, PRODYNA AG
 */
public class RessourceProducer {
	Logger logger = Logger.getLogger(RessourceProducer.class.getName());
	
	@Produces
	@PersistenceContext
	private EntityManager em;

	/**
	 * produce {@link Logger} instance; the instance will be tailored to class
	 * using Logger by {@link InjectionPoint}
	 * 
	 * @param injectionPoint
	 *            representing information about class in which Logger will be
	 *            injected; used to tailor Logger to class using it
	 * @return {@link Logger} tailored to class using it
	 */
	@Produces
	public Logger produceLogger(final InjectionPoint injectionPoint) {
		return Logger.getLogger(injectionPoint.getMember().getDeclaringClass()
				.getName());		
	}

	/**
	 * produce {@link MBeanServer} instance ready to be injected in classes
	 * 
	 * @return {@link MBeanServer} instance
	 */
	@Produces
	public MBeanServer produceMBeanServer() {
		return ManagementFactory.getPlatformMBeanServer();
	}

	/**
	 * produce {@link InitialContext} instance ready to be injected in classes
	 * 
	 * @return {@link InitialContext} instance
	 */
	@Produces
	public InitialContext produceContext() {
		try {
			return new InitialContext();
		} catch (final NamingException e) {
			logger.warning(e.getMessage());
		}
		return null;
	}

	/**
	 * produce {@link QueueConnectionFactory} instance ready to be injected in
	 * classes<br>
	 * {@link QueueConnectionFactory} is necessary to send queue messages
	 * 
	 * @return {@link QueueConnectionFactory} instance
	 */
	@Produces
	public QueueConnectionFactory produceQCF() {
		try {
			return (QueueConnectionFactory) produceContext().lookup("ConnectionFactory");
		} catch (final NamingException e) {
			logger.warning(e.getMessage());
		}
		return null;
	}
}