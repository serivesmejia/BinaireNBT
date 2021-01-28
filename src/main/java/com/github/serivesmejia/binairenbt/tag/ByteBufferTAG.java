package com.github.serivesmejia.binairenbt.tag;

import com.github.serivesmejia.binairenbt.Constants;
import com.github.serivesmejia.binairenbt.exception.IllegalTagFormatException;
import com.github.serivesmejia.binairenbt.exception.UnmatchingTagIdException;
import com.github.serivesmejia.binairenbt.util.ByteUtil;

import java.nio.BufferOverflowException;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public abstract class ByteBufferTAG<T> implements TAG<T>{

    protected ByteBuffer bb;

    private int payloadPosition;

    private short nameLength;
    private byte[] nameLengthBytes = new byte[2];

    private String name;
    private byte[] nameBytes;

    private byte[] headerBytes;

    private int payloadCapacity = 0;
    protected int typePayloadCapacity = -1;

    private boolean hasInit = false;

    protected final void init(String name, int payloadCapacity) throws IllegalTagFormatException {
        if(hasInit) return;
        hasInit = true;

        this.name = name;
        this.payloadCapacity = payloadCapacity;
        this.typePayloadCapacity = payloadCapacity;

        //convert the name string to an UTF_8 byte array
        nameBytes = name.getBytes(StandardCharsets.UTF_8);

        //name length shouldn't be bigger than a 2-byte short
        if(nameBytes.length > Short.MAX_VALUE) {
            throw new IllegalArgumentException("TAG name is bigger than 2-byte short max value (" + String.valueOf(Short.MAX_VALUE) + ")");
        }

        //get name length as short
        nameLength = (short) nameBytes.length;

        headerBytes = new byte[Constants.NONAME_HEADER_BYTES + nameLength];
        headerBytes[0] = idByte();

        //putting the two bytes indicating the name length
        nameLengthBytes = ByteUtil.shortToBigEndianBytes(nameLength);

        for(int i = 0 ; i < nameLengthBytes.length ; i++) {
            headerBytes[i + 1] = nameLengthBytes[i];
        }

        //putting all the name bytes
        if (nameLength >= 0)
            System.arraycopy(nameBytes, 0, headerBytes, Constants.NONAME_HEADER_BYTES, nameLength);
        else
            throw new IllegalTagFormatException("Tag name length is zero");

        //defining where the actual payload starts
        payloadPosition = Constants.NONAME_HEADER_BYTES + nameLength;

        bb = ByteBuffer.allocate(payloadCapacity + payloadPosition);
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

        //ID check to see if we're actually dealing with the correct type
        //(ID should always be the first byte)
        if(bytes[0] != idByte())
            throw new UnmatchingTagIdException("TAG ID from given bytes does not match with this tag (" + this.getClass().getSimpleName() + ")");

        nameLength = ByteUtil.bigEndianBytesToShort(new byte[] {bytes[1], bytes[2]});
        headerBytes = new byte[Constants.NONAME_HEADER_BYTES + nameLength];

        if (headerBytes.length >= 0)
            System.arraycopy(bytes, 0, headerBytes, 0, headerBytes.length);

        int payloadBytesAmount = bytes.length - headerBytes.length;

        if(payloadBytesAmount > typePayloadCapacity && typePayloadCapacity != -1) {

        }

        bb.position(0);
        bb.put(bytes);
    }

    @Override
    public void fromPayloadBytes(byte[] bytes) {
        assertInRange(bytes.length);

        bb.position(payloadPosition);
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
    public final byte[] headerBytes() {
        return headerBytes.clone();
    }

    @Override
    public final byte[] payloadBytes() {
        byte[] payloadBytes = new byte[payloadCapacity];

        if (payloadCapacity >= 0)
            System.arraycopy(bb.array(), payloadPosition, payloadBytes, 0, payloadCapacity);

        return payloadBytes;
    }

    public final byte grabPayloadByte(int byteIndex) {
        return payloadBytes()[byteIndex];
    }

    public final byte[] grabPayloadBytes(int grabAmount, int offset) {

        byte[] grabbedBytes = new byte[grabAmount];
        byte[] payloadBytes = payloadBytes();

        for(int i = 0 ; i < grabAmount ; i++) {
            grabbedBytes[i] = payloadBytes[i + offset];
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
        return bb;
    }

    protected void assertInRange(int index) {
        if(index > payloadCapacity()) throw new BufferOverflowException();
        else if(index < payloadCapacity()) throw new BufferUnderflowException();
    }

}