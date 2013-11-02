package com.prodyna.pac.mmonshausen.conference.util;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.faces.context.FacesContext;

/**
 * produces references to resources needed by conference_webs classes
 * 
 * @author Martin Monshausen, PRODYNA AG
 */
public class Resources {
	/**
	 * produce reference to {@link FacesContext} used by JSF controllers to send
	 * error messages to UI
	 * 
	 * @return reference to {@link FacesContext}
	 */
	@Produces
	@RequestScoped
	public FacesContext produceFacesContext() {
		return FacesContext.getCurrentInstance();
	}
}
