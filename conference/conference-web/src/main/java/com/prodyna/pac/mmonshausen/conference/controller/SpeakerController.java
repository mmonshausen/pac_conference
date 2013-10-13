package com.prodyna.pac.mmonshausen.conference.controller;

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
	
	public void createConference() {
		speakerService.saveSpeaker(speaker);
	}
	
	@Produces
	@Named
	public Speaker getSpeaker() {
		if(speaker != null) {
			return speaker;
		} else {
			return new Speaker();
		}
	}
	
	public void saveChanges() {
		speakerService.updateSpeaker(speaker);
	}
	
	public void deleteChanges() {
		speakerService.deleteSpeaker(id);
	}
	
	private void initialize() {
		if((id != null) && (id !=0)) {
			speakerService.getSpeakerById(id);			
		}
	}
		
	public void setId(final Long id) {
		this.id = id;
		initialize();
	}
}