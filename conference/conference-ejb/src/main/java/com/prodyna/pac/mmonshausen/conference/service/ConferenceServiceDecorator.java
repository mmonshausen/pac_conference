package com.prodyna.pac.mmonshausen.conference.service;

import javax.decorator.Decorator;
import javax.decorator.Delegate;
import javax.enterprise.event.Event;
import javax.inject.Inject;

import com.prodyna.pac.mmonshausen.conference.model.Conference;
import com.prodyna.pac.mmonshausen.conference.util.DecoratorHelper;

/**
 * Decorator which intercepts methods and sends notifications to observers and queue messages
 * 
 * @author Martin Monshausen, PRODYNA AG
 */
@Decorator
public abstract class ConferenceServiceDecorator implements ConferenceService {
	
	@Inject @Invaded
	private Event<Conference> event;

	@Inject
	@Delegate
	private ConferenceService conferenceService;
	
	@Inject
	private DecoratorHelper decoHelper;

	@Override
	public Conference saveConference(final Conference conference) {
		event.fire(conference);
		
		decoHelper.sendQueueMessage("conference [id="+conference.getId()+" name="+conference.getName()+ "] erstellt");
		
		return conferenceService.saveConference(conference);
	}

	@Override
	public Conference updateConference(final Conference conference) {
		event.fire(conference);
		
		decoHelper.sendQueueMessage("conference [id="+conference.getId()+" name="+conference.getName()+ "] upgedated");
		
		return conferenceService.updateConference(conference);
	}

	@Override
	public void deleteConference(final long id) {
		event.fire(new Conference());
		
		decoHelper.sendQueueMessage("conference [id="+id+ "] geloescht");	
		
		deleteConference(id);
	}
}