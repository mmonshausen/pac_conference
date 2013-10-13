package com.prodyna.pac.mmonshausen.conference.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;

import com.prodyna.pac.mmonshausen.conference.model.Conference;
import com.prodyna.pac.mmonshausen.conference.model.Talk;
import com.prodyna.pac.mmonshausen.conference.service.ConferenceService;
import com.prodyna.pac.mmonshausen.conference.service.TalkService;

/**
 * JSF-Controller for conferences
 * 
 * @author Martin Monshausen, PRODYNA AG
 */
@Model
public class ConferenceController {
	
	@Inject
	private ConferenceService conferenceService;
	
	@Inject
	private TalkService talkService;
	
	private Conference conference;
	private Long id;
	
	public void createConference() {
		conferenceService.saveConference(conference);
	}
	
	@Produces
	@Named
	public Conference getConference() {
		if(conference != null) {
			return conference;
		} else {
			return new Conference();
		}
	}
	
	@Produces
	@Named
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
	
	public void saveChanges() {
		conferenceService.updateConference(conference);
	}
	
	public void deleteChanges() {
		conferenceService.deleteConference(conference.getId());
	}
	
	private void initialize() {
		if((id != null) && (id !=0)) {
			conference = conferenceService.getConferenceById(id);			
		}
	}
		
	public void setId(final Long id) {
		this.id = id;
		initialize();
	}
}