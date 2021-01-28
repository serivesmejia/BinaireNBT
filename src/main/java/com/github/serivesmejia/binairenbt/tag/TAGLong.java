package com.github.serivesmejia.binairenbt.tag;

import com.github.serivesmejia.binairenbt.Constants;

import java.nio.ByteOrder;

public class TAGLong extends ByteBufferTAG<Long> {

    public TAGLong(String name) {
        init(name, Constants.TAG_LONG_PAYLOAD_CAPACITY);
        bb.order(ByteOrder.BIG_ENDIAN);
    }

    public TAGLong(byte[] bytes) {
        init(bytes, Constants.TAG_LONG_PAYLOAD_CAPACITY);
    }

    @Override
    public Long toJava() {
        bb.position(payloadPosition());
        return bb.getLong();
    }

    @Override
    public void fromJava(Long value) {
        position(payloadPosition());
        bb.putLong(value);
    }

    @Override
    public int id() {
        return 4;
    }

}
