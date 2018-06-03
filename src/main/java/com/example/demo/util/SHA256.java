package com.example.demo.util;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SHA256 {
	final static String ALGORITHM = "SHA-256";
	
	public String encode(byte[] param) throws NoSuchAlgorithmException {
		MessageDigest md;
		String endcodedData = null;
		try {
			md = MessageDigest.getInstance(ALGORITHM);

			byte[] hash = md.digest(param);
			StringBuffer hexString = new StringBuffer();

			for (int i = 0; i < hash.length; i++) {
				String hex = Integer.toHexString(0xff & hash[i]);
				if (hex.length() == 1) {
					hexString.append('0');
				}
				hexString.append(hex);
			}
			endcodedData = hexString.reverse().toString();
		} catch (NoSuchAlgorithmException e) {
			throw new NoSuchAlgorithmException(ALGORITHM + " is not a proper algorithm.");
		}
		return endcodedData;
	}
}