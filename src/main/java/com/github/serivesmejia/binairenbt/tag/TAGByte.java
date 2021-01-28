package com.github.serivesmejia.binairenbt.tag;

import com.github.serivesmejia.binairenbt.Constants;

public class TAGByte extends ByteBufferTAG<Byte> {

    public TAGByte(String name) {
        init(name, Constants.TAG_BYTE_PAYLOAD_CAPACITY);
    }

    public TAGByte(byte[] bytes) {
        typePayloadCapacity = Constants.TAG_BYTE_PAYLOAD_CAPACITY;
        init(bytes);
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

    @Override
    public int id() {
        return 1;
    }

}
