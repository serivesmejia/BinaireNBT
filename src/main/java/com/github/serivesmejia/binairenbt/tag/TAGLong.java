package com.github.serivesmejia.binairenbt.tag;

import java.nio.ByteOrder;

public class TAGLong extends ByteBufferTAG<Long> {

    public TAGLong() {
        init(8);
        bb.order(ByteOrder.BIG_ENDIAN);
    }

    @Override
    public Long toJava() {
        bb.position(0);
        return bb.getLong();
    }

    @Override
    public void fromJava(Long value) {
        position(0);
        bb.putLong(value);
    }

    @Override
    public void fromBytes(byte[] bytes) {
        assertInRange(bytes.length);
        position(0);
        bb.put(bytes);
    }

    @Override
    public int getId() {
        return 4;
    }

}
