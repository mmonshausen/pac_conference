package com.prodyna.pac.mmonshausen.conference.service;

import java.util.List;

import com.prodyna.pac.mmonshausen.conference.model.Conference;

/**
 * interface specification for ConferenceService
 * 
 * @author Martin Monshausen, PRODYNA AG
 */
public interface ConferenceService {
	public Conference saveConference(Conference conference);
	
	public Conference getConferenceById(long id);
	public List<Conference> listAllConferences();
	public List<Conference> getConferenceForLocation(long id);
	
	public Conference updateConference(Conference conference);
	
	public void deleteConference(long id);
}
