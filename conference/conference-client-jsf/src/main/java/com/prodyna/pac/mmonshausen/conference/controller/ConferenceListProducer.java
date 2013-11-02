package com.prodyna.pac.mmonshausen.conference.controller;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.event.Reception;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;

import com.prodyna.pac.mmonshausen.conference.model.Conference;
import com.prodyna.pac.mmonshausen.conference.service.ConferenceService;
import com.prodyna.pac.mmonshausen.conference.service.Invaded;

/**
 * gets and holds List of conferences
 * 
 * @author Martin Monshausen, PRODYNA AG
 */
@ApplicationScoped
public class ConferenceListProducer {
	@Inject
	private ConferenceService conferenceService;
	
	private List<Conference> conferenceList;
	
	@PostConstruct
	public void retrieveConferenceList() {
		conferenceList = conferenceService.listAllConferences();
	}
	
	public void onConferenceListChanged(@Observes(notifyObserver = Reception.IF_EXISTS) @Invaded final Conference conference) {
        retrieveConferenceList();
    }
	
	@Named
	@Produces
	public List<Conference> getConferences() {
		return conferenceList;
	}
}