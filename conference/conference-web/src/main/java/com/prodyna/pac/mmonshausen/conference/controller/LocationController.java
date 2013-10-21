package com.prodyna.pac.mmonshausen.conference.controller;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import com.prodyna.pac.mmonshausen.conference.model.Location;
import com.prodyna.pac.mmonshausen.conference.model.Room;
import com.prodyna.pac.mmonshausen.conference.service.LocationService;
import com.prodyna.pac.mmonshausen.conference.service.RoomService;

/**
 * JSF-Controller for locations
 * 
 * @author Martin Monshausen, PRODYNA AG
 */
@Model
public class LocationController {
	@Inject
	private LocationService locationService;
	
	@Inject
	private RoomService roomService;
	
	@Inject
	private FacesContext facesContext;
	
	private Location location;
	private Long id;
	private String mode;
	
	@Produces
	@Named
	public Location getLocation() {
		if(location != null) {
			return location;
		} else {
			return new Location();
		}
	}
	
	public List<Room> getRooms() {
		return roomService.getRoomsForLocation(id);
	}
	
	public void createLocation() {
		try {
			System.out.println("create");
			locationService.saveLocation(location);
            facesContext.addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Registered!", "Registration successful"));
        } catch (Exception e) {
            String errorMessage = getRootErrorMessage(e);
            FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR, errorMessage, "Registration Unsuccessful");
            facesContext.addMessage(null, m);
        }	
	}
	
	private String getRootErrorMessage(Exception e) {
        // Default to general error message that registration failed.
        String errorMessage = "Registration failed. See server log for more information";
        if (e == null) {
            // This shouldn't happen, but return the default messages
            return errorMessage;
        }

        // Start with the exception and recurse to find the root cause
        Throwable t = e;
        while (t != null) {
            // Get the message from the Throwable class instance
            errorMessage = t.getLocalizedMessage();
            t = t.getCause();
        }
        // This is the root cause message
        return errorMessage;
    }
	
	public void saveChanges() {
		locationService.updateLocation(location);
	}
	
	public void deleteLocation(long id) {
		locationService.deleteLocation(id);
	}
	
	@PostConstruct
	public void initialize() {
		if((id != null) && (id !=0)) {
			location = locationService.getLocationById(id);			
		} else {
			location = new Location();
		}
	}
		
	public void setId(final Long id) {
		this.id = id;
		initialize();
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public Long getId() {
		return id;
	}
}