package com.github.serivesmejia.binairenbt.tag;

import java.nio.ByteOrder;

public class TAGInt extends ByteBufferTAG<Integer> {

    public TAGInt() {
        init(4);
        bb.order(ByteOrder.BIG_ENDIAN);
    }

    @Override
    public Integer toJava() {
        bb.position(0);
        return bb.getInt();
    }

    @Override
    public void fromJava(Integer value) {
        bb.position(0);
        bb.putInt(value);
    }

    @Override
    public void fromBytes(byte[] bytes) {
        assertInRange(bytes.length);

        bb.position(0);
        bb.put(bytes);
    }

    @Override
    public int getId() {
        return 3;
    }

}
