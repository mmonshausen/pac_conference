package com.prodyna.pac.mmonshausen.conference.service;

import java.util.List;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.prodyna.pac.mmonshausen.conference.model.Speaker;

/**
 * service bean containing crud operations for speakers
 * 
 * @author Martin Monshausen, PRODYNA AG
 */
@Stateless
public class SpeakerServiceBean implements SpeakerService {

	@Inject
	private EntityManager em;
	
	@Inject
	private Logger logger;
	
	@Override
	public Speaker saveSpeaker(Speaker speaker) {
		//TODO: validation & error handling
		em.persist(speaker);
		logger.info("speaker successfully persisted");
		return speaker;
	}

	@Override
	public Speaker getSpeakerById(long id) {
		Speaker speaker = em.find(Speaker.class, id);
		
		if(speaker == null) {
			logger.warning("speaker (id="+id+") not found!");
		}
		
		return speaker;
	}

	@Override
	public List<Speaker> listAllSpeakers() {
		final String queryString = "SELECT speaker FROM Speaker speaker";
		final TypedQuery<Speaker> query = em.createQuery(queryString,
				Speaker.class);
		final List<Speaker> resultList = query.getResultList();

		if(resultList.isEmpty()) {
			logger.warning("no speakers existing!");
		}

		return resultList;
	}

	@Override
	public Speaker updateSpeaker(Speaker speaker) {
		final Long id = speaker.getId();
		
		//TODO: validation & error handling
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

	@Override
	public void deleteSpeaker(long id) {
		Speaker speaker = getSpeakerById(id);
		
		if(speaker != null) {
			em.remove(speaker);
			logger.info("speaker successfully deleted");
		} else {
			logger.warning("speaker (id="+id+") not found; no speaker deleted!");
		}
	}
}