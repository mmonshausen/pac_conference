package com.prodyna.pac.mmonshausen.conference.service;

import java.util.List;

import com.prodyna.pac.mmonshausen.conference.model.Location;

/**
 * 
 * 
 * @author Martin Monshausen, PRODYNA AG
 */
public interface LocationService {
	public Location saveLocation(Location location);
	
	public Location getLocationById(long id);
	public List<Location> listAllLocations();
	
	public Location updateLocation(Location location);
	
	public void deleteLocation(long id);
}
