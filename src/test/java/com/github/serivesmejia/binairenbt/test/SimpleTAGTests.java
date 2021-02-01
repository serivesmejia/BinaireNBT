package com.github.serivesmejia.binairenbt.test;

import com.github.serivesmejia.binairenbt.exception.IllegalTagFormatException;
import com.github.serivesmejia.binairenbt.tag.*;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

/*
 * These unit test perform a simple check with all the TAG types:
 * 1) We have our original "java value" (int, double, byte... primitive)
 * 2) We pass it onto a corresponding tag with the fromJava method
 * 3) Then,we assert if the toJava() method return value equals the original java value
 * 4) We create a new tag copying the old tag's bytes onto it, and we assert that:
 *    a) The toJava() method return value equals to the original "java value"
 *    b) That the old tag and the new tag names are the same (which means we
 *       performed the internal copyBytes action correctly.)
 *
 * No need to document the specific test since they're all basically the same.
 */
public class SimpleTAGTests {

    @Test
    public void TestTagByte() throws IllegalTagFormatException {
        byte lng = 9;

        TAGByte tagNbt = new TAGByte("byteTag");
        tagNbt.fromJava(lng);

        assertEquals((byte)tagNbt.toJava(), lng);

        TAGByte sameTagNbt = new TAGByte(tagNbt.bytes());

        assertEquals(lng, (byte)sameTagNbt.toJava());
        assertEquals(tagNbt.name(), sameTagNbt.name());
    }

    @Test
    public void TestTagShort() throws IllegalTagFormatException {
        short lng = 9;

        TAGShort tagNbt = new TAGShort("shortTag");
        tagNbt.fromJava(lng);

        assertEquals((short)tagNbt.toJava(), lng);

        TAGShort sameTagNbt = new TAGShort(tagNbt.bytes());

        assertEquals(lng, (short)sameTagNbt.toJava());
        assertEquals(tagNbt.name(), sameTagNbt.name());
    }

    @Test
    public void TestTagInt() throws IllegalTagFormatException {
        int lng = 9;

        TAGInt tagNbt = new TAGInt("intTag");
        tagNbt.fromJava(lng);

        assertEquals((int)tagNbt.toJava(), lng);

        TAGInt sameTagNbt = new TAGInt(tagNbt.bytes());

        assertEquals(lng, (int)sameTagNbt.toJava());
        assertEquals(tagNbt.name(), sameTagNbt.name());
    }

    @Test
    public void TestTagLong() throws IllegalTagFormatException {
        long lng = 267205353534634634L;

        TAGLong tagNbt = new TAGLong("longTag");
        tagNbt.fromJava(lng);

        assertEquals((long)tagNbt.toJava(), lng);

        TAGLong sameTagNbt = new TAGLong(tagNbt.bytes());

        assertEquals(lng, (long)sameTagNbt.toJava());
        assertEquals(tagNbt.name(), sameTagNbt.name());
    }

    @Test
    public void TestTagFloat() throws IllegalTagFormatException {
        float lng = 9.2f;

        TAGFloat tagNbt = new TAGFloat("floatTag");
        tagNbt.fromJava(lng);

        assertEquals(tagNbt.toJava(), lng, 0);

        TAGFloat sameTagNbt = new TAGFloat(tagNbt.bytes());

        assertEquals(lng, sameTagNbt.toJava(), 0);
        assertEquals(tagNbt.name(), sameTagNbt.name());
    }

    @Test
    public void TestTagDouble() throws IllegalTagFormatException {
        double lng = 23.5;

        TAGDouble tagNbt = new TAGDouble("doubleTag");
        tagNbt.fromJava(lng);

        assertEquals(tagNbt.toJava(), lng, 0);

        TAGDouble sameTagNbt = new TAGDouble(tagNbt.bytes());

        assertEquals(lng, sameTagNbt.toJava(), 0);
        assertEquals(tagNbt.name(), sameTagNbt.name());
    }

    @Test
    public void TestTagByteArray() throws IllegalTagFormatException {
        Byte[] lng = new Byte[] {9, 2, 3, 1};

        TAGByteArray tagNbt = new TAGByteArray("byteArrayTag", 4);
        tagNbt.fromJava(lng);

        assertArrayEquals(tagNbt.toJava(), lng);

        TAGByteArray sameTagNbt = new TAGByteArray(tagNbt.bytes());

        assertArrayEquals(lng, sameTagNbt.toJava());
        assertEquals(tagNbt.name(), sameTagNbt.name());
    }

    @Test
    public void TestTagString() throws IllegalTagFormatException {
        String lng = "mmAAii";

        TAGString tagNbt = new TAGString("stringTag", lng);

        assertEquals(tagNbt.toJava(), lng);

        TAGString sameTagNbt = new TAGString(tagNbt.bytes());

        assertEquals(lng, sameTagNbt.toJava());
        assertEquals(tagNbt.name(), sameTagNbt.name());
    }

    @Test
    public void TestTagIntArray() throws IllegalTagFormatException {
        Integer[] lng = new Integer[] {9, 2, 3, 1};

        TAGIntArray tagNbt = new TAGIntArray("intArrayTag", 4);
        tagNbt.fromJava(lng);

        assertArrayEquals(tagNbt.toJava(), lng);

        TAGIntArray sameTagNbt = new TAGIntArray(tagNbt.bytes());

        assertArrayEquals(lng, sameTagNbt.toJava());
        assertEquals(tagNbt.name(), sameTagNbt.name());
    }

    @Test
    public void TestTagLongArray() throws IllegalTagFormatException {
        Long[] lng = new Long[] {9537357454632L, 53453646334L, 575646456L, 1213215990L};

        TAGLongArray tagNbt = new TAGLongArray("longArrayTag", 4);
        tagNbt.fromJava(lng);

        assertArrayEquals(tagNbt.toJava(), lng);

        TAGLongArray sameTagNbt = new TAGLongArray(tagNbt.bytes());

        assertArrayEquals(lng, sameTagNbt.toJava());
        assertEquals(tagNbt.name(), sameTagNbt.name());
    }

}
