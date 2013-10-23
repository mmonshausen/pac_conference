package com.prodyna.pac.mmonshausen.conference.service;

import java.util.logging.Logger;

import javax.decorator.Decorator;
import javax.decorator.Delegate;
import javax.enterprise.event.Event;
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

import com.prodyna.pac.mmonshausen.conference.model.Speaker;

/**
 * Decorator which intercepts methods and sends notifications to observers and queue messages
 * 
 * @author Martin Monshausen, PRODYNA AG
 */
@Decorator
public abstract class SpeakerServiceDecorator implements SpeakerService {
	@Inject
	private Logger logger;

	@Inject @Invaded
	private Event<Speaker> event;

	@Inject
	@Delegate
	private SpeakerService speakerService;

	@Inject
	Context ctx;

	@Inject
	QueueConnectionFactory qcf;

	@Override
	public Speaker saveSpeaker(final Speaker speaker) {
		event.fire(speaker);
		sendQueueMessage("speaker erstellt");
		return speakerService.saveSpeaker(speaker);
	}

	@Override
	public Speaker updateSpeaker(final Speaker speaker) {
		event.fire(speaker);
		sendQueueMessage("speaker upgedated!");
		return speakerService.updateSpeaker(speaker);
	}

	private void sendQueueMessage(final String message) {
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

	@Override
	public void deleteSpeaker(final long id) {
		event.fire(new Speaker());
		sendQueueMessage("speaker geloescht!");	
		deleteSpeaker(id);
	}
}
