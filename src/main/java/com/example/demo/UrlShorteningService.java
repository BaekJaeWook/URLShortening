package com.example.demo;

import java.security.NoSuchAlgorithmException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UrlShorteningService {

	final static long MAX = 218340105584895l;
	final static int SUB_STRING_LENGTH = 12;

	final static String ERR_CODE_1 = "NOT_EXIST";

	@Autowired
	private UrlShorteningDao urlShorteningDao;

	public String shorten(String longURL) throws NoSuchAlgorithmException {
		SHA256 sha256 = new SHA256();
		Base62 base62 = new Base62();

		String shorteningKey = "";

		if (urlShorteningDao.isRegisteredLongURL(longURL)) {
			return urlShorteningDao.getShorteningKey(longURL);
		}

		L: while (true) {
			String sha256Hash = sha256.encode(longURL.getBytes());

			for (int i = 0; i < sha256Hash.length() - SUB_STRING_LENGTH; i++) {
				String digits = sha256Hash.substring(i, i + SUB_STRING_LENGTH);
				long longKey = Long.parseLong(digits, 16);

				if (longKey >= MAX) {
					System.out.println(longKey + " is too large");
					continue;
				}

				shorteningKey = base62.encode(longKey);
				if (urlShorteningDao.isExistKey(shorteningKey)) {
					System.out.println("Hash Collision");
				} else {
					urlShorteningDao.insert(shorteningKey, longURL);
					break L;
				}
			}
			sha256Hash = sha256.encode(sha256Hash.getBytes());
		}
		return shorteningKey;
	}

	public String getLongURL(String shorteningKey) {
		if (urlShorteningDao.isExistKey(shorteningKey)) {
			return urlShorteningDao.getLongURL(shorteningKey);
		}
		return ERR_CODE_1;
	}
}