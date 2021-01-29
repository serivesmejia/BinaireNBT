package com.github.serivesmejia.binairenbt.tag;

import com.github.serivesmejia.binairenbt.Constants;
import com.github.serivesmejia.binairenbt.exception.IllegalTagFormatException;
import com.github.serivesmejia.binairenbt.exception.UnmatchingTagIdException;
import com.github.serivesmejia.binairenbt.util.ByteUtil;
import com.github.serivesmejia.binairenbt.util.nbt.NBTHeaderBuilder;

import java.nio.BufferOverflowException;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

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

        //writing the header bytes, leaving the payload for the
        //user to fill it with copyToPayload or fromJava methods.
        bb.position(0);
        bb.put(cachedHeaderBytes);

        //redefining the cache arrays if we don't have the sizes we need
        if(cachedPayloadBytes.length != payloadCapacity) {
            cachedPayloadBytes = new byte[payloadCapacity];
        }
        int headerCapacity = bb.array().length - payloadCapacity;
        if(cachedHeaderBytes.length != headerCapacity) {
            cachedHeaderBytes = new byte[headerCapacity];
        }

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

        //ID check to see if we're actually dealing with the correct type
        //(ID should always be the first byte)
        if(bytes[0] != idByte())
            throw new UnmatchingTagIdException("TAG ID "+ bytes[0] +" from given bytes does not match with this tag ( " + idByte() + " " + this.getClass().getSimpleName() + ")");

        //getting the name length from the 2nd and 3rd bytes, short
        short nameLength = ByteUtil.bigEndianBytesToShort(new byte[]{bytes[1], bytes[2]});

        //defining the payload starting position (should be exactly at the header ending)
        payloadPosition = (Constants.NONAME_HEADER_BYTES + nameLength);

        //defining the actual payload capacity
        //(if we have a >= 0 typePayloadCapacity we use that,
        //since that means we know how big this type should
        //actually be and that's our best bet, so we enforce that)
        if(typePayloadCapacity >= 0) {
            //using the type capacity
            payloadCapacity = typePayloadCapacity;
            if(bytes.length - payloadPosition > payloadCapacity) {
                throw new IllegalTagFormatException("Amount of given bytes is bigger than this tag's payload capacity");
            }
        } else {
            //using some determined capacity based on header length
            payloadCapacity = bytes.length - payloadPosition;
        }

        //putting the actual bytes into the ByteBuffer
        bb.position(0);
        bb.put(bytes);
        wrapUserByteBuffer();

        //redefining the cache arrays if we don't have the sizes we need
        if(cachedPayloadBytes.length != payloadCapacity) {
            cachedPayloadBytes = new byte[payloadCapacity];
        }

        int headerCapacity = bb.array().length - payloadCapacity;
        if(cachedHeaderBytes.length != headerCapacity) {
            cachedHeaderBytes = new byte[headerCapacity];
        }

        //grabbing name bytes from header bytes basing from the specified name length
        byte[] nameBytes = grabHeaderBytes(nameLength, Constants.NONAME_HEADER_BYTES);
        //decoding the UTF-8 bytes into an actual string
        name = new String(nameBytes, StandardCharsets.UTF_8);
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

}