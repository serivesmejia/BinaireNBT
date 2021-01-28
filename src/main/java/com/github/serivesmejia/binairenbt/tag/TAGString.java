package com.github.serivesmejia.binairenbt.tag;

import com.github.serivesmejia.binairenbt.Constants;
import com.github.serivesmejia.binairenbt.util.ByteUtil;

import java.nio.BufferOverflowException;
import java.nio.BufferUnderflowException;
import java.nio.charset.StandardCharsets;

public class TAGString extends ByteBufferTAG<String> {

    private byte[] bytesStringBytesLength = new byte[Constants.TAG_SHORT_PAYLOAD_CAPACITY];

    public TAGString(String name, String string) {
        this(name, (short) string.getBytes(StandardCharsets.UTF_8).length);
        fromJava(string);
    }

    public TAGString(String name, short stringBytesLength) {
        bytesStringBytesLength = ByteUtil.unsignedShortToBigEndianBytes(stringBytesLength);

        init(name, bytesStringBytesLength.length + stringBytesLength);
        bb.position(payloadPosition());
        bb.put(bytesStringBytesLength);
    }

    public TAGString(byte[] bytes)  {
        init(bytes);
        //grab the string length from the first 4 bytes of this tag's payload
        bytesStringBytesLength = grabPayloadBytes(bytesStringBytesLength.length, 0);
    }

    @Override
    public String toJava() {
        //grab the actual utf-8 string bytes starting from where the 4 string length bytes end
        byte[] stringBytes = grabPayloadBytes(stringBytesLength(), bytesStringBytesLength.length);
        return new String(stringBytes, StandardCharsets.UTF_8);
    }

    @Override
    public void fromJava(String value) {
        byte[] stringBytes = value.getBytes(StandardCharsets.UTF_8);

        //range check, we want the exact same
        //amount of bytes this TAGString can hold
        if(stringBytes.length > stringBytesLength())
            throw new BufferOverflowException();
        else if(stringBytes.length < stringBytesLength())
            throw new BufferUnderflowException();

        bb.position(payloadPosition() +  bytesStringBytesLength.length);
        bb.put(stringBytes);
    }

    public final short stringBytesLength() {
        return ByteUtil.bigEndianBytesToUnsignedShort(bytesStringBytesLength);
    }

    @Override
    public int id() {
        return 8;
    }
}
