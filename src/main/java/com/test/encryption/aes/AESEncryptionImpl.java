package com.test.encryption.aes;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Hex;

import com.test.encryption.util.EncryptionUtil;

public class AESEncryptionImpl {
	private static final String AES_ALORITHM = "AES";

	private SecretKeySpec keySpec;

	private SecretKeySpec encDecKeySpec;

	private SecretKeySpec getKeySpec(String keyPath)
			throws GeneralSecurityException {
		if (encDecKeySpec == null) {
			if (keySpec == null) {
				keySpec = getDataEncKeySpec(keyPath);
			}
			String encKey = decryptDataEncKey(keyPath, keySpec);
			byte[] bytes = new byte[16];
			bytes = EncryptionUtil.convertHexStr2Bytes(encKey);
			encDecKeySpec = new SecretKeySpec(bytes, AES_ALORITHM);
		}
		return encDecKeySpec;
	}

	private SecretKeySpec getDataEncKeySpec(String keyPath)
			throws GeneralSecurityException {
		if (keySpec == null) {
			try {
				byte[] bytes = new byte[16];
				File f = new File(keyPath);
				if (f.exists()) {
					new FileInputStream(f).read(bytes);
				}
				keySpec = new SecretKeySpec(bytes, AES_ALORITHM);
			} catch (IOException ex) {
				throw new GeneralSecurityException(ex.getMessage());
			}
		}
		return keySpec;
	}

	public String encryptDataEncKey(String text, String keyPath)
			throws GeneralSecurityException {
		Cipher cipher = Cipher.getInstance(AES_ALORITHM);
		cipher.init(Cipher.ENCRYPT_MODE, getDataEncKeySpec(keyPath));
		byte[] encodedText = cipher.doFinal(text.getBytes());
		return new String(Hex.encodeHexString(encodedText));
	}

	public String decryptDataEncKey(String encodedText, SecretKeySpec keySpec)
			throws GeneralSecurityException {
		Cipher cipher = Cipher.getInstance(AES_ALORITHM);
		cipher.init(Cipher.DECRYPT_MODE, keySpec);
		byte[] decodedText = cipher.doFinal(EncryptionUtil
				.convertHexStr2Bytes(encodedText));
		return new String(decodedText);
	}

	public String encrypt(String text, String keyFilePath, String salt)
			throws GeneralSecurityException {
		Cipher cipher = Cipher.getInstance(AES_ALORITHM);
		cipher.init(Cipher.ENCRYPT_MODE, getKeySpec(keyFilePath));
		byte[] encodedText = cipher.doFinal(text.getBytes());
		return new String(Hex.encodeHexString(encodedText));
	}

	public String decrypt(String encodedText, String keyFilePath, String salt)
			throws GeneralSecurityException {
		Cipher cipher = Cipher.getInstance(AES_ALORITHM);
		cipher.init(Cipher.DECRYPT_MODE, getKeySpec(keyFilePath));
		byte[] decodedText = cipher.doFinal(EncryptionUtil
				.convertHexStr2Bytes(encodedText));
		return new String(decodedText);
	}

}
