package com.example.demo;

import java.net.InetAddress;
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

@Controller
public class UrlShorteningController {
	@Autowired
	UrlShorteningService urlShorteningService;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index() {
		return "index";
	}

	@RequestMapping(value = "/{param:[A-Za-z0-9]{1,8}$}")
	public ModelAndView redirect(@PathVariable(required = true) String param) throws NoSuchAlgorithmException {
		if (DD.ERR_CODES[0].equals(urlShorteningService.getLongURL(param))) {
			return new ModelAndView("redirect:/error.html");
		}
		return new ModelAndView("redirect:" + urlShorteningService.getLongURL(param));
	}

	@ResponseBody
	@RequestMapping(value = "/shortening", method = RequestMethod.POST)
	public Map<String, Object> urlShortening(@RequestBody Map<String, Object> param)
			throws NoSuchAlgorithmException, UnknownHostException {
		Map<String, Object> result = new HashMap<>();
		String longURL = (String) param.get("longURL");
		InetAddress local = InetAddress.getLocalHost();
		String ip = local.getHostAddress();

		if (longURL == null || "".equals(longURL) || longURL.trim().length() == 0) {
			result.put("status", DD.FAIL);
			result.put("message", DD.EMPTY_MESSAGE);
			return result;
		}

		if (!longURL.startsWith("http://") && !longURL.startsWith("https://")) {
			result.put("status", DD.FAIL);
			result.put("message", DD.INVALID_MESSAGE);
			return result;
		}

		String shorteningKey = urlShorteningService.shorten((String) param.get("longURL"));
		String shortenURL = "http://" + ip + ":8080/" + shorteningKey;

		result.put("status", DD.SUCCESS);
		result.put("message", shortenURL);
		return result;
	}
}
