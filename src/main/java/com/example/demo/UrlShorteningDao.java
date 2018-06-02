package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UrlShorteningDao {

	@Autowired
	JdbcTemplate jdbcTemplate;

	public int insert(String shorteningKey, String longURL) {
		String sql = "insert into url_map(shortening_key, long_url, create_date, expiry_date) values(?, ?, sysdate, sysdate + 7)";
		return jdbcTemplate.update(sql, shorteningKey, longURL);
	}

	public String getShorteningKey(String longURL) {
		String sql = "select * from url_map where long_url = ?";

		Object[] object = new Object[1];
		object[0] = longURL;
		
		String shorteningKey = jdbcTemplate
				.query(sql, object, new BeanPropertyRowMapper<UrlMap>(UrlMap.class))
				.get(0)
				.getShorteningKey();
		
		return shorteningKey;
	}

	public String getLongURL(String shorteningKey) {
		String sql = "select * from url_map where shortening_key = ?";

		Object[] object = new Object[1];
		object[0] = shorteningKey;

		String longURL = jdbcTemplate
				.query(sql, object, new BeanPropertyRowMapper<UrlMap>(UrlMap.class))
				.get(0)
				.getLongUrl();
		
		return longURL;
	}
	
	public boolean isRegisteredLongURL(String longURL) {
		String sql = "select * from url_map where long_url = ?";
		
		Object[] object = new Object[1];
		object[0] = longURL;
		
		return jdbcTemplate.query(sql, object, new BeanPropertyRowMapper<UrlMap>(UrlMap.class)).size() > 0;
	}

	public boolean isExistKey(String shorteningKey) {
		String sql = "select * from url_map where shortening_key = ?";

		Object[] object = new Object[1];
		object[0] = shorteningKey;

		return jdbcTemplate.query(sql, object, new BeanPropertyRowMapper<UrlMap>(UrlMap.class)).size() > 0;
	}
}
