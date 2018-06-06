package com.example.demo.controller;

import java.net.UnknownHostException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.service.DDService;
import com.example.demo.service.UrlShorteningService;
import com.example.demo.util.XssFilter;

@Controller
public class UrlShorteningController {
	private static final String LONG_URL = "longURL";
	private static final String NOT_EXIST = "NOT_EXIST";
	private static final String FAIL = "FAIL";
	private static final String EMPTY_MESSAGE = "EMPTY_MESSAGE";
	private static final String INVALID_MESSAGE = "INVALID_MESSAGE";
	private static final String SUCCESS = "SUCCESS";
	private static final String STATUS = "status";
	private static final String MESSAGE = "message";
	private static final String IP_ADDRESS = "IP_ADDRESS";
	private static final String PORT_NUMBER = "PORT_NUMBER";

	@Autowired
	UrlShorteningService urlShorteningService;

	@Autowired
	DDService ddService;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index() {
		return "index";
	}

	@RequestMapping(value = "/{param:[A-Za-z0-9]{1,8}$}")
	public ModelAndView redirect(@PathVariable(required = true) String param) throws NoSuchAlgorithmException {
		if (ddService.getDDMessage(NOT_EXIST).equals(urlShorteningService.getLongURL(param))) {
			return new ModelAndView("redirect:/error.html");
		}
		return new ModelAndView("redirect:" + urlShorteningService.getLongURL(param));
	}

	@ResponseBody
	@RequestMapping(value = "/shortening", method = RequestMethod.POST)
	public Map<String, Object> urlShortening(@RequestBody Map<String, Object> param)
			throws NoSuchAlgorithmException, UnknownHostException {
		Map<String, Object> result = new HashMap<>();

		String longURL = (String) param.get(LONG_URL);

		if (longURL == null || "".equals(longURL) || longURL.trim().length() == 0) {
			result.put(STATUS, ddService.getDDMessage(FAIL));
			result.put(MESSAGE, ddService.getDDMessage(EMPTY_MESSAGE));
			return result;
		}

		longURL = XssFilter.XssReplace(longURL);

		if (!longURL.startsWith("http://") && !longURL.startsWith("https://")) {
			result.put(STATUS, ddService.getDDMessage(FAIL));
			result.put(MESSAGE, ddService.getDDMessage(INVALID_MESSAGE));
			return result;
		}

		String shorteningKey = urlShorteningService.shorten((String) param.get(LONG_URL));
		String shortenURL = "http://" + ddService.getDDMessage(IP_ADDRESS) + ":" + ddService.getDDMessage(PORT_NUMBER) + "/" + shorteningKey;

		result.put(STATUS, ddService.getDDMessage(SUCCESS));
		result.put(MESSAGE, shortenURL);
		return result;
	}
}
