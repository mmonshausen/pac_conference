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

	/*
	 * (non-Javadoc)
	 * @see com.prodyna.pac.mmonshausen.conference.service.TalkService#createTalk(com.prodyna.pac.mmonshausen.conference.model.Talk)
	 */
	@Override
	public Talk createTalk(final Talk talk) {
		em.persist(talk);
		return talk;
	}

	/*
	 * (non-Javadoc)
	 * @see com.prodyna.pac.mmonshausen.conference.service.TalkService#getTalkById(long)
	 */
	@Override
	public Talk getTalkById(final long id) {
		final TypedQuery<Talk> query = em.createNamedQuery("selectTalkById",
				Talk.class);
		query.setParameter("talkId", id);
		final List<Talk> resultList = query.getResultList();

		if(resultList.isEmpty()) {
			logger.warning("talk (id="+id+") not found");
			return null;
		} else {
			return resultList.get(0);
		}		
	}

	/*
	 * (non-Javadoc)
	 * @see com.prodyna.pac.mmonshausen.conference.service.TalkService#getConferenceTalksOrderedByDateTime(java.lang.Long)
	 */
	@Override
	public List<Talk> getConferenceTalksOrderedByDateTime(final Long conferenceId) {
		final TypedQuery<Talk> query = em.createNamedQuery("selectConferenceTalksOrderedByDateTime",
				Talk.class);
		query.setParameter("conferenceId", conferenceId);
		final List<Talk> resultList = query.getResultList();

		if(resultList.isEmpty()) {
			logger.warning("no talks for that parameters existing!");
		}

		return resultList;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.prodyna.pac.mmonshausen.conference.service.TalkService#getRoomTalksOrderedByDateTime(java.lang.Long)
	 */
	@Override
	public List<Talk> getRoomTalksOrderedByDateTime(final Long roomId) {
		final TypedQuery<Talk> query = em.createNamedQuery("selectRoomTalksOrderedByDateTime",
				Talk.class);
		query.setParameter("roomId", roomId);
		final List<Talk> resultList = query.getResultList();

		if(resultList.isEmpty()) {
			logger.warning("no talks for that parameters existing!");
		}

		return resultList;
	}

	/*
	 * (non-Javadoc)
	 * @see com.prodyna.pac.mmonshausen.conference.service.TalkService#listAllTalks()
	 */
	@Override
	public List<Talk> listAllTalks() {
		final TypedQuery<Talk> query = em.createNamedQuery("selectAllTalks",
				Talk.class);
		final List<Talk> resultList = query.getResultList();

		if(resultList.isEmpty()) {
			logger.warning("no talks existing!");
		}

		return resultList;
	}

	/*
	 * (non-Javadoc)
	 * @see com.prodyna.pac.mmonshausen.conference.service.TalkService#updateTalk(com.prodyna.pac.mmonshausen.conference.model.Talk)
	 */
	@Override
	public Talk updateTalk(final Talk talk) {
		final Long id = talk.getId();

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

	/*
	 * (non-Javadoc)
	 * @see com.prodyna.pac.mmonshausen.conference.service.TalkService#deleteTalk(long)
	 */
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