/**
 * 
 */
package com.cognizant.animalsearchapp.rest.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Syamala
 *
 */
@ResponseStatus(value = HttpStatus.CONFLICT, reason = "Duplicate request in last 24 hours")
public class DuplicateReqestException extends Exception {

	private static final long serialVersionUID = 100L;

}
