package com.github.serivesmejia.binairenbt.test;

import com.github.serivesmejia.binairenbt.tag.*;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public class TAGTests {

    @Test
    public void TestTagByte() {
        byte lng = 9;

        TAGByte tagNbt = new TAGByte("byteTag");
        tagNbt.fromJava(lng);

        TAGByte sameTagNbt = new TAGByte("sameByteTag");
        sameTagNbt.fromPayloadBytes(tagNbt.payloadBytes());

        assertEquals(lng, (byte)sameTagNbt.toJava());
    }

    @Test
    public void TestTagShort() {
        short lng = 9;

        TAGShort tagNbt = new TAGShort("shortTag");
        tagNbt.fromJava(lng);

        TAGShort sameTagNbt = new TAGShort("sameShortTag");
        sameTagNbt.fromPayloadBytes(tagNbt.payloadBytes());

        assertEquals(lng, (short)sameTagNbt.toJava());
    }

    @Test
    public void TestTagInt() {
        int lng = 9;

        TAGInt tagNbt = new TAGInt("intTag");
        tagNbt.fromJava(lng);
        System.out.println(Arrays.toString(tagNbt.bytes()));

        TAGInt sameTagNbt = new TAGInt("sameIntTag");
        sameTagNbt.fromPayloadBytes(tagNbt.payloadBytes());

        assertEquals(lng, (int)sameTagNbt.toJava());
    }

    @Test
    public void TestTagLong() {
        long lng = 26720;

        TAGLong longNbt = new TAGLong("longTag");
        longNbt.fromJava(lng);

        TAGLong sameLongNbt = new TAGLong("sameLongTag");
        sameLongNbt.fromPayloadBytes(longNbt.payloadBytes());

        assertEquals(lng, (long)sameLongNbt.toJava());
    }

    @Test
    public void TestTagFloat() {
        float lng = 9.2f;

        TAGFloat tagNbt = new TAGFloat("floatTag");
        tagNbt.fromJava(lng);

        TAGFloat sameTagNbt = new TAGFloat("sameFloatTag");
        sameTagNbt.fromPayloadBytes(tagNbt.payloadBytes());

        assertEquals(lng, sameTagNbt.toJava(), 0);
    }

    @Test
    public void TestTagDouble() {
        double lng = 23.5;

        TAGDouble tagNbt = new TAGDouble("doubleTag");
        tagNbt.fromJava(lng);

        TAGDouble sameTagNbt = new TAGDouble("sameDoubleTag");
        sameTagNbt.fromPayloadBytes(tagNbt.payloadBytes());

        assertEquals(lng, sameTagNbt.toJava(), 0);
    }

}
