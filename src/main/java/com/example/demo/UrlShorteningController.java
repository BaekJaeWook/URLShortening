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
		return "index";
	}

	@RequestMapping(value = "/{param:[A-Za-z0-9]{1,8}$}")
	public ModelAndView redirect(@PathVariable(required = true) String param) throws NoSuchAlgorithmException {
		if (UrlShorteningService.ERR_CODE_1.equals(urlShorteningService.getLongURL(param))) {
			return new ModelAndView("redirect:/error.html");
		}

		return new ModelAndView("redirect:" + urlShorteningService.getLongURL(param));
	}

	@ResponseBody
	@RequestMapping(value = "/shortening", method = RequestMethod.POST)
	public Map<String, Object> urlShortening(@RequestBody Map<String, Object> param) throws NoSuchAlgorithmException {
		Map<String, Object> result = new HashMap<>();
		String longURL = (String) param.get("longURL");

		if (longURL == null || "".equals(longURL) || longURL.trim().length() == 0) {
			result.put("status", "fail");
			result.put("message", "입력란이 비어있습니다.");
			return result;
		}

		if (!longURL.startsWith("http://") && !longURL.startsWith("https://")) {
			result.put("status", "fail");
			result.put("message", "http:// 혹은 https://로 시작하는 유효한 URL을 입력해주시기 바랍니다.");
			return result;
		}

		String shorteningKey = urlShorteningService.shorten((String) param.get("longURL"));
		String shortenURL = "http://localhost:8080/" + shorteningKey;

		result.put("status", "success");
		result.put("message", shortenURL);
		return result;
	}
}
