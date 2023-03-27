/**
 * 对公众平台发送给公众账号的消息加解密示例代码.
 * 
 * @copyright Copyright (c) 1998-2014 Tencent Inc.
 */

// ------------------------------------------------------------------------

package com.q.ai.component.util.security;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * SHAUtil class
 *
 * 计算字符串的sha-1
 */
public class SHAUtil {

	private static final String SHA256 = "SHA-256";

	public static String getSHA256(String text){
		try {
			MessageDigest md = MessageDigest.getInstance(SHA256);
			md.update(text.getBytes());
			byte[] digest = md.digest();

			StringBuilder hexStr = new StringBuilder();
			String shaHex;
			for (byte b : digest) {
				shaHex = Integer.toHexString(b & 0xFF);
				if (shaHex.length() < 2) {
					hexStr.append(0);
				}
				hexStr.append(shaHex);
			}
			return hexStr.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}
}
