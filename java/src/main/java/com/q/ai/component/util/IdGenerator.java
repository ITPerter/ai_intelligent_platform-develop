package com.q.ai.component.util;

/**
 * @author HQW
 * @date 2023-05-04 21:32
 */
import java.util.Random;

import static org.apache.lucene.util.StringHelper.ID_LENGTH;

public class IdGenerator {
    public static long generateId() {
        Random random = new Random();
        long min = (long) Math.pow(10, ID_LENGTH - 1);
        long max = (long) Math.pow(10, ID_LENGTH) - 1;
        return Math.abs(random.nextLong() % (max - min) + min);
    }

    public static void main(String[] args) {
        System.out.println(generateId());
    }
}