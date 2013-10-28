package com.prodyna.pac.mmonshausen.conference.service;

import javax.decorator.Decorator;
import javax.decorator.Delegate;
import javax.enterprise.event.Event;
import javax.inject.Inject;

import com.prodyna.pac.mmonshausen.conference.model.Talk;
import com.prodyna.pac.mmonshausen.conference.util.DecoratorHelper;

/**
 * Decorator which intercepts {@link TalkService} methods and sends
 * notifications to observers and queue messages
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

	/*
	 * (non-Javadoc)
	 * @see com.prodyna.pac.mmonshausen.conference.service.TalkService#createTalk(com.prodyna.pac.mmonshausen.conference.model.Talk)
	 */
	@Override
	public Talk createTalk(final Talk talk) {
		final Talk resultTalk = talkService.createTalk(talk);
		
		event.fire(resultTalk);
		
		decoHelper.sendQueueMessage("talk ["+talk.getId()+" "+talk.getName()+ "] erstellt");
		
		return resultTalk;
	}

	/*
	 * (non-Javadoc)
	 * @see com.prodyna.pac.mmonshausen.conference.service.TalkService#updateTalk(com.prodyna.pac.mmonshausen.conference.model.Talk)
	 */
	@Override
	public Talk updateTalk(final Talk talk) {
		final Talk resultTalk = talkService.updateTalk(talk);
		
		event.fire(resultTalk);
		
		decoHelper.sendQueueMessage("talk ["+talk.getId()+" "+talk.getName()+ "] upgedated");
		
		return resultTalk;
	}

	/*
	 * (non-Javadoc)
	 * @see com.prodyna.pac.mmonshausen.conference.service.TalkService#deleteTalk(long)
	 */
	@Override
	public void deleteTalk(final long id) {
		talkService.deleteTalk(id);
		
		event.fire(new Talk());
		
		decoHelper.sendQueueMessage("talk ["+id+" ] geloescht");
	}	
}