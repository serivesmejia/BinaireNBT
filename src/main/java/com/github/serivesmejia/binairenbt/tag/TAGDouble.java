package com.github.serivesmejia.binairenbt.tag;

import com.github.serivesmejia.binairenbt.exception.UnmatchingTagIdException;

public class TAGDouble extends ByteBufferTAG<Double> {

    public TAGDouble(String name) {
        init(name, 8);
    }

    public TAGDouble(byte[] bytes) throws UnmatchingTagIdException {
        init(bytes);
    }

    @Override
    public Double toJava() {
        position(payloadPosition);
        return bb.getDouble();
    }

    @Override
    public void fromJava(Double value) {
        position(0);

        bb.put(prePayloadBytes);
        bb.putDouble(value);
    }

    @Override
    public int id() {
        return 8;
    }

}
