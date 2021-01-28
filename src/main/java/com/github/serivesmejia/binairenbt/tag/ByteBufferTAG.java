package com.github.serivesmejia.binairenbt.tag;

import com.github.serivesmejia.binairenbt.Constants;
import com.github.serivesmejia.binairenbt.exception.UnmatchingTagIdException;

import java.nio.BufferOverflowException;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;

public abstract class ByteBufferTAG<T> implements TAG<T>{

    protected ByteBuffer bb;

    protected int payloadPosition;
    protected short nameLength;

    protected String name;

    protected byte[] nameBytes;
    protected byte[] prePayloadBytes;

    protected int capacity = 0;

    private boolean hasInit = false;

    protected final void init(String name, int capacity) {
        if(hasInit) return;
        hasInit = true;

        this.name = name;
        this.capacity = capacity;

        nameBytes = name.getBytes(StandardCharsets.UTF_8);
        nameLength = (short) nameBytes.length;

        prePayloadBytes = new byte[Constants.PRE_PAYLOAD_BYTES + nameLength];
        prePayloadBytes[0] = idAsByte();

        ByteBuffer nameLengthBB = ByteBuffer.allocate(2);
        nameLengthBB.order(ByteOrder.BIG_ENDIAN);
        nameLengthBB.putShort(nameLength);

        prePayloadBytes[1] = nameLengthBB.get(0);
        prePayloadBytes[2] = nameLengthBB.get(1);

        if (nameLength >= 0)
            System.arraycopy(nameBytes, 0, prePayloadBytes, Constants.PRE_PAYLOAD_BYTES, nameLength);

        payloadPosition = Constants.PRE_PAYLOAD_BYTES + nameLength;

        bb = ByteBuffer.allocate(capacity + payloadPosition);
    }

    protected final void init(byte[] bytes) throws UnmatchingTagIdException {
        if(hasInit) return;
        hasInit = true;

        bb = ByteBuffer.allocate(bytes.length);
        fromBytes(bytes);
    }

    @Override
    public void fromBytes(byte[] bytes) throws UnmatchingTagIdException {
        if(bytes.length > bb.capacity()) throw new BufferOverflowException();
        else if(bytes.length < bb.capacity()) throw new BufferUnderflowException();

        int id = bytes[0];

        if(id != id())
            throw new UnmatchingTagIdException("TAG ID from given bytes does not match with this tag (" + this.getClass().getSimpleName() + ")");

        ByteBuffer nameLengthBB = ByteBuffer.allocate(2);
        nameLengthBB.order(ByteOrder.BIG_ENDIAN);
        nameLengthBB.put(bytes[1]);
        nameLengthBB.put(bytes[2]);

        nameLength = nameLengthBB.getShort();

        prePayloadBytes = new byte[Constants.PRE_PAYLOAD_BYTES + nameLength];

        if (prePayloadBytes.length >= 0)
            System.arraycopy(bytes, 0, prePayloadBytes, 0, prePayloadBytes.length);

        bb.position(0);
        bb.put(bytes);
    }

    @Override
    public void fromPayloadBytes(byte[] bytes) {
        assertInRange(bytes.length);

        bb.position(0);
        bb.put(prePayloadBytes);
        bb.put(bytes);
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
    public final byte[] bytes() {
        return bb.array().clone();
    }

    @Override
    public final byte[] payloadBytes() {
        byte[] payloadBytes = new byte[capacity];

        if (capacity >= 0)
            System.arraycopy(bb.array(), payloadPosition, payloadBytes, 0, capacity);

        return payloadBytes;
    }

    @Override
    public final int payloadSize() {
        return capacity;
    }

    public final byte idAsByte() {
        return (byte) id();
    }

    public final ByteBuffer byteBuffer() {
        return bb;
    }

    protected void assertInRange(int index) {
        if(index > payloadSize()) throw new BufferOverflowException();
        else if(index < payloadSize()) throw new BufferUnderflowException();
    }

}
