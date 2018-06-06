package com.example.demo.dao;

public interface UrlShorteningDao {
	public int insert(String shorteningKey, String longURL);

	public String getShorteningKey(String longURL);

	public String getLongURL(String shorteningKey);

	public boolean isRegisteredLongURL(String longURL);

	public boolean isExistKey(String shorteningKey);
}
