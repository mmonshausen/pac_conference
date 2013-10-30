package com.prodyna.pac.mmonshausen.conference.rest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.prodyna.pac.mmonshausen.conference.model.Location;
import com.prodyna.pac.mmonshausen.conference.service.LocationService;
import com.prodyna.pac.mmonshausen.conference.util.InputValidator;

/**
 * REST Service for creating, reading, updating and deleting locations
 * 
 * @author Martin Monshausen, PRODYNA AG
 */
@Path("/location")
public class LocationRestService {
	@Inject
	private InputValidator inputValidator;
	
	@Inject
	private LocationService locationService;
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response saveLocation(final Location location) {
		Response.ResponseBuilder builder;
		try {
			inputValidator.validateLocation(location);
			
			locationService.createLocation(location);
			
			builder = Response.ok();
		} catch (final ConstraintViolationException e) {
			builder = RESTHelper.createViolationResponse(e.getConstraintViolations());
		} catch (final ValidationException e) {
			final Map<String, String> responseObj = new HashMap<String, String>();
            responseObj.put("message", e.getMessage());
            builder = Response.status(Response.Status.CONFLICT).entity(responseObj);
		}
		return builder.build();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{id:[0-9][0-9]*}")
	public Location getLocationById(@PathParam("id") final long id){
		final Location location = locationService.getLocationById(id);
		
		if (location == null) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }		
		return location;
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Location> listAllLocations() {
		final List<Location> locationList = locationService.listAllLocations();
		
		if (locationList == null || locationList.isEmpty()) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
		return locationList;
	}
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateLocation(final Location location) {
		Response.ResponseBuilder builder;
		try {
			inputValidator.validateLocation(location);
			
			locationService.updateLocation(location);
			
			builder = Response.ok();
		} catch (final ConstraintViolationException e) {
			builder = RESTHelper.createViolationResponse(e.getConstraintViolations());
		} catch (final ValidationException e) {
			final Map<String, String> responseObj = new HashMap<String, String>();
            responseObj.put("message", e.getMessage());
            builder = Response.status(Response.Status.CONFLICT).entity(responseObj);
		}
		return builder.build();
	}
	
	@DELETE
	@Path("{id}")
	public void deleteLocation(@PathParam("id") final long id) {
		locationService.deleteLocation(id);
	}
}