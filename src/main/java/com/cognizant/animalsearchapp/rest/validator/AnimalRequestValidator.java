/**
 * 
 */
package com.cognizant.animalsearchapp.rest.validator;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.xml.sax.SAXException;

/**
 * @author Syamala
 *
 */
@Component
public class AnimalRequestValidator {

	private final Logger logger = LoggerFactory.getLogger(AnimalRequestValidator.class);

	public boolean validateXMLSchema(String xsdPath, String xml) {

		try {
			SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
			ClassLoader classLoader = getClass().getClassLoader();
			File file = new File(classLoader.getResource(xsdPath).getFile());
			Schema schema = factory.newSchema(file);
			Validator validator = schema.newValidator();
			validator.validate(new StreamSource(new StringReader(xml)));
		} catch (IOException | SAXException e) {
			logger.error("Schema Validation Failed : {} ", e.getMessage());
			return false;
		}
		return true;
	}

}
