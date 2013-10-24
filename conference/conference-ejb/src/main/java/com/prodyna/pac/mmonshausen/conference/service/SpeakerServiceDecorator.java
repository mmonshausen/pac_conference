package com.prodyna.pac.mmonshausen.conference.service;

import javax.decorator.Decorator;
import javax.decorator.Delegate;
import javax.enterprise.event.Event;
import javax.inject.Inject;

import com.prodyna.pac.mmonshausen.conference.model.Speaker;
import com.prodyna.pac.mmonshausen.conference.util.DecoratorHelper;

/**
 * Decorator which intercepts methods and sends notifications to observers and queue messages
 * 
 * @author Martin Monshausen, PRODYNA AG
 */
@Decorator
public abstract class SpeakerServiceDecorator implements SpeakerService {
	@Inject @Invaded
	private Event<Speaker> event;

	@Inject
	@Delegate
	private SpeakerService speakerService;

	@Inject
	private DecoratorHelper decoHelper;
	
	@Override
	public Speaker saveSpeaker(final Speaker speaker) {
		event.fire(speaker);
		
		decoHelper.sendQueueMessage("speaker [id="+speaker.getId()+" name="+speaker.getName()+ "] erstellt");
		
		return speakerService.saveSpeaker(speaker);
	}

	@Override
	public Speaker updateSpeaker(final Speaker speaker) {
		event.fire(speaker);
		
		decoHelper.sendQueueMessage("speaker [id="+speaker.getId()+" name="+speaker.getName()+ "] upgedated");
		
		return speakerService.updateSpeaker(speaker);
	}

	@Override
	public void deleteSpeaker(final long id) {
		event.fire(new Speaker());
		
		decoHelper.sendQueueMessage("speaker [id="+id+ "] geloescht");	
		
		deleteSpeaker(id);
	}
}
