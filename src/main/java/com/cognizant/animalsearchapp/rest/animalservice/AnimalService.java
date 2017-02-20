/**
 * 
 */
package com.cognizant.animalsearchapp.rest.animalservice;

import java.util.List;

import com.cognizant.animalsearchapp.rest.model.AnimalAccessLog;
import com.cognizant.animalsearchapp.rest.model.Animals;

/**
 * @author Syamala
 *
 */
public interface AnimalService {
	public Animals getAnimalRegionByName(List<String> animalList);

	public List<AnimalAccessLog> getAccessLog(List<String> names);

	public int logAccessRequest(List<String> names);
}
