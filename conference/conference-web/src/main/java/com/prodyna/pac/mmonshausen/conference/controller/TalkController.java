package com.prodyna.pac.mmonshausen.conference.controller;

import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;

import com.prodyna.pac.mmonshausen.conference.model.Talk;
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
	
	private Talk talk;
	private Long id;
	
	public void createConference() {
		talkService.createTalk(talk);
	}
	
	@Produces
	@Named
	public Talk getConference() {
		if(talk != null) {
			return talk;
		} else {
			return new Talk();
		}
	}
	
	public void saveChanges() {
		talkService.updateTalk(talk);
	}
	
	public void deleteChanges() {
		talkService.deleteTalk(id);
	}
	
	private void initialize() {
		if((id != null) && (id !=0)) {
			talkService.getTalkById(id);			
		}
	}
		
	public void setId(Long id) {
		this.id = id;
		initialize();
	}
}