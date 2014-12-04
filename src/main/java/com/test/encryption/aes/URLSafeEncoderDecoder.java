package com.test.encryption.aes;

import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.KeySpec;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class URLSafeEncoderDecoder {

	private static final Logger logger = LoggerFactory
			.getLogger(URLSafeEncoderDecoder.class);

	Cipher ecipher;
	Cipher dcipher;

	byte[] salt = { (byte) 0xA9, (byte) 0x9B, (byte) 0xC8, (byte) 0x32,
			(byte) 0x56, (byte) 0x35, (byte) 0xE3, (byte) 0x03 };

	int iterationCount = 3;

	private static String passPhrase = "4A144BEBF7E5E7B7DCF26491AE";

	public URLSafeEncoderDecoder() {

		try {

			KeySpec keySpec = new PBEKeySpec(passPhrase.toCharArray(), salt,
					iterationCount);
			SecretKey key = SecretKeyFactory.getInstance("PBEWithMD5AndDES")
					.generateSecret(keySpec);

			ecipher = Cipher.getInstance(key.getAlgorithm());
			dcipher = Cipher.getInstance(key.getAlgorithm());

			AlgorithmParameterSpec paramSpec = new PBEParameterSpec(salt,
					iterationCount);

			ecipher.init(Cipher.ENCRYPT_MODE, key, paramSpec);
			dcipher.init(Cipher.DECRYPT_MODE, key, paramSpec);

		} catch (java.security.InvalidAlgorithmParameterException e) {
		} catch (java.security.spec.InvalidKeySpecException e) {
		} catch (javax.crypto.NoSuchPaddingException e) {
		} catch (java.security.NoSuchAlgorithmException e) {
		} catch (java.security.InvalidKeyException e) {
		}
	}

	public String encrypt(String str) {

		try {
			return Base64.encodeBase64URLSafeString(str.getBytes());
		} catch (Exception e) {
			logger.debug("Error encrypting transaction id " + str, e);
		}

		return null;
	}

	public String decrypt(String str) {

		try {

			return new String(Base64.decodeBase64(str.getBytes()));

		} catch (Exception e) {
			logger.debug("Error decrypting transaction id " + str, e);
		}

		return null;
	}

}
