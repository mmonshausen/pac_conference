package com.prodyna.pac.mmonshausen.conference.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;

import com.prodyna.pac.mmonshausen.conference.model.Location;
import com.prodyna.pac.mmonshausen.conference.model.Room;
import com.prodyna.pac.mmonshausen.conference.model.Talk;
import com.prodyna.pac.mmonshausen.conference.service.LocationService;
import com.prodyna.pac.mmonshausen.conference.service.RoomService;
import com.prodyna.pac.mmonshausen.conference.service.TalkService;

/**
 * JSF-Controller for conferences
 * 
 * @author Martin Monshausen, PRODYNA AG
 */
@Model
public class RoomController {
	
	@Inject
	private RoomService roomService;
	
	@Inject
	private TalkService talkService;
	
	@Inject
	private LocationService locationService;
	
	private Room room;
	private Long id;
	private String mode;
	private Long locationId;
	
	public Map<Date, List<Talk>> getTalksByDay() {
		final List<Talk> talks = talkService.getRoomTalksOrderedByDateTime(id);
		
		final Map<Date, List<Talk>> talksGroupedByDate = new TreeMap<Date, List<Talk>>();
		for (final Talk talk : talks) {
			final Date date = talk.getDate();
			
			List<Talk> talkList = talksGroupedByDate.get(date);
			if(talkList == null) {
				talkList = new ArrayList<Talk>();
				talksGroupedByDate.put(date, talkList);
			}
			
			talkList.add(talk);
		}
		
		return talksGroupedByDate;
	}
	
	@Produces
	@Named
	public Room getRoom() {
		if(room != null) {
			return room;
		} else {
			return new Room();
		}
	}
	
	public Location[] getLocationList() {
		final List<Location> locationList = locationService.listAllLocations();
		
		if(locationList != null) {
			return locationList.toArray(new Location[locationList.size()]);
		} else {
			return new Location[0];
		}
	}
	
	public void createRoom() {
		resolveIdsToObjects();
		roomService.createRoom(room);
	}
	
	public void saveChanges() {
		resolveIdsToObjects();
		roomService.updateRoom(room);
	}

	private void resolveIdsToObjects() {
		final Location location = locationService.getLocationById(locationId);
		room.setLocation(location);
	}
	
	public void deleteRoom(final long id) {
		roomService.deleteRoom(id);
	}
	
	@PostConstruct
	public void initialize() {
		if((id != null) && (id !=0)) {
			room = roomService.getRoomById(id);
			locationId = room.getLocation().getId();
		} else {
			room = new Room();
		}
	}
		
	public void setId(final Long id) {
		this.id = id;
		initialize();
	}

	public Long getId() {
		return id;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(final String mode) {
		this.mode = mode;
	}
	
	public Long getLocationId() {
		return locationId;
	}

	public void setLocationId(Long locationId) {
		this.locationId = locationId;
	}
}