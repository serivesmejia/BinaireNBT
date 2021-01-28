package com.github.serivesmejia.binairenbt.tag;

import java.nio.ByteOrder;

public class TAGShort extends ByteBufferTAG<Short> {

    public TAGShort() {
        init(2);
        bb.order(ByteOrder.BIG_ENDIAN);
    }

    @Override
    public Short toJava() {
        position(0);
        return bb.getShort();
    }

    @Override
    public void fromJava(Short value) {
        position(0);
        bb.putShort(value);
    }

    @Override
    public void fromBytes(byte[] bytes) {
        assertInRange(bytes.length);

        position(0);
        bb.put(bytes);
    }

    @Override
    public int getId() {
        return 2;
    }

}
