package com.prodyna.pac.mmonshausen.conference.service;

import javax.decorator.Decorator;
import javax.decorator.Delegate;
import javax.enterprise.event.Event;
import javax.inject.Inject;

import com.prodyna.pac.mmonshausen.conference.model.Location;
import com.prodyna.pac.mmonshausen.conference.util.DecoratorHelper;

/**
 * Decorator which intercepts {@link LocationService} methods and sends
 * notifications to observers and queue messages
 * 
 * @author Martin Monshausen, PRODYNA AG
 */
@Decorator
public abstract class LocationServiceDecorator implements LocationService {
	@Inject @Invaded
	private Event<Location> event;

	@Inject
	@Delegate
	private LocationService locationService;

	@Inject
	DecoratorHelper decoHelper;
	
	/*
	 * (non-Javadoc)
	 * @see com.prodyna.pac.mmonshausen.conference.service.LocationService#saveLocation(com.prodyna.pac.mmonshausen.conference.model.Location)
	 */
	@Override
	public Location createLocation(final Location location) {
		final Location resultLocation = locationService.createLocation(location);
		
		event.fire(resultLocation);
		
		decoHelper.sendQueueMessage("location [id="+location.getId()+" name="+location.getName()+ "] erstellt");
		
		return resultLocation;
	}

	/*
	 * (non-Javadoc)
	 * @see com.prodyna.pac.mmonshausen.conference.service.LocationService#updateLocation(com.prodyna.pac.mmonshausen.conference.model.Location)
	 */
	@Override
	public Location updateLocation(final Location location) {
		final Location resultLocation = locationService.updateLocation(location);
		
		event.fire(resultLocation);

		decoHelper.sendQueueMessage("location [id="+location.getId()+" name="+location.getName()+ "] upgedated");

		return resultLocation;
	}

	/*
	 * (non-Javadoc)
	 * @see com.prodyna.pac.mmonshausen.conference.service.LocationService#deleteLocation(long)
	 */
	@Override
	public void deleteLocation(final long id) {
		locationService.deleteLocation(id);
		
		event.fire(new Location());
		
		decoHelper.sendQueueMessage("location [id="+id+ "] geloescht");	
	}
}