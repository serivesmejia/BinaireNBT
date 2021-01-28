package com.github.serivesmejia.binairenbt.test;

import com.github.serivesmejia.binairenbt.exception.IllegalTagFormatException;
import com.github.serivesmejia.binairenbt.tag.*;
import org.junit.Test;

import static org.junit.Assert.*;

public class SimpleTAGTests {

    @Test
    public void TestTagByte() throws IllegalTagFormatException {
        byte lng = 9;

        TAGByte tagNbt = new TAGByte("byteTag");
        tagNbt.fromJava(lng);

        TAGByte sameTagNbt = new TAGByte(tagNbt.bytes());

        assertEquals(lng, (byte)sameTagNbt.toJava());
        assertEquals(tagNbt.name(), sameTagNbt.name());
    }

    @Test
    public void TestTagShort() throws IllegalTagFormatException {
        short lng = 9;

        TAGShort tagNbt = new TAGShort("shortTag");
        tagNbt.fromJava(lng);

        TAGShort sameTagNbt = new TAGShort(tagNbt.bytes());

        assertEquals(lng, (short)sameTagNbt.toJava());
        assertEquals(tagNbt.name(), sameTagNbt.name());
    }

    @Test
    public void TestTagInt() throws IllegalTagFormatException {
        int lng = 9;

        TAGInt tagNbt = new TAGInt("intTag");
        tagNbt.fromJava(lng);

        TAGInt sameTagNbt = new TAGInt(tagNbt.bytes());

        assertEquals(lng, (int)sameTagNbt.toJava());
        assertEquals(tagNbt.name(), sameTagNbt.name());
    }

    @Test
    public void TestTagLong() throws IllegalTagFormatException {
        long lng = 26720;

        TAGLong tagNbt = new TAGLong("longTag");
        tagNbt.fromJava(lng);

        TAGLong sameTagNbt = new TAGLong(tagNbt.bytes());

        assertEquals(lng, (long)sameTagNbt.toJava());
        assertEquals(tagNbt.name(), sameTagNbt.name());
    }

    @Test
    public void TestTagFloat() throws IllegalTagFormatException {
        float lng = 9.2f;

        TAGFloat tagNbt = new TAGFloat("floatTag");
        tagNbt.fromJava(lng);

        TAGFloat sameTagNbt = new TAGFloat(tagNbt.bytes());

        assertEquals(lng, sameTagNbt.toJava(), 0);
        assertEquals(tagNbt.name(), sameTagNbt.name());
    }

    @Test
    public void TestTagDouble() throws IllegalTagFormatException {
        double lng = 23.5;

        TAGDouble tagNbt = new TAGDouble("doubleTag");
        tagNbt.fromJava(lng);

        TAGDouble sameTagNbt = new TAGDouble(tagNbt.bytes());

        assertEquals(lng, sameTagNbt.toJava(), 0);
        assertEquals(tagNbt.name(), sameTagNbt.name());
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
