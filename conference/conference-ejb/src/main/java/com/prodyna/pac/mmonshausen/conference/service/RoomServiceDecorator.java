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

import com.prodyna.pac.mmonshausen.conference.model.Room;

/**
 * Decorator which intercepts methods and sends notifications to observers and queue messages
 * 
 * @author Martin Monshausen, PRODYNA AG
 */
@Decorator
public abstract class RoomServiceDecorator implements RoomService {
	@Inject
	private Logger logger;

	@Inject @Invaded
	private Event<Room> event;

	@Inject
	@Delegate
	private RoomService roomService;

	@Inject
	Context ctx;

	@Inject
	QueueConnectionFactory qcf;

	@Override
	public Room createRoom(final Room room) {
		event.fire(room);
		sendQueueMessage("room erstellt");
		return roomService.createRoom(room);
	}

	@Override
	public Room updateRoom(final Room room) {
		event.fire(room);
		sendQueueMessage("room upgedated!");
		return roomService.updateRoom(room);
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
	public void deleteRoom(final long id) {
		event.fire(new Room());
		sendQueueMessage("room geloescht!");	
		deleteRoom(id);
	}
}
