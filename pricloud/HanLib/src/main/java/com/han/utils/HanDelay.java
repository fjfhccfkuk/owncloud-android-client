package com.han.utils;

/**
 * Created by hsx on 17-6-9.
 */

public class HanDelay {
    private static boolean __delay(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e){
            return true;
        }

        return  false;
    }

    public static boolean delay_ms(int ms)
    {
        return __delay(ms);
    }

    public static boolean delay_s(int s) {
        return __delay(s * 1000);
    }
}
