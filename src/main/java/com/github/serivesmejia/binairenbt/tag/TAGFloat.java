package com.github.serivesmejia.binairenbt.tag;

import com.github.serivesmejia.binairenbt.exception.UnmatchingTagIdException;

import java.nio.ByteOrder;

public class TAGFloat extends ByteBufferTAG<Float> {

    public TAGFloat(String name) {
        init(name, 4);
        bb.order(ByteOrder.BIG_ENDIAN);
    }

    public TAGFloat(byte[] bytes) throws UnmatchingTagIdException {
        init(bytes);
    }

    @Override
    public Float toJava() {
        position(payloadPosition);
        return bb.getFloat();
    }

    @Override
    public void fromJava(Float value) {
        position(0);

        bb.put(prePayloadBytes);
        bb.putFloat(value);
    }

    @Override
    public int id() {
        return 5;
    }

}
