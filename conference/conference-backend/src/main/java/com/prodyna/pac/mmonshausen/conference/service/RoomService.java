package com.prodyna.pac.mmonshausen.conference.service;

import java.util.List;

import com.prodyna.pac.mmonshausen.conference.model.Room;

/**
 * interface specification for RoomService<br>
 * RoomService is responsible for performing CRUD operations for entity
 * {@link Room}
 * 
 * @author Martin Monshausen, PRODYNA AG
 */
public interface RoomService {
	
	/**
	 * persists given {@link Room} to database
	 * 
	 * @param room
	 *            object to persist
	 * @return object persisted (including id)
	 */
	public Room createRoom(Room room);
	
	/**
	 * reads {@link Room} for database for given id<br>
	 * if nothing found for id warning will be logged
	 * 
	 * @param id
	 *            id of {@link Room} to be read from database
	 * @return if found corresponding {@link Room} object, otherwise null
	 */
	public Room getRoomById(long id);
	
	/**
	 * reads {@link Room} for database for given locationId<br>
	 * if nothing found for id warning will be logged
	 * 
	 * @param id
	 *            id of {@link Location} for which all rooms should be read from database
	 * @return if found corresponding {@link Room} object, otherwise null
	 */
	public List<Room> getLocationRooms(long id);
	
	/**
	 * reads all {@link Room} objects from database<br>
	 * if there are no {@link Room} return empty list
	 * 
	 * @return if found list of {@link Room}, otherwise empty list
	 */
	public List<Room> listAllRooms();
	
	/**
	 * persists changes on the {@link Room} to database
	 * 
	 * @param room
	 *            object with changes to be stored to database
	 * @return object with merged changes
	 */
	public Room updateRoom(Room room);
	
	/**
	 * removed object with given id from database<br>
	 * if there is no object with given id log warning
	 * 
	 * @param id
	 *            id of {@link Room} to be deleted
	 */
	public void deleteRoom(long id);

}