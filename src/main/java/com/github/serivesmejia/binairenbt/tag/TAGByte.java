package com.github.serivesmejia.binairenbt.tag;

import com.github.serivesmejia.binairenbt.exception.UnmatchingTagIdException;

public class TAGByte extends ByteBufferTAG<Byte> {

    public TAGByte(String name) {
        init(name, 1);
    }

    public TAGByte(byte[] bytes) throws UnmatchingTagIdException {
        init(bytes);
    }

    @Override
    public Byte toJava() {
        bb.position(payloadPosition);
        return bb.get();
    }

    @Override
    public void fromJava(Byte value) {
        bb.position(0);
        bb.put(prePayloadBytes);
        bb.put(value);
    }

    @Override
    public int id() {
        return 1;
    }

}
