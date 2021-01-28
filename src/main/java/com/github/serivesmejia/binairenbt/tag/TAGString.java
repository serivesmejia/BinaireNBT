package com.github.serivesmejia.binairenbt.tag;

import java.nio.BufferOverflowException;
import java.nio.BufferUnderflowException;
import java.nio.charset.StandardCharsets;

public class TAGString extends ByteBufferTAG<String> {

    private TAGShort shortStringBytesLength = new TAGShort("stringLength");

    public TAGString(String name, String string) {
        this(name, (short) string.getBytes(StandardCharsets.UTF_8).length);
        fromJava(string);
    }

    public TAGString(String name, short stringBytesLength) {
        shortStringBytesLength.fromJava(stringBytesLength);

        init(name, shortStringBytesLength.payloadCapacity() + stringBytesLength);
        bb.position(payloadPosition());
        bb.put(shortStringBytesLength.payloadBytes());
    }

    public TAGString(byte[] bytes)  {
        init(bytes);
        //grab the string length from the first 4 bytes of this tag's payload
        shortStringBytesLength.copyToPayloadBytes(grabPayloadBytes(shortStringBytesLength.payloadCapacity(), 0));
    }

    @Override
    public String toJava() {
        //grab the actual utf-8 string bytes starting from where the 4 string length bytes end
        byte[] stringBytes = grabPayloadBytes(stringBytesLength(), shortStringBytesLength.payloadCapacity());
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

        bb.position(payloadPosition() + shortStringBytesLength.payloadCapacity());
        bb.put(stringBytes);
    }

    public final short stringBytesLength() {
        return shortStringBytesLength.toJava();
    }

    @Override
    public int id() {
        return 8;
    }
}
