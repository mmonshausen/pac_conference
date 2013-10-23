package com.prodyna.pac.mmonshausen.conference.service;

import java.util.List;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.prodyna.pac.mmonshausen.conference.model.Location;
import com.prodyna.pac.mmonshausen.conference.util.MeasuringInterceptor;

/**
 * service bean containing crud operations for locations 
 * 
 * @author Martin Monshausen, PRODYNA AG
 */
@Stateless
@Interceptors(MeasuringInterceptor.class)
public class LocationServiceBean implements LocationService {
	
	@Inject
	private EntityManager em;
	
	@Inject
	private Logger logger;

	@Override
	public Location saveLocation(final Location location) {
		em.persist(location);
		logger.info("location successfully persisted");
		return location;
	}

	@Override
	public Location getLocationById(final long id) {
		final Location location = em.find(Location.class, id);
		
		if(location == null) {
			logger.warning("location (id="+id+") not found");
		}
		
		return location;
	}

	@Override
	public List<Location> listAllLocations() {
		final String queryString = "SELECT location FROM Location location";
		final TypedQuery<Location> query = em.createQuery(queryString,
				Location.class);
		final List<Location> resultList = query.getResultList();

		if(resultList.isEmpty()) {
			logger.warning("no locations existing!");
		}

		return resultList;
	}

	@Override
	public Location updateLocation(final Location location) {
		Long id = location.getId();
		
		//TODO: validation & error handling
		if(id != null) {
			final Location persistedLocation = getLocationById(id);

			if(persistedLocation != null) {
				persistedLocation.setName(location.getName());
				persistedLocation.setStreet(location.getStreet());
				persistedLocation.setZipCode(location.getZipCode());
				persistedLocation.setCity(location.getCity());
				persistedLocation.setCountry(location.getCountry());
				
				return em.merge(persistedLocation);
			} else {
				logger.warning("location (id="+id+") not found; could not update location!");
			}
		} else {
			logger.warning("location has no id; perhaps it had not been persisted yet!");
		}

		return null;
	}

	@Override
	public void deleteLocation(final long id) {
		final Location location = getLocationById(id);

		if(location != null) {
			em.remove(location);
			logger.info("location (id="+id+") successfully deleted");
		} else {
			//TODO: error handling
			logger.warning("location (id="+id+") not found; nothing deleted!");
		}
	}
}