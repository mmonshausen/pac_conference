package com.prodyna.pac.mmonshausen.conference.service;

import javax.decorator.Decorator;
import javax.decorator.Delegate;
import javax.enterprise.event.Event;
import javax.inject.Inject;

import com.prodyna.pac.mmonshausen.conference.model.Room;
import com.prodyna.pac.mmonshausen.conference.util.DecoratorHelper;

/**
 * Decorator which intercepts {@link RoomService} methods and sends
 * notifications to observers and queue messages
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
	
	/*
	 * (non-Javadoc)
	 * @see com.prodyna.pac.mmonshausen.conference.service.RoomService#createRoom(com.prodyna.pac.mmonshausen.conference.model.Room)
	 */
	@Override
	public Room createRoom(final Room room) {
		final Room resultRoom = roomService.createRoom(room);
		
		event.fire(resultRoom);
		
		decoHelper.sendQueueMessage("room [id="+room.getId()+" name="+room.getName()+ "] erstellt");
		
		return resultRoom;
	}

	/*
	 * (non-Javadoc)
	 * @see com.prodyna.pac.mmonshausen.conference.service.RoomService#updateRoom(com.prodyna.pac.mmonshausen.conference.model.Room)
	 */
	@Override
	public Room updateRoom(final Room room) {
		final Room resultRoom = roomService.updateRoom(room);
		
		event.fire(resultRoom);
		
		decoHelper.sendQueueMessage("room [id="+room.getId()+" name="+room.getName()+ "] upgedated");
		
		return resultRoom;
	}

	/*
	 * (non-Javadoc)
	 * @see com.prodyna.pac.mmonshausen.conference.service.RoomService#deleteRoom(long)
	 */
	@Override
	public void deleteRoom(final long id) {
		roomService.deleteRoom(id);
		
		event.fire(new Room());
		
		decoHelper.sendQueueMessage("room [id="+id+ "] geloescht");
	}
}