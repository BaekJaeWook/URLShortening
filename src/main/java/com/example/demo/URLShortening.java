package com.example.demo;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

public class URLShortening {
	final static long MAX = 218340105584895l;
	final static int SUB_STRING_LENGTH = 12;
	final static String ERR_CODE_1 = "NOT_EXIST";

	Map<String, String> dataStorage1 = new HashMap<String, String>();
	Map<String, String> dataStorage2 = new HashMap<String, String>();

	public String shorten(String longURL) throws NoSuchAlgorithmException {
		SHA256 sha256 = new SHA256();

		String sha256Hash = sha256.encode(longURL.getBytes());
		Base62 base62 = new Base62();

		String shortenURL = "";

		if (dataStorage2.containsKey(longURL)) {
			shortenURL = dataStorage2.get(longURL);
			System.out.println("Already registerd URL");
			return shortenURL;
		}

		for (int i = 0; i < sha256Hash.length() - SUB_STRING_LENGTH; i++) {
			String digits = sha256Hash.substring(i, i + SUB_STRING_LENGTH);
			long longKey = Long.parseLong(digits, 16);

			if (longKey >= MAX) {
				System.out.println(longKey + " is too large");
				continue;
			}
			shortenURL = base62.encode(longKey);
			if (dataStorage1.containsKey(shortenURL)) {
				System.out.println("Hash Collision");
				continue;
			} else {
				dataStorage1.put(shortenURL, longURL);
				dataStorage2.put(longURL, shortenURL);

				return shortenURL;
			}
		}
		return shortenURL;
	}

	public String getLongURL(String shortenURL) {
		if (dataStorage1.containsKey(shortenURL)) {
			return dataStorage1.get(shortenURL);
		}
		return ERR_CODE_1;
	}
}