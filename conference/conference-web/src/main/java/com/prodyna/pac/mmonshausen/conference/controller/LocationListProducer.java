package com.prodyna.pac.mmonshausen.conference.controller;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.event.Reception;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;

import com.prodyna.pac.mmonshausen.conference.model.Location;
import com.prodyna.pac.mmonshausen.conference.service.LocationService;

/**
 * gets and holds list of locations
 * 
 * @author Martin Monshausen, PRODYNA AG
 */
@ApplicationScoped
public class LocationListProducer {
	@Inject
	private LocationService locationService;
	
	private List<Location> locationList;
	
	@PostConstruct
	public void retrieveLocationList() {
		locationList = locationService.listAllLocations();
	}
	
	public void onLocationListChanged(@Observes(notifyObserver = Reception.IF_EXISTS) final Location location) {
        retrieveLocationList();
    }
	
	@Named
	@Produces
	public List<Location> getLocations() {
		return locationList;
	}
}