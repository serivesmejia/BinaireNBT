package com.github.serivesmejia.binairenbt.util;

public final class MathUtil {
    public static int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }
}
