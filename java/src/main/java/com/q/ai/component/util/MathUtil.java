package com.q.ai.component.util;

/**
 *
 */
public class MathUtil {


    /**
     * 使用换底公式计算log a(b)= log e(a)/loge(b)
     *
     * @param a
     * @param b
     * @return
     */
    public static double log(double a, double b) {
        return Math.log(a) / Math.log(b);
    }

}
