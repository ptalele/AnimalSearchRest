/**
 * 
 */
package com.cognizant.animalsearchapp.rest.animaldao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.cognizant.animalsearchapp.rest.model.Animal;

/**
 * @author Syamala
 *
 */
public class AnimalRowMapper implements RowMapper<Animal> {

	@Override
	public Animal mapRow(ResultSet rs, int rowNum) throws SQLException {
		Animal animal = new Animal();

		animal.setId(rs.getInt("id"));
		animal.setName(rs.getString("name"));
		animal.setRegion(rs.getString("region"));
		animal.setAccessTimeStamp(rs.getTimestamp("accesstime"));
		return animal;
	}

}
