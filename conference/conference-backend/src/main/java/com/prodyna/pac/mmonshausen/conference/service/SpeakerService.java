package com.prodyna.pac.mmonshausen.conference.service;

import java.util.List;

import com.prodyna.pac.mmonshausen.conference.model.Speaker;

/**
 * interface specification for SpeakerService<br>
 * SpeakerService is responsible for performing CRUD operations for entity
 * {@link Speaker}
 * 
 * @author Martin Monshausen, PRODYNA AG
 */
public interface SpeakerService {
	
	/**
	 * persists given {@link Speaker} to database
	 * 
	 * @param speaker
	 *            object to persist
	 * @return object persisted (including id)
	 */
	public Speaker createSpeaker(Speaker speaker);
	
	/**
	 * reads {@link Speaker} for database for given id<br>
	 * if nothing found for id warning will be logged
	 * 
	 * @param id
	 *            id of {@link Speaker} to be read from database
	 * @return if found corresponding {@link Speaker} object, otherwise null
	 */
	public Speaker getSpeakerById(long id);
	
	/**
	 * reads all {@link Speaker} objects from database<br>
	 * if there are no {@link Speaker} return empty list
	 * 
	 * @return if found list of {@link Speaker}, otherwise empty list
	 */
	public List<Speaker> listAllSpeakers();

	/**
	 * persists changes on the {@link Speaker} to database
	 * 
	 * @param speaker
	 *            object with changes to be stored to database
	 * @return object with merged changes
	 */
	public Speaker updateSpeaker(Speaker speaker);
	
	/**
	 * removed object with given id from database<br>
	 * if there is no object with given id log warning
	 * 
	 * @param id
	 *            id of {@link Speaker} to be deleted
	 */
	public void deleteSpeaker(long id);
}