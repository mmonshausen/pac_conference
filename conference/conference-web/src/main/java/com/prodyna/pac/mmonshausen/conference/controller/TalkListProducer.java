package com.prodyna.pac.mmonshausen.conference.controller;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.event.Reception;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;

import com.prodyna.pac.mmonshausen.conference.model.Talk;
import com.prodyna.pac.mmonshausen.conference.service.TalkService;

/**
 * gets and holds list of talks
 * 
 * @author Martin Monshausen, PRODYNA AG
 */
@ApplicationScoped
public class TalkListProducer {
	@Inject
	private TalkService talkService;
	
	private List<Talk> talkList;
	
	@PostConstruct
	public void retrieveTalkList() {
		talkList = talkService.listAllTalks();
	}
	
	public void onTalkListChanged(@Observes(notifyObserver = Reception.IF_EXISTS) final Talk talk) {
        retrieveTalkList();
    }
	
	@Named
	@Produces
	public List<Talk> getTalks() {
		return talkList;
	}
}