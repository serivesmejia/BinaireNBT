package com.github.serivesmejia.binairenbt.tag;

import com.github.serivesmejia.binairenbt.Constants;

import java.nio.ByteOrder;

public class TAGFloat extends ByteBufferTAG<Float> {

    public TAGFloat(String name) {
        init(name, Constants.TAG_FLOAT_PAYLOAD_CAPACITY);
        bb.order(ByteOrder.BIG_ENDIAN);
    }

    public TAGFloat(byte[] bytes) {
        typePayloadCapacity = Constants.TAG_FLOAT_PAYLOAD_CAPACITY;
        init(bytes);
    }

    @Override
    public Float toJava() {
        position(payloadPosition());
        return bb.getFloat();
    }

    @Override
    public void fromJava(Float value) {
        position(payloadPosition());
        bb.putFloat(value);
    }

    @Override
    public int id() {
        return 5;
    }

}
