package com.example.demo;

import java.security.NoSuchAlgorithmException;

import org.springframework.stereotype.Service;

@Service
public class UrlShorteningService {
	URLShortening us = new URLShortening();

	public String urlShortening(String longURL) throws NoSuchAlgorithmException {
		String shorteningKey = us.shorten(longURL);
		return shorteningKey;
	}

	public String getLongURL(String shortenURL) throws NoSuchAlgorithmException {
		String longURL = us.getLongURL(shortenURL);
		return longURL;
	}
}
