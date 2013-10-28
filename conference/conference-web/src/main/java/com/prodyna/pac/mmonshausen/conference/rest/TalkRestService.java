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

import com.prodyna.pac.mmonshausen.conference.model.Talk;
import com.prodyna.pac.mmonshausen.conference.service.TalkService;
import com.prodyna.pac.mmonshausen.conference.util.InputValidator;

/**
 * REST Service for creating, reading, updating and deleting talks
 * 
 * @author Martin Monshausen, PRODYNA AG
 */
@Path("/talk")
public class TalkRestService {
	@Inject
	private InputValidator inputValidator;

	@Inject
	private TalkService talkService;

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createTalk(final Talk talk) {
		Response.ResponseBuilder builder;
		try {
			inputValidator.validateTalk(talk);

			talkService.createTalk(talk);

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
	@Path("/{id:[0-9][0-9]*}")
	@Produces(MediaType.APPLICATION_JSON)
	public Talk getTalkById(@PathParam("id") final long id) {
		final Talk talk = talkService.getTalkById(id);

		if (talk == null) {
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}
		return talk;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/conference/{id:[0-9][0-9]*}")
	public List<Talk> getTalksByDate(@PathParam("id") final long id) {
		final List<Talk> talkList = talkService.getConferenceTalksOrderedByDateTime(id);

		if (talkList == null || talkList.isEmpty()) {
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}
		return talkList;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Talk> listAllTalks() {
		final List<Talk> talkList = talkService.listAllTalks();

		if (talkList == null || talkList.isEmpty()) {
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}
		return talkList;
	}

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateTalk(final Talk talk) {
		Response.ResponseBuilder builder;
		try {
			inputValidator.validateTalk(talk);

			talkService.updateTalk(talk);

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
	public void deleteTalk(@PathParam("id") final long id) {
		talkService.deleteTalk(id);
	}
}