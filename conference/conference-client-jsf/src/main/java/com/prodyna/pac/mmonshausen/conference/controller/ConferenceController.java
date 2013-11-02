package com.prodyna.pac.mmonshausen.conference.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

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
import com.prodyna.pac.mmonshausen.conference.model.Location;
import com.prodyna.pac.mmonshausen.conference.model.Talk;
import com.prodyna.pac.mmonshausen.conference.service.ConferenceService;
import com.prodyna.pac.mmonshausen.conference.service.LocationService;
import com.prodyna.pac.mmonshausen.conference.service.TalkService;
import com.prodyna.pac.mmonshausen.conference.util.InputValidator;
import com.prodyna.pac.mmonshausen.conference.util.JSFMessageHelper;

/**
 * JSF-Controller for conferences
 * 
 * @author Martin Monshausen, PRODYNA AG
 */
@Model
public class ConferenceController {
	@Inject
	private InputValidator inputValidator;
	
	@Inject
	private FacesContext facesContext;
	
	@Inject
	private JSFMessageHelper msgHelper;

	@Inject
	private ConferenceService conferenceService;
	
	@Inject
	private TalkService talkService;
	
	@Inject
	private LocationService locationService;
	
	private Conference conference;
	private Long id;

	private String mode;
	private Long locationId;
	
	@Produces
	@Named
	public Conference getConference() {
		if (conference != null) {
			return conference;
		} else {
			return new Conference();
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
	
	public Map<Date, List<Talk>> getTalksByDay() {
		final List<Talk> talks = talkService.getConferenceTalksOrderedByDateTime(id);
		
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
	
	public void createConference() {
		try {
			resolveIdsToObjects();
			
			inputValidator.validateConference(conference);
			
			conferenceService.createConference(conference);
        }  catch (final ConstraintViolationException e) {
        	final FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_WARN, msgHelper.getConstraintViolationMessage(e) , msgHelper.getConstraintViolationMessage(e));
            facesContext.addMessage(null, m);
		} catch (final ValidationException e) {
			final FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_WARN, e.getMessage(), e.getMessage());
            facesContext.addMessage(null, m);
		} catch (final Exception e) {
            final FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR, msgHelper.getRootErrorMessage(e), msgHelper.getRootErrorMessage(e));
            facesContext.addMessage(null, m);
        }
	}

	public void saveChanges() {
		try {
			resolveIdsToObjects();
			
			inputValidator.validateConference(conference);
			
			conferenceService.updateConference(conference);
        }  catch (final ConstraintViolationException e) {
        	final FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_WARN, msgHelper.getConstraintViolationMessage(e), msgHelper.getConstraintViolationMessage(e));
            facesContext.addMessage(null, m);
		} catch (final ValidationException e) {
			final FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_WARN, e.getMessage(), e.getMessage());
            facesContext.addMessage(null, m);
		} catch (final Exception e) {
            final FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR, msgHelper.getRootErrorMessage(e), msgHelper.getRootErrorMessage(e));
            facesContext.addMessage(null, m);
        }		
	}
	
	private void resolveIdsToObjects() {
		final Location location = locationService.getLocationById(locationId);
		conference.setLocation(location);
	}
	
	public String deleteConference(final long id) {
		try {
			conferenceService.deleteConference(id);
        } catch (final Exception e) {
            final FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR, msgHelper.getRootErrorMessage(e), msgHelper.getRootErrorMessage(e));
            facesContext.addMessage(null, m);
        }
		return "conference_admin";
	}
	
	@PostConstruct
	public void initialize() {
		if((id != null) && (id !=0)) {
			conference = conferenceService.getConferenceById(id);
			locationId = conference.getLocation().getId();
		} else {
			conference = new Conference();
		}
	}
	
	public Long getId() {
		return id;
	}
		
	public void setId(final Long id) {
		this.id = id;
		initialize();
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

	public void setLocationId(final Long locationId) {
		this.locationId = locationId;
	}
}