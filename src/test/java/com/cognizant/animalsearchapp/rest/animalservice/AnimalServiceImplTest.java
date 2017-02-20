/**
 * 
 */
package com.cognizant.animalsearchapp.rest.animalservice;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyListOf;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.util.CollectionUtils;

import com.cognizant.animalsearchapp.rest.animaldao.AnimalDAOImpl;
import com.cognizant.animalsearchapp.rest.model.Animal;
import com.cognizant.animalsearchapp.rest.model.AnimalAccessLog;
import com.cognizant.animalsearchapp.rest.model.Animals;

/**
 * @author Syamala
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class AnimalServiceImplTest {

	@Mock
	AnimalDAOImpl dao;

	@InjectMocks
	AnimalServiceImpl service;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * Test method for
	 * {@link com.cognizant.animalsearchapp.rest.animalservice.AnimalServiceImpl#getAnimalRegionByName(java.util.List)}.
	 * 
	 */
	@Test
	public final void testGetAnimalRegionByName() {
		Animals animals = new Animals();
		List<Animal> animalList = new ArrayList<Animal>();
		Animal animal = new Animal();
		animal.setName("tiger");
		animal.setRegion("USA");
		Animal animal1 = new Animal();
		animal1.setName("lion");
		animal1.setRegion("AFRICA");
		Animal animal2 = new Animal();
		animal2.setName("elephant");
		animal2.setRegion("INDIA");
		animalList.add(animal);
		animalList.add(animal1);
		animalList.add(animal2);
		animals.addAnimals(animalList);
		List<String> names = Arrays.asList("Tiger", "Lion", "elephant");

		Mockito.when(dao.findRegionByName(anyListOf(String.class))).thenReturn(animals);

		Animals result = service.getAnimalRegionByName(names);
		assertNotNull(result);
		assertNotNull(result.getAnimals());
		assertEquals(animalList, result.getAnimals());
		Mockito.verify(dao).findRegionByName(anyListOf(String.class));
	}

	/**
	 * Test method for
	 * {@link com.cognizant.animalsearchapp.rest.animalservice.AnimalServiceImpl#logAccessRequest(java.util.List)}.
	 */
	@Test
	public final void testLogAccessRequest() {
		List<String> names = Arrays.asList("Tiger", "Lion", "Monkey");
		Mockito.when(dao.logAccessRequest(anyListOf(String.class))).thenReturn(1);
		int result = service.logAccessRequest(names);
		assertEquals(result, 1);
		Mockito.verify(dao).logAccessRequest(anyListOf(String.class));
	}

	/**
	 * Test method for
	 * {@link com.cognizant.animalsearchapp.rest.animalservice.AnimalServiceImpl#getAccessLog(java.util.List)}.
	 */
	@Test
	public final void testGetAccessLog() {
		List<AnimalAccessLog> logs = new ArrayList<AnimalAccessLog>();
		AnimalAccessLog accessLog = new AnimalAccessLog();
		accessLog.setAccessTime(new Date());
		accessLog.setName("lion,tiger,monkey");
		accessLog.setRequestId(1);
		logs.add(accessLog);

		List<String> names = Arrays.asList("Tiger", "Lion", "Monkey");

		Mockito.when(dao.getAccessLog(anyListOf(String.class))).thenReturn(logs);

		List<AnimalAccessLog> result = service.getAccessLog(names);
		assertNotNull(result);
		assertTrue(!CollectionUtils.isEmpty(result));
		assertEquals(result.get(0).getName(), "lion,tiger,monkey");
		assertEquals(result.get(0).getRequestId(), 1);
		Mockito.verify(dao).getAccessLog(anyListOf(String.class));
	}

}
