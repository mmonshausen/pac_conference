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
	public Location createLocation(final Location location) {
		em.persist(location);
		return location;
	}

	/*
	 * (non-Javadoc)
	 * @see com.prodyna.pac.mmonshausen.conference.service.LocationService#getLocationById(long)
	 */
	@Override
	public Location getLocationById(final long id) {
		final TypedQuery<Location> query = em.createNamedQuery("selectLocationById",
				Location.class);
		query.setParameter("locationId", id);
		final List<Location> resultList = query.getResultList();

		if(resultList.isEmpty()) {
			logger.warning("location (id="+id+") not found");
			return null;
		} else {
			return resultList.get(0);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.prodyna.pac.mmonshausen.conference.service.LocationService#listAllLocations()
	 */
	@Override
	public List<Location> listAllLocations() {
		final TypedQuery<Location> query = em.createNamedQuery("selectAllLocations",
				Location.class);
		final List<Location> resultList = query.getResultList();

		if(resultList.isEmpty()) {
			logger.warning("no locations existing!");
		}

		return resultList;
	}

	/*
	 * (non-Javadoc)
	 * @see com.prodyna.pac.mmonshausen.conference.service.LocationService#updateLocation(com.prodyna.pac.mmonshausen.conference.model.Location)
	 */
	@Override
	public Location updateLocation(final Location location) {
		final Long id = location.getId();
		
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

	/*
	 * (non-Javadoc)
	 * @see com.prodyna.pac.mmonshausen.conference.service.LocationService#deleteLocation(long)
	 */
	@Override
	public void deleteLocation(final long id) {
		final Location location = getLocationById(id);

		if(location != null) {
			em.remove(location);
			logger.info("location (id="+id+") successfully deleted");
		} else {
			logger.warning("location (id="+id+") not found; nothing deleted!");
		}
	}
}