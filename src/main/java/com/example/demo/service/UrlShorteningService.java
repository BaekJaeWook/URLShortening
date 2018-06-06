package com.example.demo.service;

import java.security.NoSuchAlgorithmException;

public interface UrlShorteningService {
	public String shorten(String longURL) throws NoSuchAlgorithmException;

	public String getLongURL(String shorteningKey);
}
