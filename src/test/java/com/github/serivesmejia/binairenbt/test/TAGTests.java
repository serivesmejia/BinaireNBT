package com.github.serivesmejia.binairenbt.test;

import com.github.serivesmejia.binairenbt.exception.IllegalTagFormatException;
import com.github.serivesmejia.binairenbt.tag.*;
import org.junit.Test;

import static org.junit.Assert.*;

public class TAGTests {

    @Test
    public void TestTagByte() throws IllegalTagFormatException {
        byte lng = 9;

        TAGByte tagNbt = new TAGByte("byteTag");
        tagNbt.fromJava(lng);

        TAGByte sameTagNbt = new TAGByte("sameByteTag");
        sameTagNbt.copyToPayload(tagNbt.payloadBytes());

        assertEquals(lng, (byte)sameTagNbt.toJava());
    }

    @Test
    public void TestTagShort() throws IllegalTagFormatException {
        short lng = 9;

        TAGShort tagNbt = new TAGShort("shortTag");
        tagNbt.fromJava(lng);

        TAGShort sameTagNbt = new TAGShort("sameShortTag");
        sameTagNbt.copyToPayload(tagNbt.payloadBytes());

        assertEquals(lng, (short)sameTagNbt.toJava());
    }

    @Test
    public void TestTagInt() throws IllegalTagFormatException {
        int lng = 9;

        TAGInt tagNbt = new TAGInt("intTag");
        tagNbt.fromJava(lng);

        TAGInt sameTagNbt = new TAGInt("sameIntTag");
        sameTagNbt.copyToPayload(tagNbt.payloadBytes());

        assertEquals(lng, (int)sameTagNbt.toJava());
    }

    @Test
    public void TestTagLong() throws IllegalTagFormatException {
        long lng = 26720;

        TAGLong longNbt = new TAGLong("longTag");
        longNbt.fromJava(lng);

        TAGLong sameLongNbt = new TAGLong("sameLongTag");
        sameLongNbt.copyToPayload(longNbt.payloadBytes());

        assertEquals(lng, (long)sameLongNbt.toJava());
    }

    @Test
    public void TestTagFloat() throws IllegalTagFormatException {
        float lng = 9.2f;

        TAGFloat tagNbt = new TAGFloat("floatTag");
        tagNbt.fromJava(lng);

        TAGFloat sameTagNbt = new TAGFloat("sameFloatTag");
        sameTagNbt.copyToPayload(tagNbt.payloadBytes());

        assertEquals(lng, sameTagNbt.toJava(), 0);
    }

    @Test
    public void TestTagDouble() throws IllegalTagFormatException {
        double lng = 23.5;

        TAGDouble tagNbt = new TAGDouble("doubleTag");
        tagNbt.fromJava(lng);

        TAGDouble sameTagNbt = new TAGDouble("sameDoubleTag");
        sameTagNbt.copyToPayload(tagNbt.payloadBytes());

        assertEquals(lng, sameTagNbt.toJava(), 0);
    }

    @Test
    public void TestTagByteArray() throws IllegalTagFormatException {
        Byte[] lng = new Byte[] {9, 2, 3, 1};

        TAGByteArray tagNbt = new TAGByteArray("byteArrayTag", 4);
        tagNbt.fromJava(lng);

        TAGByteArray sameTagNbt = new TAGByteArray(tagNbt.bytes());

        assertArrayEquals(lng, sameTagNbt.toJava());
        assertEquals(tagNbt.name(), sameTagNbt.name());
    }

}
