package com.prodyna.pac.mmonshausen.conference.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;

import com.prodyna.pac.mmonshausen.conference.model.Conference;
import com.prodyna.pac.mmonshausen.conference.model.Room;
import com.prodyna.pac.mmonshausen.conference.model.Speaker;
import com.prodyna.pac.mmonshausen.conference.model.Talk;
import com.prodyna.pac.mmonshausen.conference.service.ConferenceService;
import com.prodyna.pac.mmonshausen.conference.service.RoomService;
import com.prodyna.pac.mmonshausen.conference.service.SpeakerService;
import com.prodyna.pac.mmonshausen.conference.service.TalkService;
import com.prodyna.pac.mmonshausen.conference.util.InputValidator;
import com.prodyna.pac.mmonshausen.conference.util.JSFMessageHelper;

/**
 * JSF-Controller for conferences
 * 
 * @author Martin Monshausen, PRODYNA AG
 */
@Model
public class TalkController {
	@Inject
	private InputValidator inputValidator;
	
	@Inject
	private FacesContext facesContext;
	
	@Inject
	private JSFMessageHelper msgHelper;
	
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
	
	public void createTalk() {
		try {
			resolveIdsToObjects();
			
			inputValidator.validateTalk(talk);
			
			talkService.createTalk(talk);
		}  catch (final ConstraintViolationException e) {
			final FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_WARN, "Validierungsfehler", msgHelper.getConstraintViolationMessage(e));
			facesContext.addMessage(null, m);
		} catch (final ValidationException e) {
			final FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_WARN, "Validierungsfehler", e.getMessage());
			facesContext.addMessage(null, m);
		} catch (final Exception e) {
			final FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR, "interner Fehler", msgHelper.getRootErrorMessage(e));
			facesContext.addMessage(null, m);
		}
	}
	
	public void saveChanges() {
		try {
			resolveIdsToObjects();
			
			inputValidator.validateTalk(talk);
			
			talkService.updateTalk(talk);
        }  catch (final ConstraintViolationException e) {
        	final FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_WARN, "Validierungsfehler", msgHelper.getConstraintViolationMessage(e));
            facesContext.addMessage(null, m);
		} catch (final ValidationException e) {
			final FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_WARN, "Validierungsfehler", e.getMessage());
            facesContext.addMessage(null, m);
		} catch (final Exception e) {
            final FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR, "interner Fehler", msgHelper.getRootErrorMessage(e));
            facesContext.addMessage(null, m);
        }
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
		try {
			talkService.deleteTalk(id);
		} catch (final Exception e) {
            final FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR, "interner Fehler", msgHelper.getRootErrorMessage(e));
            facesContext.addMessage(null, m);
        }
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

	public void setRoomId(final Long roomId) {
		this.roomId = roomId;
	}

	public Long[] getSpeakerIds() {
		return speakerIds;
	}

	public void setSpeakerIds(final Long[] speakerIds) {
		this.speakerIds = speakerIds;
	}

	public Long getConferenceId() {
		return conferenceId;
	}

	public void setConferenceId(final Long conferenceId) {
		this.conferenceId = conferenceId;
	}
}