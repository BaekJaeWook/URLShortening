package com.example.demo;

import java.util.Date;

public class DD {
	private String id;
	private String message;
	private Date create_date;
	private Date modify_date;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Date getCreate_date() {
		return create_date;
	}

	public void setCreate_date(Date create_date) {
		this.create_date = create_date;
	}

	public Date getModify_date() {
		return modify_date;
	}

	public void setModify_date(Date modify_date) {
		this.modify_date = modify_date;
	}

	@Override
	public String toString() {
		return "DD [id=" + id + ", message=" + message + ", create_date=" + create_date + ", modify_date=" + modify_date
				+ "]";
	}
}
