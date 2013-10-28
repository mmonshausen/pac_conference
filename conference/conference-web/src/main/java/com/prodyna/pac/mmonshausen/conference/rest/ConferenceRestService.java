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

import com.prodyna.pac.mmonshausen.conference.model.Conference;
import com.prodyna.pac.mmonshausen.conference.service.ConferenceService;
import com.prodyna.pac.mmonshausen.conference.util.InputValidator;

/**
 * REST Service for creating, reading, updating and deleting conferences
 * 
 * @author Martin Monshausen, PRODYNA AG
 */
@Path("/conference")
public class ConferenceRestService {
	@Inject
	private InputValidator inputValidator;
	
	@Inject
	private ConferenceService conferenceService;
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response saveConference(final Conference conference) {
		Response.ResponseBuilder builder;
		try {
			inputValidator.validateConference(conference);
			
			conferenceService.createConference(conference);
			
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
	public Conference getConferenceById(@PathParam("id") final long id) {
		final Conference conference = conferenceService.getConferenceById(id);
		
		if (conference == null) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
		return conference;
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Conference> listAllConferences() {
		final List<Conference> conferenceList = conferenceService.listAllConferences();
		
		if (conferenceList == null || conferenceList.isEmpty()) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
		return conferenceList;
	}
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateConference(final Conference conference) {
		Response.ResponseBuilder builder;
		try {
			inputValidator.validateConference(conference);
			
			conferenceService.updateConference(conference);
			
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
	public void deleteConference(@PathParam("id") final long id) {
		conferenceService.deleteConference(id);
	}
}