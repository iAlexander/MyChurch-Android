package com.d2.pcu.fragments.cabinet.donate.pay;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class StringEncryption {

	 private static String convertToHex(byte[] data) {
	        StringBuilder buf = new StringBuilder();
	        for (byte b : data) {
	            int halfbyte = (b >>> 4) & 0x0F;
	            int two_halfs = 0;
	            do {
	                buf.append((0 <= halfbyte) && (halfbyte <= 9) ? (char) ('0' + halfbyte) : (char) ('a' + (halfbyte - 10)));
	                halfbyte = b & 0x0F;
	            } while (two_halfs++ < 1);
	        }
	        return buf.toString();
	    }

	    public static String SHA1String(String text) throws NoSuchAlgorithmException {
	        MessageDigest md = MessageDigest.getInstance("SHA-1");
	        md.update(text.getBytes(StandardCharsets.UTF_8), 0, text.length());
	        byte[] sha1hash = md.digest();
	        return convertToHex(sha1hash);
	    }

	public static byte[] SHA1(String text) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("SHA-1");
		md.update(text.getBytes(StandardCharsets.UTF_8), 0, text.length());
		return md.digest();
	}
}