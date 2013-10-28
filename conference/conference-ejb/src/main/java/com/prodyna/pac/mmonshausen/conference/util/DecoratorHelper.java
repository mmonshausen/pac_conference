package com.prodyna.pac.mmonshausen.conference.util;

import java.util.logging.Logger;

import javax.inject.Inject;
import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.QueueConnectionFactory;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.NamingException;

/**
 * holds helper methods for Decorators, e.q. sending queue messages
 * 
 * @author Martin Monshausen, PRODYNA AG
 */
public class DecoratorHelper {
	
	@Inject
	private Logger logger;
	
	@Inject
	private Context ctx;

	@Inject
	private QueueConnectionFactory qcf;
	
	/**
	 * send given text message to queue
	 * 
	 * @param message
	 *            text message to send to queue
	 */
	public void sendQueueMessage(final String message) {
		try {
			final Queue queue = (Queue) ctx.lookup( "queue/log" );
			final Connection conn = qcf.createQueueConnection();
			conn.start();

			final Session session = conn.createSession(true, Session.AUTO_ACKNOWLEDGE);
			final MessageProducer producer = session.createProducer(queue);

			final TextMessage msg = session.createTextMessage(message);
			producer.send(msg);

			producer.close();
			session.commit();
			session.close();
			conn.stop();
			conn.close();
		} catch (final NamingException e) {
			logger.warning("NamingException aufgetreten!");
		} catch (final JMSException e) {
			logger.warning("JMSException aufgetreten!");
		}
	}
}