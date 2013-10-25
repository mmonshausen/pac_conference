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

import com.prodyna.pac.mmonshausen.conference.model.Room;
import com.prodyna.pac.mmonshausen.conference.service.RoomService;
import com.prodyna.pac.mmonshausen.conference.util.InputValidator;

/**
 * REST Service for creating, reading, updating and deleting rooms
 * 
 * @author Martin Monshausen, PRODYNA AG
 */
@Path("/room")
public class RoomRestService {
	@Inject
	private InputValidator inputValidator;
	
	@Inject
	private RoomService roomService;
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createRoom(final Room room) {
		Response.ResponseBuilder builder;
		try {
			inputValidator.validateRoom(room);
			
			roomService.createRoom(room);
			
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
	public Room getRoomById(@PathParam("id") final long id) {
		final Room room = roomService.getRoomById(id);
		
		if (room == null) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
		
		return room;
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Room> listAllRooms() {
		final List<Room> roomList = roomService.listAllRooms();
		
		if (roomList == null || roomList.isEmpty()) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
		
		return roomList;
	}
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateRoom(final Room room) {
		Response.ResponseBuilder builder;
		try {
			inputValidator.validateRoom(room);
			
			roomService.updateRoom(room);
			
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
	public void deleteRoom(@PathParam("id") final long id) {
		roomService.deleteRoom(id);
	}
}