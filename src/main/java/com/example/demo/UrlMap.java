package com.example.demo;

import java.util.Date;

public class UrlMap {
	private String shorteningKey;
	private String longUrl;
	private Date createDate;
	private Date expiryDate;

	public String getShorteningKey() {
		return shorteningKey;
	}

	public void setShorteningKey(String shorteningKey) {
		this.shorteningKey = shorteningKey;
	}

	public String getLongUrl() {
		return longUrl;
	}

	public void setLongUrl(String longUrl) {
		this.longUrl = longUrl;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}

	@Override
	public String toString() {
		return "UrlMap [shorteningKey=" + shorteningKey + ", longUrl=" + longUrl + ", createDate=" + createDate
				+ ", expiryDate=" + expiryDate + "]";
	}
}