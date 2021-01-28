package com.github.serivesmejia.binairenbt.tag;

import com.github.serivesmejia.binairenbt.exception.UnmatchingTagIdException;

import java.nio.ByteOrder;

public class TAGLong extends ByteBufferTAG<Long> {

    public TAGLong(String name) {
        init(name, 8);
        bb.order(ByteOrder.BIG_ENDIAN);
    }

    public TAGLong(byte[] bytes) throws UnmatchingTagIdException {
        init(bytes);
    }

    @Override
    public Long toJava() {
        bb.position(payloadPosition);
        return bb.getLong();
    }

    @Override
    public void fromJava(Long value) {
        position(0);

        bb.put(prePayloadBytes);
        bb.putLong(value);
    }

    @Override
    public int id() {
        return 4;
    }

}
