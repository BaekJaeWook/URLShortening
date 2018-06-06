package com.example.demo.service;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.security.NoSuchAlgorithmException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.demo.dao.UrlShorteningDao;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UrlShorteningServiceTest {
	@Autowired
	UrlShorteningService urlShorteningService;

	@Autowired
	UrlShorteningDao urlShorteningDao;

	@Test
	public void UrlShorteningService_register_registed_url_test() throws NoSuchAlgorithmException {
		// given
		String longURL = "https://www.daum.net/";
		urlShorteningDao.insert("1T2doEsk", longURL);

		// when
		String temp = urlShorteningService.shorten(longURL);

		// then
		assertThat(temp, is("1T2doEsk"));
	}
}
