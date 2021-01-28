package com.github.serivesmejia.binairenbt.test;

import com.github.serivesmejia.binairenbt.tag.*;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TAGTests {

    @Test
    public void TestTagByte() {
        byte lng = 9;

        TAGByte tagNbt = new TAGByte();
        tagNbt.fromJava(lng);

        TAGByte sameTagNbt = new TAGByte();
        sameTagNbt.fromBytes(tagNbt.toBytes());

        assertEquals(lng, (byte)sameTagNbt.toJava());
    }

    @Test
    public void TestTagShort() {
        short lng = 9;

        TAGShort tagNbt = new TAGShort();
        tagNbt.fromJava(lng);

        TAGShort sameTagNbt = new TAGShort();
        sameTagNbt.fromBytes(tagNbt.toBytes());

        assertEquals(lng, (short)sameTagNbt.toJava());
    }

    @Test
    public void TestTagInt() {
        int lng = 9;

        TAGInt tagNbt = new TAGInt();
        tagNbt.fromJava(lng);

        TAGInt sameTagNbt = new TAGInt();
        sameTagNbt.fromBytes(tagNbt.toBytes());

        assertEquals(lng, (int)sameTagNbt.toJava());
    }

    @Test
    public void TestTagLong() {
        long lng = 26720;

        TAGLong longNbt = new TAGLong();
        longNbt.fromJava(lng);

        TAGLong sameLongNbt = new TAGLong();
        sameLongNbt.fromBytes(longNbt.toBytes());

        assertEquals(lng, (long)sameLongNbt.toJava());
    }

    @Test
    public void TestTagFloat() {
        float lng = 9;

        TAGFloat tagNbt = new TAGFloat();
        tagNbt.fromJava(lng);

        TAGFloat sameTagNbt = new TAGFloat();
        sameTagNbt.fromBytes(tagNbt.toBytes());

        assertEquals(lng, sameTagNbt.toJava(), 0);
    }

}
