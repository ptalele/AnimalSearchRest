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
@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Animal Not found")
public class AnimalNotFoundException extends Exception {

	private static final long serialVersionUID = 1L;

}
