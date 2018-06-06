package com.example.demo.dao;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UrlShorteningDaoTest {
	@Autowired
	UrlShorteningDao urlShorteningDao;

	@Test
	public void UrlShorteningDao_shorten_test() {
		// when
		int temp = urlShorteningDao.insert("3wwuu0eU", "https://www.kakaopay.com/");

		// then
		assertThat(temp, is(1));
	}

	@Test
	public void UrlShorteningDao_getLongURL_test() {
		// given
		urlShorteningDao.insert("vHl1T75H", "https://www.google.com/");

		// when
		String temp = urlShorteningDao.getLongURL("vHl1T75H");

		// then
		assertThat(temp, is("https://www.google.com/"));
	}

	@Test
	public void UrlShorteningDao_getShorteningKey_test() {
		// given
		urlShorteningDao.insert("h9Yue8AD", "https://github.com/");

		// when
		String temp = urlShorteningDao.getShorteningKey("https://github.com/");

		// then
		assertThat(temp, is("h9Yue8AD"));
	}

	@Test
	public void UrlShorteningDao_isRegisteredLongURL_true_test() {
		// given
		urlShorteningDao.insert("eR81OoV6", "https://www.youtube.com/");

		// when
		boolean temp = urlShorteningDao.isRegisteredLongURL("https://www.youtube.com/");

		// then
		assertThat(temp, is(true));
	}

	@Test
	public void UrlShorteningDao_isExistKey_true_test() {
		// given
		urlShorteningDao.insert("Sak30Hsw", "http://www.samsung.com");

		// when
		boolean temp = urlShorteningDao.isExistKey("Sak30Hsw");

		// then
		assertThat(temp, is(true));
	}

	@Test
	public void UrlShorteningDao_isRegisteredLongURL_false_test() {
		// when
		boolean temp = urlShorteningDao.isRegisteredLongURL("https://code0xff.tistory.com");

		// then
		assertThat(temp, is(false));
	}

	@Test
	public void UrlShorteningDao_isExistKey_false_test() {
		// when
		boolean temp = urlShorteningDao.isExistKey("99999999");

		// then
		assertThat(temp, is(false));
	}
}
