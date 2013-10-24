package com.prodyna.pac.mmonshausen.conference.service;

import javax.decorator.Decorator;
import javax.decorator.Delegate;
import javax.enterprise.event.Event;
import javax.inject.Inject;

import com.prodyna.pac.mmonshausen.conference.model.Location;
import com.prodyna.pac.mmonshausen.conference.util.DecoratorHelper;

/**
 * Decorator which intercepts methods and sends notifications to observers and queue messages
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
	
	@Override
	public Location saveLocation(final Location location) {
		event.fire(location);
		
		decoHelper.sendQueueMessage("location [id="+location.getId()+" name="+location.getName()+ "] erstellt");
		
		return locationService.saveLocation(location);
	}

	@Override
	public Location updateLocation(final Location location) {
		event.fire(location);

		decoHelper.sendQueueMessage("location [id="+location.getId()+" name="+location.getName()+ "] upgedated");

		return locationService.updateLocation(location);
	}

	@Override
	public void deleteLocation(final long id) {
		event.fire(new Location());
		
		decoHelper.sendQueueMessage("location [id="+id+ "] geloescht");	
		
		deleteLocation(id);
	}
}
