package com.prodyna.pac.mmonshausen.conference.controller;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;

import com.prodyna.pac.mmonshausen.conference.model.Speaker;
import com.prodyna.pac.mmonshausen.conference.service.SpeakerService;
import com.prodyna.pac.mmonshausen.conference.util.JSFMessageHelper;

/**
 * JSF-Controller for speakers
 * 
 * @author Martin Monshausen, PRODYNA AG
 */
@Model
public class SpeakerController {
	
	@Inject
	private FacesContext facesContext;
	
	@Inject
	private JSFMessageHelper msgHelper;
	
	@Inject
	private SpeakerService speakerService;
	
	private Speaker speaker;
	private Long id;
	private String mode;
	
	@Produces
	@Named
	public Speaker getSpeaker() {
		if(speaker != null) {
			return speaker;
		} else {
			return new Speaker();
		}
	}
	
	public void createSpeaker() {
		try {
			speakerService.createSpeaker(speaker);
        }  catch (final ConstraintViolationException e) {
        	final FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_WARN, msgHelper.getConstraintViolationMessage(e), msgHelper.getConstraintViolationMessage(e));
            facesContext.addMessage(null, m);
		} catch (final ValidationException e) {
			final FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_WARN, e.getMessage(), e.getMessage());
            facesContext.addMessage(null, m);
		} catch (final Exception e) {
            final FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR, msgHelper.getRootErrorMessage(e), msgHelper.getRootErrorMessage(e));
            facesContext.addMessage(null, m);
        }
	}
	
	public void saveChanges() {
		try {
			speakerService.updateSpeaker(speaker);
        }  catch (final ConstraintViolationException e) {
        	final FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_WARN, msgHelper.getConstraintViolationMessage(e), msgHelper.getConstraintViolationMessage(e));
            facesContext.addMessage(null, m);
		} catch (final ValidationException e) {
			final FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_WARN, e.getMessage(), e.getMessage());
            facesContext.addMessage(null, m);
		} catch (final Exception e) {
            final FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR, msgHelper.getRootErrorMessage(e), msgHelper.getRootErrorMessage(e));
            facesContext.addMessage(null, m);
        }
	}
	
	public String deleteSpeaker(final long id) {
		try {
			speakerService.deleteSpeaker(id);
		} catch (final Exception e) {
            final FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR, msgHelper.getRootErrorMessage(e), msgHelper.getRootErrorMessage(e));
            facesContext.addMessage(null, m);
        }
		return "speaker_admin";
	}
	
	@PostConstruct
	public void initialize() {
		if((id != null) && (id !=0)) {
			speaker = speakerService.getSpeakerById(id);			
		} else {
			speaker = new Speaker();
		}
	}
		
	public void setId(final Long id) {
		this.id = id;
		initialize();
	}

	public Long getId() {
		return id;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(final String mode) {
		this.mode = mode;
	}
}