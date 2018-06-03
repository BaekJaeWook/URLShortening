package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class DDDao {

	@Autowired
	JdbcTemplate jdbcTemplate;

	public String getDDMessage(String id) {
		String sql = "select * from dd where id = ?";

		Object[] object = new Object[1];
		object[0] = id;

		String message = jdbcTemplate.query(sql, object, new BeanPropertyRowMapper<DD>(DD.class)).get(0).getMessage();

		return message;
	}
}
