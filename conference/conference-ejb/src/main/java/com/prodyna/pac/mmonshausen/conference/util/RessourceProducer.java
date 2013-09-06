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
	@Produces
	@PersistenceContext
	private EntityManager em;

	@Produces
	public Logger produceLogger(final InjectionPoint injectionPoint) {
		return Logger.getLogger(injectionPoint.getMember().getDeclaringClass()
				.getName());		
	}

	@Produces
	public MBeanServer produceMBeanServer() {
		return ManagementFactory.getPlatformMBeanServer();
	}

	@Produces
	public InitialContext produceContext() {
		try {
			return new InitialContext();
		} catch (final NamingException e) {
			//TODO: How to handle exceptions in producers
		}
		return null;
	}

	@Produces
	public QueueConnectionFactory produceQCF() {
		try {
			return (QueueConnectionFactory) produceContext().lookup("ConnectionFactory");
		} catch (final NamingException e) {
			//TODO: How to handle exceptions in producers
		}
		return null;
	}
}
