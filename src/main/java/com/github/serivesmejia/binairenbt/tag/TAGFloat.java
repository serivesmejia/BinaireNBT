package com.github.serivesmejia.binairenbt.tag;

import com.github.serivesmejia.binairenbt.Constants;

import java.nio.ByteOrder;

public class TAGFloat extends ByteBufferTAG<Float> {

    public TAGFloat(String name) {
        init(name, Constants.TAG_FLOAT_PAYLOAD_CAPACITY);
        bb.order(ByteOrder.BIG_ENDIAN);
    }

    public TAGFloat(byte[] bytes) {
        init(bytes, Constants.TAG_FLOAT_PAYLOAD_CAPACITY);
        bb.order(ByteOrder.BIG_ENDIAN);
    }

    @Override
    public Float toJava() {
        bb.position(payloadPosition());
        return bb.getFloat();
    }

    @Override
    public void fromJava(Float value) {
        bb.position(payloadPosition());
        bb.putFloat(value);
    }

}
