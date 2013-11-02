package com.prodyna.pac.mmonshausen.conference.service;

import java.util.List;

import com.prodyna.pac.mmonshausen.conference.model.Location;

/**
 * interface specification for LocationService<br>
 * LocationService is responsible for performing CRUD operations for entity
 * {@link Location}
 * 
 * @author Martin Monshausen, PRODYNA AG
 */
public interface LocationService {
	
	/**
	 * persists given {@link Location} to database
	 * 
	 * @param location
	 *            object to persist
	 * @return object persisted (including id)
	 */
	public Location createLocation(Location location);
	
	/**
	 * reads {@link Location} for database for given id<br>
	 * if nothing found for id warning will be logged
	 * 
	 * @param id
	 *            id of {@link Location} to be read from database
	 * @return if found corresponding {@link Location} object, otherwise null
	 */
	public Location getLocationById(long id);
	
	/**
	 * reads all {@link Location} objects from database<br>
	 * if there are no {@link Location} return empty list
	 * 
	 * @return if found list of {@link Location}, otherwise empty list
	 */
	public List<Location> listAllLocations();
	
	/**
	 * persists changes on the {@link Location} to database
	 * 
	 * @param location
	 *            object with changes to be stored to database
	 * @return object with merged changes
	 */
	public Location updateLocation(Location location);
	
	/**
	 * removed object with given id from database<br>
	 * if there is no object with given id log warning
	 * 
	 * @param id
	 *            id of {@link Location} to be deleted
	 */
	public void deleteLocation(long id);
}