package com.prodyna.pac.mmonshausen.conference.service;

import java.util.List;

import com.prodyna.pac.mmonshausen.conference.model.Conference;

/**
 * interface specification for ConferenceService<br>
 * ConferenceService is responsible for performing CRUD operations for entity
 * {@link Conference}
 * 
 * @author Martin Monshausen, PRODYNA AG
 */
public interface ConferenceService {

	/**
	 * persists given {@link Conference} to database
	 * 
	 * @param conference
	 *            object to persist
	 * @return object persisted (including id)
	 */
	public Conference createConference(Conference conference);

	/**
	 * reads {@link Conference} for database for given id<br>
	 * if nothing found for id warning will be logged
	 * 
	 * @param id
	 *            id of {@link Conference} to be read from database
	 * @return if found corresponding {@link Conference} object, otherwise null
	 */
	public Conference getConferenceById(long id);

	/**
	 * reads all {@link Conference} objects from database<br>
	 * if there are no {@link Conference} return empty list
	 * 
	 * @return if found list of {@link Conference}, otherwise empty list
	 */
	public List<Conference> listAllConferences();

	/**
	 * persists changes on the {@link Conference} to database
	 * 
	 * @param conference
	 *            object with changes to be stored to database
	 * @return object with merged changes
	 */
	public Conference updateConference(Conference conference);

	/**
	 * removed object with given id from database<br>
	 * if there is no object with given id log warning
	 * 
	 * @param id
	 *            id of {@link Conference} to be deleted
	 */
	public void deleteConference(long id);
}
