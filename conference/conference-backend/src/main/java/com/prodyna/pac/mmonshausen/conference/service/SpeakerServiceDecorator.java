package com.prodyna.pac.mmonshausen.conference.service;

import javax.decorator.Decorator;
import javax.decorator.Delegate;
import javax.enterprise.event.Event;
import javax.inject.Inject;

import com.prodyna.pac.mmonshausen.conference.model.Speaker;
import com.prodyna.pac.mmonshausen.conference.util.DecoratorHelper;

/**
 * Decorator which intercepts {@link SpeakerService} methods and sends
 * notifications to observers and queue messages
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
	
	/*
	 * (non-Javadoc)
	 * @see com.prodyna.pac.mmonshausen.conference.service.SpeakerService#createSpeaker(com.prodyna.pac.mmonshausen.conference.model.Speaker)
	 */
	@Override
	public Speaker createSpeaker(final Speaker speaker) {
		final Speaker resultSpeaker = speakerService.createSpeaker(speaker);
		
		event.fire(resultSpeaker);
		
		decoHelper.sendQueueMessage("speaker [id="+speaker.getId()+" name="+speaker.getName()+ "] erstellt");
		
		return resultSpeaker;
	}

	/*
	 * (non-Javadoc)
	 * @see com.prodyna.pac.mmonshausen.conference.service.SpeakerService#updateSpeaker(com.prodyna.pac.mmonshausen.conference.model.Speaker)
	 */
	@Override
	public Speaker updateSpeaker(final Speaker speaker) {
		final Speaker resultSpeaker = speakerService.updateSpeaker(speaker);
		
		event.fire(resultSpeaker);
		
		decoHelper.sendQueueMessage("speaker [id="+speaker.getId()+" name="+speaker.getName()+ "] upgedated");
		
		return resultSpeaker;
	}

	/*
	 * (non-Javadoc)
	 * @see com.prodyna.pac.mmonshausen.conference.service.SpeakerService#deleteSpeaker(long)
	 */
	@Override
	public void deleteSpeaker(final long id) {
		speakerService.deleteSpeaker(id);
		
		event.fire(new Speaker());
		
		decoHelper.sendQueueMessage("speaker [id="+id+ "] geloescht");
	}
}