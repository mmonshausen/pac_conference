package com.prodyna.pac.mmonshausen.conference.controller;

import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;

import com.prodyna.pac.mmonshausen.conference.model.Location;
import com.prodyna.pac.mmonshausen.conference.service.LocationService;

/**
 * JSF-Controller for locations
 * 
 * @author Martin Monshausen, PRODYNA AG
 */
@Model
public class LocationController {
	
	@Inject
	private LocationService locationService;
	
	private Location location;
	private Long id;
	
	public void createLocation() {
		locationService.saveLocation(location);
	}
	
	@Produces
	@Named
	public Location getLocation() {
		if(location != null) {
			return location;
		} else {
			return new Location();
		}
	}
	
	public void saveChanges() {
		locationService.updateLocation(location);
	}
	
	public void deleteChanges() {
		locationService.deleteLocation(id);
	}
	
	private void initialize() {
		if((id != null) && (id !=0)) {
			location = locationService.getLocationById(id);			
		}
	}
		
	public void setId(final Long id) {
		this.id = id;
		initialize();
	}
}