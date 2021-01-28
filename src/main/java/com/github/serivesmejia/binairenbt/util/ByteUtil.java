package com.github.serivesmejia.binairenbt.util;

import com.github.serivesmejia.binairenbt.Constants;

import java.nio.BufferOverflowException;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class ByteUtil {

    public static final short UNSIGNED_SHORT_MIN = 0;
    public static final char UNSIGNED_SHORT_MAX = 65535;

    public static byte[] shortToBigEndianBytes(short value) {
        ByteBuffer bb = ByteBuffer.allocate(Constants.TAG_SHORT_PAYLOAD_CAPACITY);
        bb.order(ByteOrder.BIG_ENDIAN);
        bb.putShort(value);

        return bb.array();
    }

    public static byte[] unsignedShortToBigEndianBytes(int value) {
        ByteBuffer bb = ByteBuffer.allocate(Constants.TAG_SHORT_PAYLOAD_CAPACITY);
        bb.order(ByteOrder.BIG_ENDIAN);

        if(value > UNSIGNED_SHORT_MAX)
            throw new IllegalArgumentException("Given unsigned short is bigger than the max value (" + UNSIGNED_SHORT_MAX + ")");
        else if(value < UNSIGNED_SHORT_MIN)
            throw new IllegalArgumentException("Given unsigned short is smaller than the min value (" + UNSIGNED_SHORT_MAX + ")");

        //a bit hacky to cast a short to a char
        //(since they're unsigned) but it works
        bb.putChar((char)value);

        return bb.array();
    }

    public static short bigEndianBytesToShort(byte[] bytes) {
        ByteBuffer bb = ByteBuffer.wrap(bytes);
        bb.order(ByteOrder.BIG_ENDIAN);

        if(bytes.length > Constants.TAG_SHORT_PAYLOAD_CAPACITY)
            throw new BufferOverflowException();
        else if(bytes.length < Constants.TAG_SHORT_PAYLOAD_CAPACITY)
            throw new BufferUnderflowException();

        return bb.getShort();
    }

    public static short bigEndianBytesToUnsignedShort(byte[] bytes) {
        ByteBuffer bb = ByteBuffer.wrap(bytes);
        bb.order(ByteOrder.BIG_ENDIAN);

        //a bit hacky again
        return (short) bb.getChar();
    }

}
