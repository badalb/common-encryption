package com.test.encryption.aes;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.test.encryption.aes.HuAESUtil;

public class AesUtilTest {
    private static final String IV = "F27D5C9927726BCEFE7510B1BDD3D137";
    private static final String SALT = "3FF2EC019C627B945225DEBAD71A01B6985FE84C95A70EB132882F88C0A59A55";
    private static final String PLAIN_TEXT = "hi there";
    private static final int KEY_SIZE = 128;
    private static final int ITERATION_COUNT = 10000;
    private static final String PASSPHRASE = "the quick brown fox jumps over the lazy dog";
    private static final String CIPHER_TEXT = "pKLY1RV+0uDKOK6k22yHsg==";
    
    @Test
    public void testEncrypt() {
        HuAESUtil util = new HuAESUtil(KEY_SIZE, ITERATION_COUNT);
        String encrypt = util.encrypt(SALT, IV, PASSPHRASE, PLAIN_TEXT);
        assertEquals(CIPHER_TEXT, encrypt);
    }
    
    @Test
    public void testDecrypt() {
        HuAESUtil util = new HuAESUtil(KEY_SIZE, ITERATION_COUNT);
        String decrypt = util.decrypt(SALT, IV, PASSPHRASE, CIPHER_TEXT);
        assertEquals(PLAIN_TEXT, decrypt);
    }
}