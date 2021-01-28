package com.github.serivesmejia.binairenbt.tag;

import java.nio.ByteOrder;

public class TAGFloat extends ByteBufferTAG<Float> {

    public TAGFloat() {
        init(4);
        bb.order(ByteOrder.BIG_ENDIAN);
    }

    @Override
    public Float toJava() {
        position(0);
        return bb.getFloat();
    }

    @Override
    public void fromJava(Float value) {
        position(0);
        bb.putFloat(value);
    }

    @Override
    public void fromBytes(byte[] bytes) {
        assertInRange(bytes.length);
        position(0);
        bb.put(bytes);
    }

    @Override
    public int getId() {
        return 5;
    }

}
