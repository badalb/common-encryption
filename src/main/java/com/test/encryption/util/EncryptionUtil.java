package com.test.encryption.util;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EncryptionUtil {

	private static final Logger logger = LoggerFactory
			.getLogger(EncryptionUtil.class);

	public static boolean isEmpty(String str) {
		if (str == null || str.length() <= 0) {
			return true;
		}
		return false;
	}

	public static byte[] convertHexStr2Bytes(String hex) {

		byte[] bArray = new BigInteger("10" + hex, 16).toByteArray();

		byte[] ret = new byte[bArray.length - 1];
		for (int i = 0; i < ret.length; i++) {
			ret[i] = bArray[i + 1];
		}
		return ret;
	}

	public static String convertHex2Str(String hex) {

		String str = null;
		try {
			str = new String(convertHexStr2Bytes(hex), "ASCII");
		} catch (UnsupportedEncodingException e) {
			logger.error("Error converting Hex to string");
		}
		return str;
	}

	public static String convertString2Hex(String str) {
		StringBuffer hexString = new StringBuffer();
		byte[] b;
		try {
			b = str.getBytes("ASCII");
			for (int i = 0; i < b.length; i++) {
				hexString.append(Integer.toHexString(0xFF & b[i]));
			}
		} catch (UnsupportedEncodingException e) {
			logger.error("Error converting string to bytes");
		}
		return hexString.toString();
	}

	public static boolean isNotNull(Object object) {
		return (object != null) ? Boolean.TRUE : Boolean.FALSE;
	}

	public static boolean isNull(Object object) {
		return (object == null) ? Boolean.TRUE : Boolean.FALSE;
	}

	public static boolean validateEmptyParams(String... args) {
		for (String s : args) {
			if (s == null || (s != null && s.equalsIgnoreCase(""))) {
				return false;
			}
		}
		return true;
	}

	public static <T> boolean isNotEmpty(List<T> objList) {
		return (isNotNull(objList) && (objList.size() > 0));
	}

	public static <T> boolean isNotEmpty(Set<T> objList) {
		return (isNotNull(objList) && (objList.size() > 0));
	}

	public static <T> boolean isEmpty(List<T> objList) {
		return (isNull(objList) || (objList.size() == 0));
	}

	public static String handleNull(String str) {
		if (str == null) {
			str = "";
		}
		return str;
	}

	public static String kvPair(String k, String v) {
		return String.format("%s=%s", new Object[] { k, v });
	}

	public static String formatTwoDecimalPlaces(double value) {
		return new DecimalFormat("#0.00").format(value);
	}

}
