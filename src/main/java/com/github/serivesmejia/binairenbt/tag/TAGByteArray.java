package com.github.serivesmejia.binairenbt.tag;

public class TAGByteArray extends ByteBufferTAG<Byte[]>{

    @Override
    public Byte[] toJava() {
        return new Byte[0];
    }

    @Override
    public void fromJava(Byte[] value) {

    }

    @Override
    public int id() {
        return 7;
    }

}
