package com.prodyna.pac.mmonshausen.conference.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;

import com.prodyna.pac.mmonshausen.conference.model.Conference;
import com.prodyna.pac.mmonshausen.conference.model.Room;
import com.prodyna.pac.mmonshausen.conference.model.Speaker;
import com.prodyna.pac.mmonshausen.conference.model.Talk;
import com.prodyna.pac.mmonshausen.conference.service.ConferenceService;
import com.prodyna.pac.mmonshausen.conference.service.RoomService;
import com.prodyna.pac.mmonshausen.conference.service.SpeakerService;
import com.prodyna.pac.mmonshausen.conference.service.TalkService;

/**
 * JSF-Controller for conferences
 * 
 * @author Martin Monshausen, PRODYNA AG
 */
@Model
public class TalkController {
	
	@Inject
	private TalkService talkService;
	
	@Inject
	private RoomService roomService;
	
	@Inject
	private SpeakerService speakerService;
	
	@Inject
	private ConferenceService conferenceService;
	
	private Talk talk;
	private Long id;
	private String mode;
	private Long conferenceId;
	private Long roomId;
	private Long[] speakerIds; 
	
	public void createTalk() {
		resolveIdsToObjects();
		talkService.createTalk(talk);
	}
	
	@Produces
	@Named
	public Talk getTalk() {
		if(talk != null) {
			return talk;
		} else {
			return new Talk();
		}
	}
	
	public Conference[] getConferenceList() {
		final List<Conference> conferenceList = conferenceService.listAllConferences();
		
		if(conferenceList != null) {
			return conferenceList.toArray(new Conference[conferenceList.size()]);
		} else {
			return new Conference[0];
		}
	}
	
	public Room[] getRoomList() {
		final List<Room> roomList = roomService.listAllRooms();
		
		if(roomList != null) {
			return roomList.toArray(new Room[roomList.size()]);
		} else {
			return new Room[0];
		}
	}
	
	public Speaker[] getSpeakerList() {
		final List<Speaker> speakerList = speakerService.listAllSpeakers();
		
		if(speakerList != null) {
			return speakerList.toArray(new Speaker[speakerList.size()]);
		} else {
			return new Speaker[0];
		}
	}
	
	public void saveChanges() {
		resolveIdsToObjects();
		talkService.updateTalk(talk);
	}

	private void resolveIdsToObjects() {
		final Room room = roomService.getRoomById(roomId);
		talk.setRoom(room);
		
		final Conference conference = conferenceService.getConferenceById(conferenceId);
		talk.setConference(conference);
		
		final List<Speaker> speakers = new ArrayList<Speaker>();
		for (final Long speakerId : speakerIds) {
			speakers.add(speakerService.getSpeakerById(speakerId));
		}
		talk.setSpeakers(speakers);
	}
	
	public void deleteTalk(final Long id) {
		talkService.deleteTalk(id);
	}
	
	@PostConstruct
	public void initialize() {
		if((id != null) && (id !=0)) {
			talk = talkService.getTalkById(id);
			roomId = talk.getRoom().getId();
			
			final List<Speaker> speakerList = talk.getSpeakers();
			speakerIds = toSpeakerIdArray(speakerList);
		} else {
			talk = new Talk();
		}
	}
		
	private Long[] toSpeakerIdArray(final List<Speaker> speakerList) {
		final Long[] speakerIds = new Long[speakerList.size()];
		
		int i = 0;
		for (final Speaker speaker : speakerList) {
			speakerIds[i] = speaker.getId();
			i++;
		}
		return speakerIds;
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
	
	public Long getRoomId() {
		return roomId;
	}

	public void setRoomId(Long roomId) {
		this.roomId = roomId;
	}

	public Long[] getSpeakerIds() {
		return speakerIds;
	}

	public void setSpeakerIds(Long[] speakerIds) {
		this.speakerIds = speakerIds;
	}

	public Long getConferenceId() {
		return conferenceId;
	}

	public void setConferenceId(Long conferenceId) {
		this.conferenceId = conferenceId;
	}
}