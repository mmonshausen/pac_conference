package com.prodyna.pac.mmonshausen.conference.util;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

/**
 * helper methods for handling JSF error messages used by controllers
 * 
 * @author Martin Monshausen, PRODYNA AG
 */
public class JSFMessageHelper {
	
	public String getRootErrorMessage(final Exception e) {
        // Default to general error message that registration failed.
        String errorMessage = "interner Fehler aufgetreten; bitte Administrator benachrichtigen";
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

	public String getConstraintViolationMessage(final ConstraintViolationException e) {
		final StringBuffer buffer = new StringBuffer();
    	for (final ConstraintViolation<?> violation : e.getConstraintViolations()) {
    		buffer.append(violation.getMessage());
		}
    	return buffer.toString();
	}
}
