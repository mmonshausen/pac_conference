package com.prodyna.pac.mmonshausen.conference.service;

import java.util.List;

import com.prodyna.pac.mmonshausen.conference.model.Room;
import com.prodyna.pac.mmonshausen.conference.model.Talk;

/**
 * interface specification for TalkService<br>
 * TalkService is responsible for performing CRUD operations for entity
 * {@link Talk}
 * 
 * @author Martin Monshausen, PRODYNA AG
 */
public interface TalkService {
	
	/**
	 * persists given {@link Talk} to database
	 * 
	 * @param talk
	 *            object to persist
	 * @return object persisted (including id)
	 */
	public Talk createTalk(Talk talk);
	
	/**
	 * reads {@link Talk} for database for given id<br>
	 * if nothing found for id warning will be logged
	 * 
	 * @param id
	 *            id of {@link Talk} to be read from database
	 * @return if found corresponding {@link Talk} object, otherwise null
	 */
	public Talk getTalkById(long id);
	
	/**
	 * reads all {@link Talk} objects from database for given conferences id<br>
	 * results are ordered by date and startTime<br>
	 * if there are no {@link Talk} for that conferences id, return empty list
	 * 
	 * @return if found, list of {@link Talk}, otherwise empty list
	 */
	public List<Talk> getConferenceTalksOrderedByDateTime(Long conferenceId);
	
	/**
	 * reads all {@link Talk} objects from database for given rooms id<br>
	 * results are ordered by date and startTime<br>
	 * if there are no {@link Talk} for that rooms id, return empty list
	 * 
	 * @return if found list of {@link Room}, otherwise empty list
	 */
	public List<Talk> getRoomTalksOrderedByDateTime(Long roomId);
	
	/**
	 * reads all {@link Talk} objects from database<br>
	 * if there are no {@link Talk} return empty list
	 * 
	 * @return if found list of {@link Talk}, otherwise empty list
	 */
	public List<Talk> listAllTalks();

	/**
	 * persists changes on the {@link Talk} to database
	 * 
	 * @param talk
	 *            object with changes to be stored to database
	 * @return object with merged changes
	 */
	public Talk updateTalk(Talk talk);
	
	/**
	 * removed object with given id from database<br>
	 * if there is no object with given id log warning
	 * 
	 * @param id
	 *            id of {@link Talk} to be deleted
	 */
	public void deleteTalk(long id);
}