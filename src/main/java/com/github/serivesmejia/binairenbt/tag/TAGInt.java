package com.github.serivesmejia.binairenbt.tag;

import com.github.serivesmejia.binairenbt.Constants;

import java.nio.ByteOrder;

public class TAGInt extends ByteBufferTAG<Integer> {

    public TAGInt(String name) {
        init(name, Constants.TAG_INT_PAYLOAD_CAPACITY);
        bb.order(ByteOrder.BIG_ENDIAN);
    }

    public TAGInt(byte[] bytes) {
        init(bytes, Constants.TAG_INT_PAYLOAD_CAPACITY);
        bb.order(ByteOrder.BIG_ENDIAN);
    }

    @Override
    public Integer toJava() {
        bb.position(payloadPosition());
        return bb.getInt();
    }

    @Override
    public void fromJava(Integer value) {
        bb.position(payloadPosition());
        bb.putInt(value);
    }

    @Override
    public int id() {
        return 3;
    }

}
