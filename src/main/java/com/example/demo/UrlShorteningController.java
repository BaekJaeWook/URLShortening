package com.example.demo;

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

@Controller
public class UrlShorteningController {

	UrlShorteningService urlShorteningService;

	@Autowired
	public void setUrlShorteningService(UrlShorteningService urlShorteningService) {
		this.urlShorteningService = urlShorteningService;
	}

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index() {
		System.out.println("home/index");
		return "home/index";
	}

	@RequestMapping(value = "/{param:[A-Za-z0-9]{1,8}$}")
	public ModelAndView redirect(@PathVariable(required=true) String param) throws NoSuchAlgorithmException {
		System.out.println(param);
		return new ModelAndView("redirect:" + urlShorteningService.getLongURL(param));
	}

	@ResponseBody
	@RequestMapping(value = "/shortening", method = RequestMethod.POST)
	public Map<String, Object> urlShortening(@RequestBody Map<String, Object> param) throws NoSuchAlgorithmException {
		Map<String, Object> result = new HashMap<>();

		String shorteningKey = urlShorteningService.urlShortening((String) param.get("longURL"));
		String shortenURL = "http://localhost:8080/" + shorteningKey;

		System.out.println("shortenURL:" + shortenURL);
		result.put("shortenURL", shortenURL);

		return result;
	}
}
