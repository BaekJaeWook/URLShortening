package com.example.demo;

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UrlShorteningController {

	UrlShorteningService urlShorteningService;

	@Autowired
	public void setUrlShorteningService(UrlShorteningService urlShorteningService) {
		this.urlShorteningService = urlShorteningService;
	}
	
	@RequestMapping(value="/")
	public String index() {
		return "index";
	}

	@RequestMapping(value="/{param}")
	public String redirect(@PathVariable String param) throws NoSuchAlgorithmException {
		String longURL = urlShorteningService.getLongURL(param);
		return "redirect:" + longURL;
	}

	@ResponseBody
	@RequestMapping(value="/shortening", method=RequestMethod.POST)
	public Map<String, Object> urlShortening(@RequestBody Map<String, Object> param) throws NoSuchAlgorithmException {
		Map<String, Object> result = new HashMap<>();
		
		String shorteningKey = urlShorteningService.urlShortening((String) param.get("longURL"));
		String shortenURL = "http://localhost:8080/" + shorteningKey;
		
		System.out.println(shortenURL);
		result.put("shortenURL", shortenURL);
		
		return result;
	}
}
