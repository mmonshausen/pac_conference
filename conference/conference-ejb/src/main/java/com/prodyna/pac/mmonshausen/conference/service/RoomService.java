package com.prodyna.pac.mmonshausen.conference.service;

import java.util.List;

import com.prodyna.pac.mmonshausen.conference.model.Room;

/**
 * interface specification for RoomService
 * 
 * @author Martin Monshausen, PRODYNA AG
 */
public interface RoomService {
	public Room createRoom(Room room);
	
	public Room getRoomById(long id);
	public List<Room> listAllRooms();
	public List<Room> getRoomsForLocation(long id);
	
	public Room updateRoom(Room room);
	
	public void deleteRoom(long id);
}
