package com.github.serivesmejia.binairenbt.tag;

import com.github.serivesmejia.binairenbt.Constants;

public class TAGDouble extends ByteBufferTAG<Double> {

    public TAGDouble(String name) {
        init(name, Constants.TAG_DOUBLE_PAYLOAD_CAPACITY);
    }

    public TAGDouble(byte[] bytes) {
        typePayloadCapacity = Constants.TAG_DOUBLE_PAYLOAD_CAPACITY;
        init(bytes);
    }

    @Override
    public Double toJava() {
        position(payloadPosition());
        return bb.getDouble();
    }

    @Override
    public void fromJava(Double value) {
        position(payloadPosition());
        bb.putDouble(value);
    }

    @Override
    public int id() {
        return 8;
    }

}
