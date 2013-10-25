package com.prodyna.pac.mmonshausen.conference.controller;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;

import com.prodyna.pac.mmonshausen.conference.model.Location;
import com.prodyna.pac.mmonshausen.conference.model.Room;
import com.prodyna.pac.mmonshausen.conference.service.LocationService;
import com.prodyna.pac.mmonshausen.conference.service.RoomService;
import com.prodyna.pac.mmonshausen.conference.util.InputValidator;
import com.prodyna.pac.mmonshausen.conference.util.JSFMessageHelper;

/**
 * JSF-Controller for locations
 * 
 * @author Martin Monshausen, PRODYNA AG
 */
@Model
public class LocationController {
	@Inject
	private InputValidator inputValidator;
	
	@Inject
	private LocationService locationService;
	
	@Inject
	private RoomService roomService;
	
	@Inject
	private FacesContext facesContext;
	
	@Inject
	private JSFMessageHelper msgHelper;
	
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
			inputValidator.validateLocation(location);
			locationService.saveLocation(location);
        }  catch (final ConstraintViolationException e) {
        	final FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_WARN, "Validierungsfehler", msgHelper.getConstraintViolationMessage(e));
            facesContext.addMessage(null, m);
		} catch (final ValidationException e) {
			final FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_WARN, "Validierungsfehler", e.getMessage());
            facesContext.addMessage(null, m);
		} catch (final Exception e) {
            final FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR, "interner Fehler", msgHelper.getRootErrorMessage(e));
            facesContext.addMessage(null, m);
        }	
	}
	
	public void saveChanges() {
		try {
			inputValidator.validateLocation(location);
			locationService.updateLocation(location);
        }  catch (final ConstraintViolationException e) {
        	final FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_WARN, "Validierungsfehler", msgHelper.getConstraintViolationMessage(e));
            facesContext.addMessage(null, m);
		} catch (final ValidationException e) {
			final FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_WARN, "Validierungsfehler", e.getMessage());
            facesContext.addMessage(null, m);
		} catch (final Exception e) {
            final FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR, "interner Fehler", msgHelper.getRootErrorMessage(e));
            facesContext.addMessage(null, m);
        }
	}
	
	public void deleteLocation(final long id) {
		try {
			locationService.deleteLocation(id);
		} catch (final Exception e) {
            final FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR, "interner Fehler", msgHelper.getRootErrorMessage(e));
            facesContext.addMessage(null, m);
        }
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

	public void setMode(final String mode) {
		this.mode = mode;
	}

	public Long getId() {
		return id;
	}
}