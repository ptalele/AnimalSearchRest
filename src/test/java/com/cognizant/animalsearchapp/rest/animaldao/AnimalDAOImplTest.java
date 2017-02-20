package com.cognizant.animalsearchapp.rest.animaldao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.util.ReflectionTestUtils;

import com.cognizant.animalsearchapp.rest.model.Animal;
import com.cognizant.animalsearchapp.rest.model.AnimalAccessLog;
import com.cognizant.animalsearchapp.rest.model.Animals;
import com.google.common.base.Joiner;

@RunWith(MockitoJUnitRunner.class)
public class AnimalDAOImplTest {

	@Mock
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Mock
	DataSource dataSource;

	AnimalDAOImpl daoImpl;

	@Before
	public void setUp() throws Exception {
		daoImpl = new AnimalDAOImpl(dataSource);

		// Spring utility class for use in unit and integration testing
		// scenarios.
		// Allows mocks to be used where no setters are accessible
		ReflectionTestUtils.setField(daoImpl, "namedParameterJdbcTemplate", namedParameterJdbcTemplate,
				NamedParameterJdbcTemplate.class);
	}

	@Test
	public final void testAnimalDAOImpl() {
		assertNotNull(daoImpl);
		assertNotNull(namedParameterJdbcTemplate);

	}

	@Test
	public final void testFindRegionByName() {
		Animals animals = new Animals();
		List<Animal> animalList = new ArrayList<Animal>();
		Animal animal = new Animal(1, "Tiger", "Africa", new Date());

		Animal animal1 = new Animal(2, "Lion", "India", new Date());

		Animal animal2 = new Animal(3, "Monkey", "North America", new Date());

		animalList.add(animal);
		animalList.add(animal1);
		animalList.add(animal2);
		animals.addAnimals(animalList);

		List<String> names = Arrays.asList("Tiger", "Lion", "Monkey");

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("names", names);
		String sql = "SELECT * FROM Animals where name in (:names)";

		given(namedParameterJdbcTemplate.query(eq(sql), eq(params), Matchers.any(AnimalRowMapper.class)))
				.willReturn(animalList);

		Animals result = daoImpl.findRegionByName(names);

		verify(namedParameterJdbcTemplate, times(1)).query(eq(sql), eq(params), Matchers.any(AnimalRowMapper.class));

		assertNotNull(result);
		assertNotNull(result.getAnimals());
		assertEquals(result.getAnimals().size(), 3);
	}

	@Test
	public final void testLogAccessRequest() {

		List<String> names = Arrays.asList("Tiger", "Lion", "Monkey");

		MapSqlParameterSource param = new MapSqlParameterSource();
		param.addValue("name", Joiner.on(",").join(names));
		String sql = "INSERT INTO Animal_Access_log (names) VALUES(:name)";

		given(namedParameterJdbcTemplate.update(eq(sql), Matchers.any(MapSqlParameterSource.class))).willReturn(1);

		int result = daoImpl.logAccessRequest(names);

		verify(namedParameterJdbcTemplate, times(1)).update(eq(sql), Matchers.any(MapSqlParameterSource.class));
		assertEquals(1, result);
	}

	@Test
	public final void testGetAccessLog() {

		List<String> names = Arrays.asList("Tiger", "Lion", "Monkey");
		List<AnimalAccessLog> accessLogs = new ArrayList<AnimalAccessLog>();
		AnimalAccessLog log = new AnimalAccessLog();
		log.setAccessTime(new Date());
		log.setName("Tiger, Lion, Monkey");
		log.setRequestId(2);
		accessLogs.add(log);
		MapSqlParameterSource param = new MapSqlParameterSource();
		param.addValue("name", Joiner.on(",").join(names));
		String sql = "SELECT * FROM Animal_Access_log where names like (:name) and TIMESTAMPDIFF(HOUR, accessTimestamp, NOW()) < 24";

		given(namedParameterJdbcTemplate.query(eq(sql), Matchers.any(MapSqlParameterSource.class),
				Matchers.any(AnimalAccessLogMapper.class))).willReturn(accessLogs);

		List<AnimalAccessLog> result = daoImpl.getAccessLog(names);

		verify(namedParameterJdbcTemplate, times(1)).query(eq(sql), Matchers.any(MapSqlParameterSource.class),
				Matchers.any(AnimalAccessLogMapper.class));

		assertNotNull(result);

		assertEquals(result.size(), 1);
	}

}
