package com.github.serivesmejia.binairenbt.tag;

import java.nio.BufferOverflowException;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;

public abstract class ByteBufferTAG<T> implements TAG<T>{

    protected ByteBuffer bb;

    protected final void init(int capacity) {
        bb = ByteBuffer.allocate(capacity);
    }

    @Override
    public final void position(int pos) {
        bb.position(pos);
    }

    @Override
    public final byte readByte() {
        return bb.get();
    }

    @Override
    public final byte readByte(int byteIndex) {
        return bb.get(byteIndex);
    }

    @Override
    public final byte[] toBytes() {
        return bb.array().clone();
    }

    @Override
    public final int payloadSize() {
        return bb.capacity();
    }

    protected void assertInRange(int index) {
        if(index > payloadSize()) throw new BufferOverflowException();
        else if(index < payloadSize()) throw new BufferUnderflowException();
    }

}
