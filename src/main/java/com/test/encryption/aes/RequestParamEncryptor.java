package com.test.encryption.aes;

import java.security.GeneralSecurityException;
import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

import com.test.encryption.util.EncryptionConstant;
import com.test.encryption.util.EncryptionUtil;

public class RequestParamEncryptor {

	private static final Key key = new SecretKeySpec(new byte[] { 'T', 'x',
			'R', 'n', 'I', 's', 'p', 'c', 'z', 'e', 'm', 'a', 't', 'u', 'c',
			'o' }, EncryptionConstant.AES_ALGORITHM);
	private final Base64 encoder = new Base64(76, "\n".getBytes(), false);

	public String encrypt(String valueToEnc) {
		try {
			Cipher c = Cipher.getInstance(EncryptionConstant.AES_ALGORITHM);
			c.init(Cipher.ENCRYPT_MODE, key);
			byte[] encValue = c.doFinal(valueToEnc.getBytes());
			return encoder.encodeToString(encValue).trim();
		} catch (GeneralSecurityException gsx) {
			throw new RuntimeException(gsx);
		}
	}

	public String decrypt(String encryptedValue) {
		try {
			Cipher c = Cipher.getInstance(EncryptionConstant.AES_ALGORITHM);
			c.init(Cipher.DECRYPT_MODE, key);
			byte[] decordedValue = encoder.decode(encryptedValue);

			return new String(c.doFinal(decordedValue));
		} catch (GeneralSecurityException gsx) {
			throw new RuntimeException(gsx);
		}
	}

	public String encryptToHex(String plainText) {
		return EncryptionUtil.convertString2Hex(encrypt(plainText));
	}

	public String decryptFromHex(String hextText) {
		return decrypt(EncryptionUtil.convertHex2Str(hextText));
	}

}
