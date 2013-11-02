package com.prodyna.pac.mmonshausen.conference.service;

import javax.decorator.Decorator;
import javax.decorator.Delegate;
import javax.enterprise.event.Event;
import javax.inject.Inject;

import com.prodyna.pac.mmonshausen.conference.model.Conference;
import com.prodyna.pac.mmonshausen.conference.util.DecoratorHelper;

/**
 * Decorator which intercepts {@link ConferenceService} methods and sends
 * notifications to observers and queue messages
 * 
 * @author Martin Monshausen, PRODYNA AG
 */
@Decorator
public abstract class ConferenceServiceDecorator implements ConferenceService {

	@Inject
	@Invaded
	private Event<Conference> event;

	@Inject
	@Delegate
	private ConferenceService conferenceService;

	@Inject
	private DecoratorHelper decoHelper;

	/* 
	 * (non-Javadoc)
	 * @see com.prodyna.pac.mmonshausen.conference.service.ConferenceService#saveConference(com.prodyna.pac.mmonshausen.conference.model.Conference)
	 */
	@Override
	public Conference createConference(final Conference conference) {
		final Conference resultConference = conferenceService
				.createConference(conference);

		event.fire(resultConference);

		decoHelper.sendQueueMessage("conference [id=" + conference.getId()
				+ " name=" + conference.getName() + "] erstellt");

		return resultConference;
	}

	/* 
	 * (non-Javadoc)
	 * @see com.prodyna.pac.mmonshausen.conference.service.ConferenceService#updateConference(com.prodyna.pac.mmonshausen.conference.model.Conference)
	 */
	@Override
	public Conference updateConference(final Conference conference) {
		final Conference resultConference = conferenceService
				.updateConference(conference);

		event.fire(resultConference);

		decoHelper.sendQueueMessage("conference [id=" + conference.getId()
				+ " name=" + conference.getName() + "] upgedated");

		return resultConference;
	}

	/*
	 * (non-Javadoc)
	 * @see com.prodyna.pac.mmonshausen.conference.service.ConferenceService#deleteConference(long)
	 */
	@Override
	public void deleteConference(final long id) {
		conferenceService.deleteConference(id);

		event.fire(new Conference());

		decoHelper.sendQueueMessage("conference [id=" + id + "] geloescht");
	}
}