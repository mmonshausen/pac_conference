package com.prodyna.pac.mmonshausen.conference.util;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.faces.context.FacesContext;

public class Resources {
	    @Produces
	    @RequestScoped
	    public FacesContext produceFacesContext() {
	        return FacesContext.getCurrentInstance();
	    }
}
