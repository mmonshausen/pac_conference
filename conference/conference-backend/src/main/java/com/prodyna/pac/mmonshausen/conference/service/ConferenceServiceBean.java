package com.prodyna.pac.mmonshausen.conference.service;

import java.util.List;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.prodyna.pac.mmonshausen.conference.model.Conference;
import com.prodyna.pac.mmonshausen.conference.util.MeasuringInterceptor;

/**
 * service bean containing crud operations for conferences
 * 
 * @author Martin Monshausen, PRODYNA AG
 */
@Stateless
@Interceptors(MeasuringInterceptor.class)
public class ConferenceServiceBean implements ConferenceService {

	@Inject
	private EntityManager em;

	@Inject
	private Logger logger;

	/*
	 * (non-Javadoc)
	 * @see com.prodyna.pac.mmonshausen.conference.service.ConferenceService#saveConference(com.prodyna.pac.mmonshausen.conference.model.Conference)
	 */
	@Override
	public Conference createConference(final Conference conference) {
		em.persist(conference);
		return conference;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.prodyna.pac.mmonshausen.conference.service.ConferenceService#getConferenceById(long)
	 */
	@Override
	public Conference getConferenceById(final long id) {
		final String queryString = "SELECT conference FROM Conference conference left join fetch conference.talks WHERE conference.id = :conferenceId";
		final TypedQuery<Conference> query = em.createNamedQuery(queryString,
				Conference.class);
		query.setParameter("conferenceId", id);
		final List<Conference> resultList = query.getResultList();

		if(resultList.isEmpty()) {
			logger.warning("conference (id=" +id+ ") not found!");
			return null;
		} else {
			return resultList.get(0);
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.prodyna.pac.mmonshausen.conference.service.ConferenceService#listAllConferences()
	 */
	@Override
	public List<Conference> listAllConferences() {
		final String queryString = "SELECT conference FROM Conference conference left join fetch conference.talks";
		final TypedQuery<Conference> query = em.createNamedQuery(queryString,
				Conference.class);
		final List<Conference> resultList = query.getResultList();

		if(resultList.isEmpty()) {
			logger.warning("no conferences existing!");
		}

		return resultList;
	}

	/*
	 * (non-Javadoc)
	 * @see com.prodyna.pac.mmonshausen.conference.service.ConferenceService#updateConference(com.prodyna.pac.mmonshausen.conference.model.Conference)
	 */
	@Override
	public Conference updateConference(final Conference conference) {
		final Long id = conference.getId();

		if (id != null) {
			final Conference persistedConference = getConferenceById(id);

			if(persistedConference != null) {
				persistedConference.setDescription(conference.getDescription());
				persistedConference.setEndDate(conference.getEndDate());
				persistedConference.setName(conference.getName());
				persistedConference.setStartDate(conference.getStartDate());

				return em.merge(persistedConference);
			} else {
				logger.warning("conference (id="+id+") not found no update performed!");
			}
		} else {
			logger.warning("conference does not have an id; perhaps it had not been persisted yet!"); 
		}
		return null;
	}

	/* 
	 * (non-Javadoc)
	 * @see com.prodyna.pac.mmonshausen.conference.service.ConferenceService#deleteConference(long)
	 */
	@Override
	public void deleteConference(final long id) {
		final Conference conference = getConferenceById(id);

		if (conference != null) {
			em.remove(conference);
			logger.info("conference (id="+id+") successfully deleted");
		} else {
			logger.warning("conference (id="+id+") not found. conference could not be deleted.");
		}
	}
}