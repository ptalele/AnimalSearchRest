/**
 * 
 */
package com.cognizant.animalsearchapp.rest.animaldao;

import java.util.List;

import com.cognizant.animalsearchapp.rest.model.AnimalAccessLog;
import com.cognizant.animalsearchapp.rest.model.Animals;

/**
 * @author Syamala
 *
 */
public interface AnimalDao {
	public Animals findRegionByName(List<String> names);

	public List<AnimalAccessLog> getAccessLog(List<String> names);

	public int logAccessRequest(List<String> names);
}
