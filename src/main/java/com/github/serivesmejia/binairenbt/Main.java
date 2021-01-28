package com.github.serivesmejia.binairenbt;

import com.github.serivesmejia.binairenbt.tag.TAGLong;

import java.util.Arrays;

public class Main {

    public static void main(String[] args) {

        TAGLong longNbt = new TAGLong();
        longNbt.fromJava(9999L);

        System.out.println(Arrays.toString(longNbt.toBytes()));
        System.out.println(longNbt.toJava());

        TAGLong sameLongNbt = new TAGLong();
        sameLongNbt.fromBytes(longNbt.toBytes());

        System.out.println(Arrays.toString(sameLongNbt.toBytes()));
        System.out.println(sameLongNbt.toJava());

    }

}
