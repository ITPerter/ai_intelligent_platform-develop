package com.q.ai.component.util.security;

import java.util.Random;

/**
 *
 */
public class RandomUtil {

    /**
     * 随机生成size位字符串
     * @param size
     * @return
     */
    public static String getRandomStr(int size) {
        String base = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < size; i++) {
            int number = random.nextInt(base.length());
            stringBuilder.append(base.charAt(number));
        }
        return stringBuilder.toString();
    }

}
