package com.prodyna.pac.mmonshausen.conference.service;

import java.util.List;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.prodyna.pac.mmonshausen.conference.model.Room;
import com.prodyna.pac.mmonshausen.conference.util.MeasuringInterceptor;

/**
 * service bean containing crud operations for rooms
 * 
 * @author Martin Monshausen, PRODYNA AG
 */
@Stateless
@Interceptors(MeasuringInterceptor.class)
public class RoomServiceBean implements RoomService {

	@Inject
	private EntityManager em;

	@Inject
	private Logger logger;

	@Override
	public Room createRoom(final Room room) {
		em.persist(room);
		return room;
	}

	/*
	 * (non-Javadoc)
	 * @see com.prodyna.pac.mmonshausen.conference.service.RoomService#getRoomById(long)
	 */
	@Override
	public Room getRoomById(final long id) {
		final String queryString = "SELECT room FROM Room room left join fetch room.talks WHERE room.id = :roomId";
		final TypedQuery<Room> query = em.createQuery(queryString,
				Room.class);
		query.setParameter("roomId", id);
		final List<Room> resultList = query.getResultList();

		if(resultList.isEmpty()) {
			logger.warning("room (id="+id+") not found!");
			return null;
		} else {
			return resultList.get(0);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.prodyna.pac.mmonshausen.conference.service.RoomService#listAllRooms()
	 */
	@Override
	public List<Room> listAllRooms() {
		final String queryString = "SELECT room FROM Room room left join fetch room.talks";
		final TypedQuery<Room> query = em.createQuery(queryString,
				Room.class);
		final List<Room> resultList = query.getResultList();

		if(resultList.isEmpty()) {
			logger.warning("no rooms existing!");
		}

		return resultList;
	}

	/*
	 * (non-Javadoc)
	 * @see com.prodyna.pac.mmonshausen.conference.service.RoomService#updateRoom(com.prodyna.pac.mmonshausen.conference.model.Room)
	 */
	@Override
	public Room updateRoom(final Room room) {
		final Long id = room.getId();
		
		if(id != null) {
			final Room persistedRoom = getRoomById(id);
			
			if(persistedRoom != null) {
				persistedRoom.setCapacity(room.getCapacity());
				persistedRoom.setName(room.getName());
				
				return em.merge(persistedRoom);
			} else {
				logger.warning("room (id="+id+") not found; could not update room!");
			}
		} else {
			logger.warning("room has no id; perhaps it had not been persisted yet!");
		}
		
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see com.prodyna.pac.mmonshausen.conference.service.RoomService#deleteRoom(long)
	 */
	@Override
	public void deleteRoom(final long id) {
		final Room room = getRoomById(id);

		if(room != null) {
			em.remove(room);
			logger.info("room (id="+id+") successfully deleted");
		} else {
			logger.warning("room (id="+id+") not found; nothing deleted!");
		}
	}
}