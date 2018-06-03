package com.example.demo.util;

public class XssFilter {
	public String XssReplace(String param) {

		param = param.replaceAll("\"", "&quot;");
		param = param.replaceAll("'", "&apos;");
		param = param.replaceAll("<", "&lt;");
		param = param.replaceAll(">", "&gt;");
		param = param.replaceAll("\r", "<br>");
		param = param.replaceAll("\n", "<p>");

		return param;
	}
}
