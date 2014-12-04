package com.test.encryption.hmac;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.commons.codec.binary.Hex;

import com.test.encryption.util.EncryptionConstant;

public class HMACRequestSignatureImpl {

	private static final Logger logger = LoggerFactory
			.getLogger(HMACRequestSignatureImpl.class);

	
	public String generateHMAC(String data, String hexEncodedKey) {
		String result = "";
		try {
			byte[] keyBytes = hexEncodedKey.getBytes();
			SecretKeySpec signingKey = new SecretKeySpec(keyBytes,
					EncryptionConstant.HMAC_SHA1_ALGORITHM);
			Mac mac = Mac.getInstance(EncryptionConstant.HMAC_SHA1_ALGORITHM);
			mac.init(signingKey);
			byte[] rawHmac = mac.doFinal(data.getBytes());
			
			byte[] hexBytes = new Hex().encode(rawHmac);
			result = new String(hexBytes, "UTF-8");
		} catch (Exception e) {
			logger.error("error getting signature", e.getMessage());
		}
		return result;
	}

	
	public String generateSecretKey() {
		try {
			SecureRandom prng = SecureRandom.getInstance(EncryptionConstant.SHA1PRNG_ALGORITHM);
			String randomNum = new Integer(prng.nextInt()).toString();
			MessageDigest sha = MessageDigest.getInstance(EncryptionConstant.SHA1_ALGORITHM);
			byte[] result = sha.digest(randomNum.getBytes());
			return hexEncode(result);
		} catch (NoSuchAlgorithmException ex) {
			logger.info("error generating key" + ex.getMessage());
		}
		return null;
	}

	private String hexEncode(byte[] aInput) {
		StringBuilder result = new StringBuilder();
		char[] digits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'a', 'b', 'c', 'd', 'e', 'f' };
		for (int idx = 0; idx < aInput.length; ++idx) {
			byte b = aInput[idx];
			result.append(digits[(b & 0xf0) >> 4]);
			result.append(digits[b & 0x0f]);
		}
		return result.toString();
	}
}
