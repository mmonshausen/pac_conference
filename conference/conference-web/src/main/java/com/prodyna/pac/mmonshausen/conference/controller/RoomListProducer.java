package com.prodyna.pac.mmonshausen.conference.controller;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.event.Reception;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;

import com.prodyna.pac.mmonshausen.conference.model.Room;
import com.prodyna.pac.mmonshausen.conference.service.RoomService;

/**
 * gets and holds list of rooms
 * 
 * @author Martin Monshausen, PRODYNA AG
 */
@ApplicationScoped
public class RoomListProducer {
	@Inject
	private RoomService roomService;
	
	private List<Room> roomList;
	
	@PostConstruct
	public void retrieveRoomList() {
		roomList = roomService.listAllRooms();
	}
	
	public void onRoomListChanged(@Observes(notifyObserver = Reception.IF_EXISTS) final Room room) {
        retrieveRoomList();
    }
	
	@Named
	@Produces
	public List<Room> getRooms() {
		return roomList;
	}
}