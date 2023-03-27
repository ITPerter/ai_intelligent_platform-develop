package com.q.ai.component.util.security;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.Charset;
import java.util.Base64;

/**
 *
 * AES-128-CBC key需要16byte = 16*8 = 128bit
 *
 * AES/ECB/PKCS5Padding base64输出
 * *
 * 指定加密算法的字符串是AES/ECB/PKCS5Padding，也就是“算法/模式/填充方式”。
 * 而IV就和工作模式有关。常见的工作模式包括，ECB、CBC(需要IV)、PCBC(需要IV)、CFB、OFB、CTR等
 *
 * IV的作用主要是用于产生密文的第一个block，以使最终生成的密文产生差异（明文相同的情况下），使密码攻击变得更为困难，
 * 除此之外iv并无其它用途，因此iv通过随机方式产生，是简便有效的途径
 */
public class AESUtil {
    private static final String AES = "AES";
    private static final String ALGORITHM = "AES/ECB/PKCS5Padding";

    private static Cipher generateCipher(String aesKey) throws Exception {
        if (aesKey == null || aesKey.length() != 16) {
            throw new Exception("key should be as long as 16.");
        }

        return Cipher.getInstance(ALGORITHM);
    }

    public static String encrypt(String aesKey,String src) throws Exception {
        Cipher cipher = generateCipher(aesKey);
        SecretKeySpec secretKeySpec = new SecretKeySpec(aesKey.getBytes(), AES);

        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
        byte[] encrypted = cipher.doFinal(src.getBytes(Charset.forName("utf-8")));
        return Base64.getEncoder().encodeToString(encrypted);
    }

    public static String decrypt(String aesKey,String src) throws Exception {
        Cipher cipher = generateCipher(aesKey);
        SecretKeySpec secretKeySpec = new SecretKeySpec(aesKey.getBytes(), AES);

        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
        byte[] encrypted = Base64.getDecoder().decode(src);
        byte[] original = cipher.doFinal(encrypted);
        return new String(original);
    }
}
