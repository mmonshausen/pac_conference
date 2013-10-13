package com.prodyna.pac.mmonshausen.conference.rest;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.ws.rs.core.Response;

/**
 * Helper class containing methods for building REST responses
 * 
 * @author Martin Monshausen, PRODYNA AG
 */
public class RESTHelper {

	/**
	 * put all validation violations into an response object suitable for
	 * handing over to requester
	 * 
	 * @param violations
	 *            list of ConstraitViolations to transform into key value pairs
	 *            for reponse object
	 * @return Response object containing all contraint violations
	 */
	public static Response.ResponseBuilder createViolationResponse(
			final Set<ConstraintViolation<?>> violations) {
		final Map<String, String> responseObj = new HashMap<String, String>();

		for (final ConstraintViolation<?> violation : violations) {
			responseObj.put(violation.getPropertyPath().toString(),
					violation.getMessage());
		}

		return Response.status(Response.Status.BAD_REQUEST).entity(responseObj);
	}
}