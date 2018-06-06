package com.example.demo.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.net.UnknownHostException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.dao.DDDao;
import com.example.demo.dao.UrlShorteningDao;
import com.example.demo.service.UrlShorteningService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UrlShorteningControllerTest {
	@Autowired
	UrlShorteningController urlShorteningController;

	@Autowired
	UrlShorteningService urlShorteningService;

	@Autowired
	UrlShorteningDao urlShorteningDao;

	@Autowired
	DDDao ddDao;

	@Test
	public void UrlShorteningController_index_test() {
		// when
		String temp = urlShorteningController.index();

		// then
		assertThat(temp, is("index"));
	}

	@Test
	public void UrlShorteningController_redirect_success_test() throws NoSuchAlgorithmException {
		// given
		urlShorteningDao.insert("7G0W1u8c", "https://github.com/code0xff/URLShortening");
		String param = "7G0W1u8c";

		// when
		ModelAndView temp = urlShorteningController.redirect(param);

		// then
		assertThat(temp.getViewName(), is("redirect:" + urlShorteningService.getLongURL(param)));
	}

	@Test
	public void UrlShorteningController_redirect_fail_test() throws NoSuchAlgorithmException {
		// given
		String param = "HQcsyny0";

		// when
		ModelAndView temp = urlShorteningController.redirect(param);

		// then
		assertThat(temp.getViewName(), is("redirect:/error.html"));
	}

	@Test
	public void UrlShorteningController_urlShortening_success_test()
			throws NoSuchAlgorithmException, UnknownHostException {
		// given
		Map<String, Object> param = new HashMap<>();
		Map<String, Object> result = new HashMap<>();

		param.put("longURL", "https://www.youtube.com/watch?v=wdoxNb3JOcQ&t=789s");

		// when
		result = urlShorteningController.urlShortening(param);

		// then
		assertThat(result.get("message"), is("http://211.108.20.28:8080/3EvUzNu2"));
	}

	@Test
	public void UrlShorteningController_urlShortening_input_empty_test_1()
			throws NoSuchAlgorithmException, UnknownHostException {
		// given
		Map<String, Object> param = new HashMap<>();
		Map<String, Object> result = new HashMap<>();

		param.put("longURL", null);

		// when
		result = urlShorteningController.urlShortening(param);

		// then
		assertThat(result.get("message"), is(ddDao.getDDMessage("EMPTY_MESSAGE")));
	}

	@Test
	public void UrlShorteningController_urlShortening_input_empty_test_2()
			throws NoSuchAlgorithmException, UnknownHostException {
		// given
		Map<String, Object> param = new HashMap<>();
		Map<String, Object> result = new HashMap<>();

		param.put("longURL", "");

		// when
		result = urlShorteningController.urlShortening(param);

		// then
		assertThat(result.get("message"), is(ddDao.getDDMessage("EMPTY_MESSAGE")));
	}

	@Test
	public void UrlShorteningController_urlShortening_input_empty_test_3()
			throws NoSuchAlgorithmException, UnknownHostException {
		// given
		Map<String, Object> param = new HashMap<>();
		Map<String, Object> result = new HashMap<>();

		param.put("longURL", "   ");

		// when
		result = urlShorteningController.urlShortening(param);

		// then
		assertThat(result.get("message"), is(ddDao.getDDMessage("EMPTY_MESSAGE")));
	}

	@Test
	public void UrlShorteningController_urlShortening_input_not_start_with_http()
			throws NoSuchAlgorithmException, UnknownHostException {
		// given
		Map<String, Object> param = new HashMap<>();
		Map<String, Object> result = new HashMap<>();

		param.put("longURL", "abcdefg");

		// when
		result = urlShorteningController.urlShortening(param);

		// then
		assertThat(result.get("message"), is(ddDao.getDDMessage("INVALID_MESSAGE")));
	}
}
