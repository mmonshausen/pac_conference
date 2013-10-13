package com.prodyna.pac.mmonshausen.conference.rest;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import javax.validation.Validator;
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

import com.prodyna.pac.mmonshausen.conference.model.Speaker;
import com.prodyna.pac.mmonshausen.conference.service.SpeakerService;

/**
 * REST Service for creating, reading, updating and deleting speakers
 * 
 * @author Martin Monshausen, PRODYNA AG
 */
@Path("/speaker")
public class SpeakerRestService {
	@Inject
	private Validator validator;
	
	@Inject
	private SpeakerService speakerService;
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response saveSpeaker(final Speaker speaker) {
		Response.ResponseBuilder builder;
		try {
			validateSpeaker(speaker);
			
			speakerService.saveSpeaker(speaker);
			
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
	public Speaker getSpeakerById(@PathParam("id") final long id) {
		final Speaker speaker = speakerService.getSpeakerById(id);
		
		if (speaker == null) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
		
		return speaker;
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Speaker> listAllSpeakers() {
		final List<Speaker> speakerList = speakerService.listAllSpeakers();
		
		if (speakerList == null || speakerList.isEmpty()) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
		
		return speakerList;
	}
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateSpeaker(final Speaker speaker) {
		Response.ResponseBuilder builder;
		try {
			validateSpeaker(speaker);
			
			speakerService.updateSpeaker(speaker);
			
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
	public void deleteSpeaker(@PathParam("id") final long id) {
		speakerService.deleteSpeaker(id);
	}
	
	private void validateSpeaker(final Speaker speaker) {
		final Set<ConstraintViolation<Speaker>> violations = validator
				.validate(speaker);

		if (!violations.isEmpty()) {
			throw new ConstraintViolationException(
					new HashSet<ConstraintViolation<?>>(violations));
		}
	}
}