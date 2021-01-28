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

    private int payloadCapacity = 0;
    private int typePayloadCapacity = -1;

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

        byte[]  headerBytes = new byte[Constants.NONAME_HEADER_BYTES + nameLength];
        headerBytes[0] = idByte();

        //putting the two bytes indicating the name length
        nameLengthBytes = ByteUtil.shortToBigEndianBytes(nameLength);

        System.arraycopy(nameLengthBytes, 0, headerBytes, 1, nameLengthBytes.length);

        //putting all the name bytes
        if (nameLength >= 0)
            System.arraycopy(nameBytes, 0, headerBytes, Constants.NONAME_HEADER_BYTES, nameLength);
        else
            throw new IllegalTagFormatException("Tag name length is zero");

        //defining where the actual payload starts
        payloadPosition = Constants.NONAME_HEADER_BYTES + nameLength;

        bb = ByteBuffer.allocate(payloadCapacity + payloadPosition);
        bb.position(0);
        bb.put(headerBytes);
    }

    protected final void init(byte[] bytes, int typePayloadCapacity) throws UnmatchingTagIdException {
        if(hasInit) return;
        hasInit = true;

        this.typePayloadCapacity = typePayloadCapacity;

        bb = ByteBuffer.allocate(bytes.length);
        fromBytes(bytes);
    }

    protected final void init(byte[] bytes) throws UnmatchingTagIdException {
        init(bytes, -1);
    }

    @Override
    public void fromBytes(byte[] bytes) throws UnmatchingTagIdException {
        //bound check (we want the exact same amount of bytes the bytebuffer can handle)
        if(bytes.length > bb.capacity()) throw new BufferOverflowException();
        else if(bytes.length < bb.capacity()) throw new BufferUnderflowException();

        //ID check to see if we're actually dealing with the correct type
        //(ID should always be the first byte)
        if(bytes[0] != idByte())
            throw new UnmatchingTagIdException("TAG ID "+ bytes[0] +" from given bytes does not match with this tag ( " + idByte() + " " + this.getClass().getSimpleName() + ")");

        //getting the name length from the 2nd and 3rd bytes, short
        nameLength = ByteUtil.bigEndianBytesToShort(new byte[] {bytes[1], bytes[2]});

        //defining the payload starting position (should be exactly at the header ending)
        payloadPosition = (Constants.NONAME_HEADER_BYTES + nameLength);

        //defining the actual payload capacity
        //(if we have a >= 0 typePayloadCapacity we use that,
        //since that means we have how big this type should
        //actually be and that's our best bet and we enforce that)
        if(typePayloadCapacity > -1) {
            //using the type capacity
            payloadCapacity = typePayloadCapacity;

            if(payloadCapacity > bytes.length - payloadPosition) {
                throw new IllegalTagFormatException("Amount of given bytes is bigger than this tag's payload capacity");
            }
        } else {
            //using some determined capacity based on header length
            payloadCapacity = bytes.length - payloadPosition;
        }

        //putting the actual bytes into the ByteBuffer
        bb.position(0);
        bb.put(bytes);

        //grabbing name bytes from header bytes basing from the specified name length
        byte[] nameBytes = grabHeaderBytes(nameLength, Constants.NONAME_HEADER_BYTES);
        //decoding the UTF-8 bytes into an actual string
        name = new String(nameBytes, StandardCharsets.UTF_8);
    }

    @Override
    public void copyToPayload(byte[] bytes) {
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
        bb.position(0);

        byte[] headerBytes = new byte[bb.array().length - payloadCapacity];
        System.arraycopy(bb.array(), 0, headerBytes, 0, headerBytes.length);

        return headerBytes;
    }

    public final byte grabHeaderByte(int byteIndex) {
        return headerBytes()[byteIndex];
    }

    public final byte[] grabHeaderBytes(int grabAmount, int offset) {
        byte[] grabbedBytes = new byte[grabAmount];
        byte[] headerBytes = headerBytes();

        System.arraycopy(headerBytes, offset, grabbedBytes, 0, grabbedBytes.length);

        return grabbedBytes;
    }

    @Override
    public final byte[] payloadBytes() {
        byte[] payloadBytes = new byte[payloadCapacity];

        System.arraycopy(bb.array(), payloadPosition, payloadBytes, 0, payloadCapacity);

        return payloadBytes;
    }

    public final byte grabPayloadByte(int byteIndex) {
        return payloadBytes()[byteIndex];
    }

    public final byte[] grabPayloadBytes(int grabAmount, int offset) {

        byte[] grabbedBytes = new byte[grabAmount];
        byte[] payloadBytes = payloadBytes();

        if (grabAmount >= 0)
            System.arraycopy(payloadBytes, offset, grabbedBytes, 0, grabAmount);

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