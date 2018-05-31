package com.example.demo;

import java.security.NoSuchAlgorithmException;

import org.springframework.stereotype.Service;

@Service
public class UrlShorteningService {

	public String urlShortening(String longURL) throws NoSuchAlgorithmException {
		URLShortening us = new URLShortening();
		
		String shorteningKey = us.shorten(longURL);
		return shorteningKey;
	}
	
	public String getLongURL(String shortenURL) throws NoSuchAlgorithmException {
		URLShortening us = new URLShortening();
		
		String longURL = us.getLongURL(shortenURL);
		return longURL;
	}
}
