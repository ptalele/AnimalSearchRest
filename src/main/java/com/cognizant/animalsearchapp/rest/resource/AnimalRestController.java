package com.cognizant.animalsearchapp.rest.resource;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.QueryParam;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cognizant.animalsearchapp.rest.animalservice.AnimalService;
import com.cognizant.animalsearchapp.rest.exception.AnimalNotFoundException;
import com.cognizant.animalsearchapp.rest.exception.DuplicateReqestException;
import com.cognizant.animalsearchapp.rest.exception.InvalidRequestException;
import com.cognizant.animalsearchapp.rest.model.AnimalAccessLog;
import com.cognizant.animalsearchapp.rest.model.AnimalRequest;
import com.cognizant.animalsearchapp.rest.model.Animals;
import com.cognizant.animalsearchapp.rest.model.ErrorResponse;
import com.cognizant.animalsearchapp.rest.validator.AnimalRequestValidator;
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

@RestController
@RequestMapping(value = "/v1/animals")
public class AnimalRestController {

	private final Logger logger = LoggerFactory.getLogger(AnimalRestController.class);

	@Autowired
	private AnimalService animalService;

	@Autowired
	private AnimalRequestValidator validator;

	/**
	 * This API get the Geographical region of animal by Names Input is
	 * serialized xml String and output is XML It validated the XML request
	 * against XSD It process only 3 animals per request It throws
	 * DuplicateReqestException if Same 3 animal names requested in past 1day.
	 * It logs incoming Request for request history
	 * 
	 * @param requestXml
	 * @return
	 * @throws DuplicateReqestException
	 * @throws InvalidRequestException
	 * @throws AnimalNotFoundException
	 */
	@RequestMapping(value = "/region", method = RequestMethod.GET)
	public Animals getAnimalRegion(@QueryParam("requestXml") String requestXml)
			throws DuplicateReqestException, InvalidRequestException, AnimalNotFoundException {

		if (requestXml == null)
			throw new InvalidRequestException();

		// Validate Against XSD
		if (!validator.validateXMLSchema("AnimalRequest.xsd", requestXml))
			throw new InvalidRequestException();

		// Map XML to Java Object
		JacksonXmlModule module = new JacksonXmlModule();
		module.setDefaultUseWrapper(false);
		XmlMapper xmlMapper = new XmlMapper(module);
		AnimalRequest request = null;

		try {
			logger.debug("RequestXml :{}", requestXml);
			request = xmlMapper.readValue(requestXml, AnimalRequest.class);
			if (request == null || CollectionUtils.isEmpty(request.getNames()))
				throw new InvalidRequestException();
			logger.debug("Schema validation Completed. Converted AnimalRequest :{}", request);
		} catch (IOException e) {
			logger.error("XML Parsing Failed. Bad Input");
			throw new InvalidRequestException();
		}
		/*
		 * check if duplicate Request Same can be done in better way by fetching
		 * animal regions from DB whose access time is more than 12 hours
		 */

		List<AnimalAccessLog> requestLogs = animalService.getAccessLog(request.getNames());
		if (!CollectionUtils.isEmpty(requestLogs)) {
			logger.debug("Duplicate Request in last 1 day");
			throw new DuplicateReqestException();
		}
		// Log a request
		animalService.logAccessRequest(request.getNames());

		// Find the Animals
		Animals animals = animalService.getAnimalRegionByName(request.getNames());

		// Validate response
		if (animals == null || CollectionUtils.isEmpty(animals.getAnimals()))
			throw new AnimalNotFoundException();

		return animals;
	}

	/**
	 * Handler for DuplicateRequestException
	 * 
	 * @param req
	 * @param e
	 * @return ResponseEntity
	 */
	@ExceptionHandler(DuplicateReqestException.class)
	public ResponseEntity<ErrorResponse> duplicateRequestExceptionHandler(HttpServletRequest req, Exception e) {
		ErrorResponse error = new ErrorResponse("DUPLICATE_REQUEST", "Duplicate request in last 24 hours");
		return new ResponseEntity<ErrorResponse>(error, HttpStatus.CONFLICT);
	}

	/**
	 * Handler for InvalidRequestException
	 * 
	 * @param req
	 * @param e
	 * @return ResponseEntity
	 */
	@ExceptionHandler(InvalidRequestException.class)
	public ResponseEntity<ErrorResponse> badRequestExceptionHandler(HttpServletRequest req, Exception e) {
		ErrorResponse error = new ErrorResponse("INVALID_REQUEST", "Bad Request XML input");
		return new ResponseEntity<ErrorResponse>(error, HttpStatus.BAD_REQUEST);
	}

	/**
	 * Handler for AnimalNotFoundException
	 * 
	 * @param req
	 * @param e
	 * @return ResponseEntity
	 */
	@ExceptionHandler(AnimalNotFoundException.class)
	public ResponseEntity<ErrorResponse> animalNotFoundExceptionHandler(HttpServletRequest req, Exception e) {
		ErrorResponse error = new ErrorResponse("ANIMAL_NOT_FOUND", "Animals not found in Repository");
		return new ResponseEntity<ErrorResponse>(error, HttpStatus.NOT_FOUND);
	}

}
