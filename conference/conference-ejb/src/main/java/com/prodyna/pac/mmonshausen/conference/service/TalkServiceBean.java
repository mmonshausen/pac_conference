package com.prodyna.pac.mmonshausen.conference.service;

import java.util.List;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.prodyna.pac.mmonshausen.conference.model.Talk;
import com.prodyna.pac.mmonshausen.conference.util.MeasuringInterceptor;

/**
 * service bean containing crud operations for talks
 * 
 * @author Martin Monshausen, PRODYNA AG
 */
@Stateless
@Interceptors(MeasuringInterceptor.class)
public class TalkServiceBean implements TalkService {

	@Inject
	private EntityManager em;

	@Inject
	private Logger logger;

	@Override
	public Talk createTalk(final Talk talk) {
		em.persist(talk);
		logger.info("talk successfully persisted");
		return talk;
	}

	@Override
	public Talk getTalkById(final long id) {
		final Talk talk = em.find(Talk.class, id);

		if(talk == null) {
			logger.warning("talk (id="+id+") not found");
		}

		return talk;
	}

	@Override
	public List<Talk> getConferenceTalksOrderedByDateTime(Long conferenceId) {
		final String queryString = "SELECT talk FROM Talk talk where talk.conference.id = :conferenceId order by talk.date, talk.startTime";
		final TypedQuery<Talk> query = em.createQuery(queryString,
				Talk.class);
		query.setParameter("conferenceId", conferenceId);
		final List<Talk> resultList = query.getResultList();

		if(resultList.isEmpty()) {
			logger.warning("no talks for that parameters existing!");
		}

		return resultList;
	}
	
	@Override
	public List<Talk> getRoomTalksOrderedByDateTime(Long roomId) {
		final String queryString = "SELECT talk FROM Talk talk where talk.room.id = :roomId order by talk.date, talk.startTime";
		final TypedQuery<Talk> query = em.createQuery(queryString,
				Talk.class);
		query.setParameter("roomId", roomId);
		final List<Talk> resultList = query.getResultList();

		if(resultList.isEmpty()) {
			logger.warning("no talks for that parameters existing!");
		}

		return resultList;
	}

	@Override
	public List<Talk> listAllTalks() {
		final String queryString = "SELECT talk FROM Talk talk";
		final TypedQuery<Talk> query = em.createQuery(queryString,
				Talk.class);
		final List<Talk> resultList = query.getResultList();

		if(resultList.isEmpty()) {
			logger.warning("no talks existing!");
		}

		return resultList;
	}

	@Override
	public Talk updateTalk(final Talk talk) {
		final Long id = talk.getId();

		//TODO: validation & error handling
		if(id != null) {
			final Talk persistedTalk = getTalkById(id);

			if(persistedTalk != null) {
				persistedTalk.setConference(talk.getConference());
				persistedTalk.setDate(talk.getDate());
				persistedTalk.setDescription(talk.getDescription());
				persistedTalk.setEndTime(talk.getEndTime());
				persistedTalk.setName(talk.getName());
				persistedTalk.setRoom(talk.getRoom());
				persistedTalk.setSpeakers(talk.getSpeakers());
				persistedTalk.setStartTime(talk.getStartTime());

				return em.merge(persistedTalk);
			} else {
				logger.warning("talk (id="+id+") not found; could not update talk!");
			}
		} else {
			logger.warning("talk has no id; perhaps it had not been persisted yet!");
		}

		return null;
	}

	@Override
	public void deleteTalk(final long id) {
		final Talk talk = getTalkById(id);

		if(talk != null) {
			em.remove(talk);
			logger.info("talk successfully deleted");
		} else {
			logger.warning("talk (id="+id+") not found; no talk deleted!");
		}		
	}
}
