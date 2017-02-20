/**
 * 
 */
package com.cognizant.animalsearchapp.rest.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

/**
 * @author Syamala
 *
 */
@JacksonXmlRootElement(localName = "animalrequest")
public class AnimalRequest implements Serializable {

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "AnimalRequest [" + (names != null ? "names=" + names : "") + "]";
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@JacksonXmlProperty(localName = "name")
	private List<String> names = new ArrayList<String>();

	/**
	 * @return the names
	 */
	public final List<String> getNames() {
		return names;
	}

	/**
	 * @param names
	 *            the names to set
	 */
	public final void setNames(List<String> names) {
		this.names = names;
	}

}
