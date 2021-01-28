package com.github.serivesmejia.binairenbt.tag;

import com.github.serivesmejia.binairenbt.exception.UnmatchingTagIdException;

import java.nio.ByteOrder;

public class TAGInt extends ByteBufferTAG<Integer> {

    public TAGInt(String name) {
        init(name, 4);
        bb.order(ByteOrder.BIG_ENDIAN);
    }

    public TAGInt(byte[] bytes) throws UnmatchingTagIdException {
        init(bytes);
    }

    @Override
    public Integer toJava() {
        bb.position(payloadPosition);
        return bb.getInt();
    }

    @Override
    public void fromJava(Integer value) {
        bb.position(0);

        bb.put(prePayloadBytes);
        bb.putInt(value);
    }

    @Override
    public int id() {
        return 3;
    }

}
