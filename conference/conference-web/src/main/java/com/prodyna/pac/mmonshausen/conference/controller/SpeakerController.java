package com.prodyna.pac.mmonshausen.conference.controller;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;

import com.prodyna.pac.mmonshausen.conference.model.Speaker;
import com.prodyna.pac.mmonshausen.conference.service.SpeakerService;

/**
 * JSF-Controller for conferences
 * 
 * @author Martin Monshausen, PRODYNA AG
 */
@Model
public class SpeakerController {
	
	@Inject
	private SpeakerService speakerService;
	
	private Speaker speaker;
	private Long id;
	private String mode;
	
	@Produces
	@Named
	public Speaker getSpeaker() {
		if(speaker != null) {
			return speaker;
		} else {
			return new Speaker();
		}
	}
	
	public void createSpeaker() {
		speakerService.saveSpeaker(speaker);
	}
	
	public void saveChanges() {
		speakerService.updateSpeaker(speaker);
	}
	
	public void deleteChanges(long id) {
		speakerService.deleteSpeaker(id);
	}
	
	@PostConstruct
	public void initialize() {
		if((id != null) && (id !=0)) {
			speaker = speakerService.getSpeakerById(id);			
		} else {
			speaker = new Speaker();
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

	public void setMode(String mode) {
		this.mode = mode;
	}
}