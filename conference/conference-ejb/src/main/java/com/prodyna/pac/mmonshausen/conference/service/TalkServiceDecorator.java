package com.prodyna.pac.mmonshausen.conference.service;

import javax.decorator.Decorator;
import javax.decorator.Delegate;
import javax.enterprise.event.Event;
import javax.inject.Inject;

import com.prodyna.pac.mmonshausen.conference.model.Talk;
import com.prodyna.pac.mmonshausen.conference.util.DecoratorHelper;

/**
 * Decorator which intercepts methods and sends notifications to observers and queue messages
 * 
 * @author Martin Monshausen, PRODYNA AG
 */
@Decorator
public abstract class TalkServiceDecorator implements TalkService {
	@Inject @Invaded
	private Event<Talk> event;

	@Inject
	@Delegate
	private TalkService talkService;
	
	private DecoratorHelper decoHelper;

	@Override
	public Talk createTalk(final Talk talk) {
		event.fire(talk);
		
		decoHelper.sendQueueMessage("talk ["+talk.getId()+" "+talk.getName()+ "] erstellt");
		
		return talkService.createTalk(talk);
	}

	@Override
	public Talk updateTalk(final Talk talk) {
		event.fire(talk);
		
		decoHelper.sendQueueMessage("talk ["+talk.getId()+" "+talk.getName()+ "] upgedated");
		
		return talkService.updateTalk(talk);
	}


	@Override
	public void deleteTalk(final long id) {
		event.fire(new Talk());
		
		decoHelper.sendQueueMessage("talk ["+id+" ] geloescht");	
		
		deleteTalk(id);
	}	
}
