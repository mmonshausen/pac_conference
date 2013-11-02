package com.prodyna.pac.mmonshausen.conference.controller;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.event.Reception;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;

import com.prodyna.pac.mmonshausen.conference.model.Speaker;
import com.prodyna.pac.mmonshausen.conference.service.Invaded;
import com.prodyna.pac.mmonshausen.conference.service.SpeakerService;

/**
 * gets and holds list of speakers
 * 
 * @author Martin Monshausen, PRODYNA AG
 */
@ApplicationScoped
public class SpeakerListProducer {
	@Inject
	private SpeakerService speakerService;
	
	private List<Speaker> speakerList;
	
	@PostConstruct
	public void retrieveSpeakerList() {
		speakerList = speakerService.listAllSpeakers();
	}
	
	public void onSpeakerListChanged(@Observes(notifyObserver = Reception.IF_EXISTS) @Invaded final Speaker speaker) {
        retrieveSpeakerList();
    }
	
	@Named
	@Produces
	public List<Speaker> getSpeakers() {
		return speakerList;
	}
}