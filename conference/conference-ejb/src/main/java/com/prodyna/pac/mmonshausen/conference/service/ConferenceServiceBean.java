package com.prodyna.pac.mmonshausen.conference.service;

import java.util.List;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.prodyna.pac.mmonshausen.conference.model.Conference;

/**
 * service bean containing crud operations for conferences
 * 
 * @author Martin Monshausen, PRODYNA AG
 */
@Stateless
public class ConferenceServiceBean implements ConferenceService {

	@Inject
	private EntityManager em;

	@Inject
	private Logger logger;

	@Override
	public Conference saveConference(final Conference conference) {
		//TODO: validation & error handling
		em.persist(conference);
		logger.info("conference success");
		return conference;
	}

	@Override
	public Conference getConferenceById(final long id) {
		final Conference conference = em.find(Conference.class, id);

		if(conference == null) {
			//TODO: error handling
			logger.warning("conference (id=" +id+ ") not found!");
		}

		return conference;
	}
	
	@Override
	public List<Conference> listAllConferences() {
		final String queryString = "SELECT conference FROM Conference conference";
		final TypedQuery<Conference> query = em.createQuery(queryString,
				Conference.class);
		final List<Conference> resultList = query.getResultList();

		if(resultList.isEmpty()) {
			logger.warning("no conferences existing!");
		}

		return resultList;
	}

	@Override
	public Conference updateConference(final Conference conference) {
		final Long id = conference.getId();

		//TODO: validation & error handling
		if (id != null) {
			final Conference persistedConference = getConferenceById(id);

			if(persistedConference != null) {
				persistedConference.setDescription(conference.getDescription());
				persistedConference.setEndDate(conference.getEndDate());
				persistedConference.setName(conference.getName());
				persistedConference.setStartDate(conference.getStartDate());
				persistedConference.setTalks(conference.getTalks());

				return em.merge(persistedConference);
			} else {
				logger.warning("conference (id="+id+") not found no update performed!");
			}
		} else {
			logger.warning("conference does not have an id; perhaps it had not been persisted yet!"); 
		}
		return null;
	}

	@Override
	public void deleteConference(final long id) {
		final Conference conference = getConferenceById(id);

		if (conference != null) {
			em.remove(conference);
			logger.info("conference (id="+id+") successfully deleted");
		} else {
			//TODO: error handling
			logger.warning("conference (id="+id+") not found. conference could not be deleted.");
		}
	}
}