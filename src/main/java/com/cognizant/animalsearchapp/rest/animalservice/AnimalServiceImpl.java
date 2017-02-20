/**
 * 
 */
package com.cognizant.animalsearchapp.rest.animalservice;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.cognizant.animalsearchapp.rest.animaldao.AnimalDao;
import com.cognizant.animalsearchapp.rest.model.AnimalAccessLog;
import com.cognizant.animalsearchapp.rest.model.Animals;

/**
 * @author Syamala
 *
 */
@Service
public class AnimalServiceImpl implements AnimalService {
	private final Logger logger = LoggerFactory.getLogger(AnimalServiceImpl.class);

	@Autowired
	private AnimalDao animalDao;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cognizant.animalsearchapp.rest.animalservice.AnimalService#
	 * getAnimalRegionByName(java.util.List)
	 */
	@Override
	public Animals getAnimalRegionByName(List<String> animalList) {
		logger.debug("Animal List: {}", animalList);
		Animals animals = new Animals();

		if (!CollectionUtils.isEmpty(animalList)) {
			animals = animalDao.findRegionByName(animalList);
		}
		logger.debug("Animal Region Resoponse: {}", animals);
		return animals;
	}

	@Override
	public int logAccessRequest(List<String> names) {
		logger.debug("Animal logAccessRequest: {}", names);
		return animalDao.logAccessRequest(names);
	}

	@Override
	public List<AnimalAccessLog> getAccessLog(List<String> names) {

		logger.debug("Animal updateAccessTime: {}", names);
		return animalDao.getAccessLog(names);
	}

}
