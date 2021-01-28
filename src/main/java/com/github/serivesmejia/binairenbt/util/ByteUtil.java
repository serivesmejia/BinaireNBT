package com.github.serivesmejia.binairenbt.util;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class ByteUtil {

    public static byte[] shortToBigEndianBytes(short value) {
        ByteBuffer bb = ByteBuffer.allocate(4);
        bb.order(ByteOrder.BIG_ENDIAN);
        bb.putShort(value);

        return bb.array();
    }

    public static short bigEndianBytesToShort(byte[] bytes) {
        ByteBuffer bb = ByteBuffer.wrap(bytes);
        bb.order(ByteOrder.BIG_ENDIAN);

        return bb.getShort();
    }

}
