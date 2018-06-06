package com.example.demo;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.net.UnknownHostException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.controller.UrlShorteningController;
import com.example.demo.dao.DDDao;
import com.example.demo.dao.UrlShorteningDao;
import com.example.demo.service.UrlShorteningService;
import com.example.demo.vo.UrlMap;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UrlShorteningApplicationTests {

	@Autowired
	UrlShorteningController urlShorteningController;

	@Autowired
	UrlShorteningService urlShorteningService;

	@Autowired
	UrlShorteningDao urlShorteningDao;

	@Autowired
	DDDao ddDao;

	@Autowired
	JdbcTemplate jdbcTemplate;

	List<UrlMap> list;
	
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