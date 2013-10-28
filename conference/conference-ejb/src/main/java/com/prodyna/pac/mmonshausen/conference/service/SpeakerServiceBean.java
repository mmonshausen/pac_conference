package com.prodyna.pac.mmonshausen.conference.service;

import java.util.List;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.prodyna.pac.mmonshausen.conference.model.Speaker;
import com.prodyna.pac.mmonshausen.conference.util.MeasuringInterceptor;

/**
 * service bean containing crud operations for speakers
 * 
 * @author Martin Monshausen, PRODYNA AG
 */
@Stateless
@Interceptors(MeasuringInterceptor.class)
public class SpeakerServiceBean implements SpeakerService {

	@Inject
	private EntityManager em;
	
	@Inject
	private Logger logger;
	
	/*
	 * (non-Javadoc)
	 * @see com.prodyna.pac.mmonshausen.conference.service.SpeakerService#createSpeaker(com.prodyna.pac.mmonshausen.conference.model.Speaker)
	 */
	@Override
	public Speaker createSpeaker(final Speaker speaker) {
		em.persist(speaker);
		return speaker;
	}

	/*
	 * (non-Javadoc)
	 * @see com.prodyna.pac.mmonshausen.conference.service.SpeakerService#getSpeakerById(long)
	 */
	@Override
	public Speaker getSpeakerById(final long id) {
		final String queryString = "SELECT speaker FROM Speaker speaker left join fetch speaker.talks WHERE speaker.id = :speakerId";
		final TypedQuery<Speaker> query = em.createQuery(queryString,
				Speaker.class);
		query.setParameter("speakerId", id);
		final List<Speaker> resultList = query.getResultList();

		if(resultList.isEmpty()) {
			logger.warning("speaker (id="+id+") not found!");
			return null;
		} else {
			return resultList.get(0);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.prodyna.pac.mmonshausen.conference.service.SpeakerService#listAllSpeakers()
	 */
	@Override
	public List<Speaker> listAllSpeakers() {
		final String queryString = "SELECT speaker FROM Speaker speaker left join fetch speaker.talks";
		final TypedQuery<Speaker> query = em.createQuery(queryString,
				Speaker.class);
		final List<Speaker> resultList = query.getResultList();

		if(resultList.isEmpty()) {
			logger.warning("no speakers existing!");
		}

		return resultList;
	}

	/*
	 * (non-Javadoc)
	 * @see com.prodyna.pac.mmonshausen.conference.service.SpeakerService#updateSpeaker(com.prodyna.pac.mmonshausen.conference.model.Speaker)
	 */
	@Override
	public Speaker updateSpeaker(final Speaker speaker) {
		final Long id = speaker.getId();
		
		if(id != null) {
			final Speaker persistedSpeaker = getSpeakerById(id);
			
			if(persistedSpeaker != null) {
				persistedSpeaker.setDescription(speaker.getDescription());
				persistedSpeaker.setName(speaker.getName());
				persistedSpeaker.setTalks(speaker.getTalks());
				
				return em.merge(persistedSpeaker);
			} else {
				logger.warning("speaker (id="+id+") not found; could not update speaker!");
			}
		} else {
			logger.warning("speaker has no id; perhaps it had not been persisted yet!");
		}
		
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see com.prodyna.pac.mmonshausen.conference.service.SpeakerService#deleteSpeaker(long)
	 */
	@Override
	public void deleteSpeaker(final long id) {
		final Speaker speaker = getSpeakerById(id);
		
		if(speaker != null) {
			em.remove(speaker);
			logger.info("speaker successfully deleted");
		} else {
			logger.warning("speaker (id="+id+") not found; no speaker deleted!");
		}
	}
}