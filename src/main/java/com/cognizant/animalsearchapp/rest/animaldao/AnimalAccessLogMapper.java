/**
 * 
 */
package com.cognizant.animalsearchapp.rest.animaldao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.cognizant.animalsearchapp.rest.model.AnimalAccessLog;

/**
 * @author Syamala
 *
 */
public class AnimalAccessLogMapper implements RowMapper<AnimalAccessLog> {
	@Override
	public AnimalAccessLog mapRow(ResultSet rs, int rowNum) throws SQLException {
		AnimalAccessLog animalAccessLog = new AnimalAccessLog();

		animalAccessLog.setRequestId(rs.getInt("RequestId"));
		animalAccessLog.setName(rs.getString("names"));
		animalAccessLog.setAccessTime(rs.getTimestamp("accessTimestamp"));
		return animalAccessLog;
	}
}
