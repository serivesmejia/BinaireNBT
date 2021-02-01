package com.github.serivesmejia.binairenbt.tag;

import com.github.serivesmejia.binairenbt.Constants;

import java.nio.ByteOrder;

public class TAGDouble extends ByteBufferTAG<Double> {

    public TAGDouble(String name) {
        init(name, Constants.TAG_DOUBLE_PAYLOAD_CAPACITY);
        bb.order(ByteOrder.BIG_ENDIAN);
    }

    public TAGDouble(byte[] bytes) {
        init(bytes, Constants.TAG_DOUBLE_PAYLOAD_CAPACITY);
        bb.order(ByteOrder.BIG_ENDIAN);
    }

    @Override
    public Double toJava() {
        bb.position(payloadPosition());
        return bb.getDouble();
    }

    @Override
    public void fromJava(Double value) {
        position(payloadPosition());
        bb.putDouble(value);
    }

}
