package com.cognizant.animalsearchapp.rest.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Invalid request")
public class InvalidRequestException extends Exception {

	private static final long serialVersionUID = 1L;

}
