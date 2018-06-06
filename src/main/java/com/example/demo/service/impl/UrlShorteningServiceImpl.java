package com.example.demo.service.impl;

import java.security.NoSuchAlgorithmException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.DDDao;
import com.example.demo.dao.UrlShorteningDao;
import com.example.demo.service.UrlShorteningService;
import com.example.demo.util.Base62;
import com.example.demo.util.SHA256;

@Service
public class UrlShorteningServiceImpl implements UrlShorteningService {
	final static long MAX = 218340105584895l;
	final static int SUB_STRING_LENGTH = 12;
	final static String NOT_EXIST = "NOT_EXIST";
	final static String HASH_COLLISION = "HASH_COLLISION";
	final static String LONG_KEY_TOO_LARGE = "LONG_KEY_TOO_LARGE";

	private static Logger logger = LogManager.getLogger(UrlShorteningServiceImpl.class);

	@Autowired
	private UrlShorteningDao urlShorteningDao;

	@Autowired
	private DDDao ddDao;

	public String shorten(String longURL) throws NoSuchAlgorithmException {
		SHA256 sha256 = new SHA256();
		Base62 base62 = new Base62();

		String shorteningKey = "";

		if (urlShorteningDao.isRegisteredLongURL(longURL)) {
			return urlShorteningDao.getShorteningKey(longURL);
		}

		L: while (true) {
			String sha256Hash = sha256.encode(longURL.getBytes());

			for (int i = 0; i <= sha256Hash.length() - SUB_STRING_LENGTH; i++) {
				String digits = sha256Hash.substring(i, i + SUB_STRING_LENGTH);
				long longKey = Long.parseLong(digits, 16);

				if (longKey >= MAX) {
					logger.debug(
							ddDao.getDDMessage(LONG_KEY_TOO_LARGE) + "[URL: " + longURL + " longKey: " + longKey + "]");
					continue;
				}

				shorteningKey = base62.encode(longKey);
				if (urlShorteningDao.isExistKey(shorteningKey)) {
					logger.debug(ddDao.getDDMessage(HASH_COLLISION) + "[" + shorteningKey + "]");
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
		return ddDao.getDDMessage(NOT_EXIST);
	}
}