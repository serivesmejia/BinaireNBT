package com.github.serivesmejia.binairenbt.tag;

import com.github.serivesmejia.binairenbt.Constants;

public class TAGByte extends ByteBufferTAG<Byte> {

    public TAGByte(String name) {
        init(name, Constants.TAG_BYTE_PAYLOAD_CAPACITY);
    }

    public TAGByte(byte[] bytes) {
        init(bytes, Constants.TAG_BYTE_PAYLOAD_CAPACITY);
    }

    @Override
    public Byte toJava() {
        bb.position(payloadPosition());
        return bb.get();
    }

    @Override
    public void fromJava(Byte value) {
        bb.position(payloadPosition());
        bb.put(value);
    }

}
