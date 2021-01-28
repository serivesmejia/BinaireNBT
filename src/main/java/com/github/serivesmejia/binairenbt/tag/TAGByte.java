package com.github.serivesmejia.binairenbt.tag;

public class TAGByte extends ByteBufferTAG<Byte> {

    public TAGByte() {
        init(1);
    }

    @Override
    public Byte toJava() {
        bb.position(0);
        return bb.get();
    }

    @Override
    public void fromJava(Byte value) {
        bb.position(0);
        bb.put(value);
    }

    @Override
    public void fromBytes(byte[] bytes) {
        assertInRange(bytes.length);
        bb.position(0);
        bb.put(bytes);
    }

    @Override
    public int getId() {
        return 1;
    }

}
