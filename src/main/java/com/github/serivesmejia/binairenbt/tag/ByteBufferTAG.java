package com.github.serivesmejia.binairenbt.tag;

import com.github.serivesmejia.binairenbt.Constants;
import com.github.serivesmejia.binairenbt.exception.IllegalTagFormatException;
import com.github.serivesmejia.binairenbt.exception.UnmatchingTagIdException;
import com.github.serivesmejia.binairenbt.util.nbt.NBTHeaderBuilder;
import com.github.serivesmejia.binairenbt.util.nbt.NBTSimpleTagParser;

import java.nio.BufferOverflowException;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;

public abstract class ByteBufferTAG<T> implements TAG<T>{

    protected ByteBuffer bb;

    private ByteBuffer userByteBuffer = null;

    private int payloadPosition;

    private byte[] nameLengthBytes = new byte[2];

    private String name;
    private byte[] nameBytes;

    private int payloadCapacity = 0;
    private int typePayloadCapacity = -1;

    private byte[] cachedHeaderBytes = new byte[Constants.NONAME_HEADER_BYTES];
    private byte[] cachedPayloadBytes = new byte[0];

    private boolean hasInit = false;

    protected final void init(String name, int payloadCapacity) throws IllegalTagFormatException {
        if(hasInit) return;
        hasInit = true;

        this.name = name;
        this.payloadCapacity = payloadCapacity;
        this.typePayloadCapacity = payloadCapacity;

        NBTHeaderBuilder headerBuilder = new NBTHeaderBuilder();

        cachedHeaderBytes = headerBuilder.setTagId(idByte())
                            .setName(name)
                            .build();

        payloadPosition = cachedHeaderBytes.length;
        nameBytes = headerBuilder.getNameBytes();
        nameLengthBytes = headerBuilder.getNameLengthBytes();

        //allocate the bytebuffer with all the needed bytes
        bb = ByteBuffer.allocate(payloadCapacity + payloadPosition);

        //writing the header bytes, leaving the payload empty for the
        //user to fill it with copyToPayload or fromJava methods.
        bb.position(0);
        bb.put(cachedHeaderBytes);

        redefineCacheArrays();

        //wrap the user bytebuffer with the current
        //internal bytebuffer backing array
        wrapUserByteBuffer();
    }

    protected final void init(byte[] bytes, int typePayloadCapacity) throws IllegalTagFormatException {
        if(hasInit) return;
        hasInit = true;

        this.typePayloadCapacity = typePayloadCapacity;

        bb = ByteBuffer.allocate(bytes.length);
        copyBytes(bytes);
    }

    protected final void init(byte[] bytes) throws IllegalTagFormatException {
        init(bytes, -1);
    }

    @Override
    public void copyBytes(byte[] bytes) throws IllegalTagFormatException {
        //bound check (we want the exact same amount of bytes the bytebuffer can handle)
        if(bytes.length > bb.capacity()) throw new BufferOverflowException();
        else if(bytes.length < bb.capacity()) throw new BufferUnderflowException();

        NBTSimpleTagParser parser = new NBTSimpleTagParser(bytes, typePayloadCapacity);

        if(parser.getId() != id())
            throw new UnmatchingTagIdException("Tag id from given bytes (" + parser.getId() + ") does not match the id of this type (" + id() + ")");

        payloadCapacity = parser.getPayloadCapacity();
        payloadPosition = parser.getPayloadStartPosition();

        name = parser.getName();
        nameLengthBytes = parser.getNameBytesLengthInBytes();

        //wrap the given bytes after parsing
        bb = ByteBuffer.wrap(bytes);

        redefineCacheArrays();
    }

    @Override
    public void copyToPayloadBytes(byte[] bytes) {
        assertInRange(bytes.length);

        bb.position(payloadPosition);
        bb.put(bytes);
    }

    @Override
    public final void position(int pos) {
        userByteBuffer.position(pos);
    }

    @Override
    public final byte readByte() {
        return userByteBuffer.get();
    }

    @Override
    public final byte readByte(int byteIndex) {
        return userByteBuffer.get(byteIndex);
    }

    @Override
    public final byte[] bytes() {
        return bb.array();
    }

    @Override
    public final byte[] headerBytes() {
        bb.position(0);

        System.arraycopy(bb.array(), 0, cachedHeaderBytes, 0, cachedHeaderBytes.length);

        return cachedHeaderBytes;
    }

    public final byte[] cachedHeaderBytes() {
        return cachedPayloadBytes;
    }

    public final byte grabHeaderByte(int byteIndex) {
        if(byteIndex > payloadPosition) throw new ArrayIndexOutOfBoundsException(byteIndex);
        return bb.array()[byteIndex];
    }

    public final byte[] grabHeaderBytes(int grabAmount, int offset) {
        byte[] grabbedBytes = new byte[grabAmount];

        for(int i = 0 ; i < grabAmount ; i++) {
            grabbedBytes[i] = grabHeaderByte(i + offset);
        }

        return grabbedBytes;
    }

    @Override
    public final byte[] payloadBytes() {
        System.arraycopy(bb.array(), payloadPosition, cachedPayloadBytes, 0, payloadCapacity);
        return cachedPayloadBytes;
    }

    public final byte[] cachedPayloadBytes() {
        return cachedPayloadBytes;
    }

    public final byte grabPayloadByte(int byteIndex) {
        return bb.array()[byteIndex + payloadPosition];
    }

    public final byte[] grabPayloadBytes(int grabAmount, int offset) {
        byte[] grabbedBytes = new byte[grabAmount];

        for(int i = 0 ; i < grabAmount ; i++) {
            grabbedBytes[i] = grabPayloadByte(i + offset);
        }

        return grabbedBytes;
    }

    @Override
    public final int payloadCapacity() {
        return payloadCapacity;
    }

    @Override
    public final int payloadPosition() {
        return payloadPosition;
    }

    @Override
    public final String name() {
        return name;
    }

    @Override
    public final byte[] nameBytes() {
        return nameBytes.clone();
    }

    @Override
    public byte[] nameLengthBytes() {
        return nameLengthBytes.clone();
    }

    public final ByteBuffer byteBuffer() {
        wrapUserByteBuffer();
        return userByteBuffer;
    }

    protected void assertInRange(int index) {
        if(index > payloadCapacity()) throw new BufferOverflowException();
        else if(index < payloadCapacity()) throw new BufferUnderflowException();
    }

    private void wrapUserByteBuffer() {
        if(bb != null) userByteBuffer = ByteBuffer.wrap(bb.array());
    }

    private void redefineCacheArrays() {
        //redefining the cache arrays if we don't have the sizes we need
        if(cachedPayloadBytes.length != payloadCapacity) {
            cachedPayloadBytes = new byte[payloadCapacity];
        }
        int headerCapacity = bb.array().length - payloadCapacity;
        if(cachedHeaderBytes.length != headerCapacity) {
            cachedHeaderBytes = new byte[headerCapacity];
        }
    }

}