package com.github.serivesmejia.binairenbt.tag;

import com.github.serivesmejia.binairenbt.Constants;

import java.nio.ByteOrder;

public class TAGShort extends ByteBufferTAG<Short> {

    public TAGShort(String name) {
        init(name, Constants.TAG_SHORT_PAYLOAD_CAPACITY);
        bb.order(ByteOrder.BIG_ENDIAN);
    }

    public TAGShort(byte[] bytes)  {
        typePayloadCapacity = Constants.TAG_SHORT_PAYLOAD_CAPACITY;
        init(bytes);
    }

    @Override
    public Short toJava() {
        position(payloadPosition());
        return bb.getShort();
    }

    @Override
    public void fromJava(Short value) {
        position(payloadPosition());
        bb.putShort(value);
    }

    @Override
    public int id() {
        return 2;
    }

}