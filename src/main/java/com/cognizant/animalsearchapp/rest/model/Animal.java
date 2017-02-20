package com.cognizant.animalsearchapp.rest.model;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

@JacksonXmlRootElement(localName = "animal")
public class Animal implements Serializable {

	private static final long serialVersionUID = 1L;

	@JacksonXmlProperty(localName = "id")
	private Integer id;

	@JacksonXmlProperty(localName = "name")
	private String name;

	@JacksonXmlProperty(localName = "region")
	private String region;

	private Date accessTimeStamp;

	// @return the accessTimeStamp
	public Date getAccessTimeStamp() {
		return accessTimeStamp;
	}

	// @param accessTimeStamp the accessTimeStamp to set

	public void setAccessTimeStamp(Date accessTimeStamp) {
		this.accessTimeStamp = accessTimeStamp;
	}

	// @return the id

	public Integer getId() {
		return id;
	}

	// @param id the id to set

	public void setId(Integer id) {
		this.id = id;
	}

	// @return the name

	public String getName() {
		return name;
	}

	// @param name the name to set

	public void setName(String name) {
		this.name = name;
	}

	// @return the region

	public String getRegion() {
		return region;
	}

	// @param region the region to set

	public void setRegion(String region) {
		this.region = region;
	}

	public Animal(Integer id, String name, String region, Date accessTime) {

		this.id = id;
		this.name = name;
		this.region = region;
		this.accessTimeStamp = accessTime;

	}

	// Default constructor
	public Animal() {

	}

	@Override
	public String toString() {
		return "Animal [" + (id != null ? "id=" + id + ", " : "") + (name != null ? "name=" + name + ", " : "")
				+ (region != null ? "region=" + region : "") + "]";
	}

}
