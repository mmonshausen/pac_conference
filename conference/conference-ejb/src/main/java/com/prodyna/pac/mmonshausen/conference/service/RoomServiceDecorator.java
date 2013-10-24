package com.prodyna.pac.mmonshausen.conference.service;

import javax.decorator.Decorator;
import javax.decorator.Delegate;
import javax.enterprise.event.Event;
import javax.inject.Inject;

import com.prodyna.pac.mmonshausen.conference.model.Room;
import com.prodyna.pac.mmonshausen.conference.util.DecoratorHelper;

/**
 * Decorator which intercepts methods and sends notifications to observers and queue messages
 * 
 * @author Martin Monshausen, PRODYNA AG
 */
@Decorator
public abstract class RoomServiceDecorator implements RoomService {
	@Inject @Invaded
	private Event<Room> event;

	@Inject
	@Delegate
	private RoomService roomService;

	@Inject
	private DecoratorHelper decoHelper;
	
	@Override
	public Room createRoom(final Room room) {
		event.fire(room);
		
		decoHelper.sendQueueMessage("room [id="+room.getId()+" name="+room.getName()+ "] erstellt");
		
		return roomService.createRoom(room);
	}

	@Override
	public Room updateRoom(final Room room) {
		event.fire(room);
		
		decoHelper.sendQueueMessage("room [id="+room.getId()+" name="+room.getName()+ "] upgedated");
		
		return roomService.updateRoom(room);
	}

	@Override
	public void deleteRoom(final long id) {
		event.fire(new Room());
		
		decoHelper.sendQueueMessage("room [id="+id+ "] geloescht");
		
		deleteRoom(id);
	}
}
