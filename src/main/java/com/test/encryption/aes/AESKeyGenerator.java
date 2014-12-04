package com.test.encryption.aes;

import java.security.NoSuchAlgorithmException;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import com.test.encryption.util.EncryptionConstant;

public class AESKeyGenerator {

	public String generateKey() throws NoSuchAlgorithmException {
		byte[] bytes = new byte[16];

		SecretKey key = null;
		KeyGenerator kgen = KeyGenerator
				.getInstance(EncryptionConstant.AES_ALGORITHM);
		kgen.init(256);
		key = kgen.generateKey();
		bytes = key.getEncoded();
		return hexEncode(bytes);
	}

	public String hexEncode(byte[] aInput) {
		int length = 0;
		StringBuilder result = new StringBuilder();
		char[] digits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'a', 'b', 'c', 'd', 'e', 'f' };
		for (int idx = 0; idx < aInput.length && length < 16; ++idx) {
			byte b = aInput[idx];
			result.append(digits[(b & 0xf0) >> 4]);
			result.append(digits[b & 0x0f]);
			length++;
		}
		return result.toString();
	}

}
