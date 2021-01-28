package com.github.serivesmejia.binairenbt.tag;

import com.github.serivesmejia.binairenbt.exception.UnmatchingTagIdException;

import java.nio.ByteOrder;

public class TAGShort extends ByteBufferTAG<Short> {

    public TAGShort(String name) {
        init(name, 2);
        bb.order(ByteOrder.BIG_ENDIAN);
    }

    public TAGShort(byte[] bytes) throws UnmatchingTagIdException {
        init(bytes);
    }

    @Override
    public Short toJava() {
        position(payloadPosition);
        return bb.getShort();
    }

    @Override
    public void fromJava(Short value) {
        position(0);

        bb.put(prePayloadBytes);
        bb.putShort(value);
    }

    @Override
    public int id() {
        return 2;
    }

}