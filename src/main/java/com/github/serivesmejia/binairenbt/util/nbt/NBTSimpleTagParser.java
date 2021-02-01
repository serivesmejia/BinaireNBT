package com.github.serivesmejia.binairenbt.util.nbt;

import com.github.serivesmejia.binairenbt.Constants;
import com.github.serivesmejia.binairenbt.exception.IllegalTagFormatException;
import com.github.serivesmejia.binairenbt.exception.UnknownTagIdException;
import com.github.serivesmejia.binairenbt.tag.TAG;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

/**
 * This class serves as a simple tag parser, a common task in the library.<br/>
 *
 */
public class NBTSimpleTagParser {

    private final ByteBuffer bb;

    private String cachedName = null;
    private int cachedNameBytesLength = -1;

    private int maxPayloadCapacity = -1;

    /**
     * Constructor for an NBTSimpleTagParser
     * @param tagBytes the bytes of this tag we'll parse
     */
    public NBTSimpleTagParser(byte[] tagBytes) {
        this(tagBytes, -1);
    }

    /**
     * Constructor for an NBTSimpleTagParser
     * @param tagBytes the bytes of this tag we'll parse
     * @param maxPayloadCapacity the max allowed payload capacity, a number <= 0 will ignore this limit
     */
    public NBTSimpleTagParser(byte[] tagBytes, int maxPayloadCapacity) {
        bb = ByteBuffer.wrap(tagBytes);
        this.maxPayloadCapacity = maxPayloadCapacity;
        validate();
    }

    private void validate() {
        getName();
        getId();

        if(getNameBytesLength() < 1)
            throw new IllegalTagFormatException("Tag name length should be bigger than 0");

        if(maxPayloadCapacity > 0 && getPayloadCapacity() > maxPayloadCapacity)
            throw new IllegalTagFormatException("Tag's payload capacity is bigger than the max allowed one (" + maxPayloadCapacity + ")");

        if(getTagType() == TAG.Type.UNKNOWN)
            throw new UnknownTagIdException("Tag ID " + getId() + " does not match any existent one");
    }

    /**
     * Get this tag's id
     * @return the tag's id as a byte
     */
    public byte getId() {
        return bb.get(0);
    }

    /**
     * Get the tag's type based on the id
     * @return the tag's type
     */
    public TAG.Type getTagType() {
        return TAG.Type.fromId(getId());
    }

    /**
     * Get this tag's name bytes length
     * @return tag's name UTF-8 bytes length
     */
    public int getNameBytesLength() {
        if(cachedNameBytesLength <= 0) {
            bb.position(1);
            cachedNameBytesLength = bb.getChar();
        }

        return cachedNameBytesLength;
    }

    /**
     * Get this tag's name bytes length as a byte array of a 2-byte unsigned short
     * @return tag's name UTF-8 bytes length in the aforementioned format
     */
    public byte[] getNameBytesLengthInBytes() {
        return grabHeaderBytes(2, 1);
    }

    /**
     * Get the name of this tag
     * @return the name
     */
    public String getName() {
        if(cachedName == null) {
            byte[] nameBytes = grabHeaderBytes(getNameBytesLength(), Constants.NONAME_HEADER_BYTES);
            cachedName = new String(nameBytes, StandardCharsets.UTF_8);
        }
        return cachedName;
    }

    /**
     * Get the position where the header ends and the payload starts
     * @return the aforementioned position
     */
    public int getPayloadStartPosition() {
        return Constants.NONAME_HEADER_BYTES + getNameBytesLength();
    }

    /**
     * Get the payload capacity
     * @return payload capacity in bytes
     */
    public int getPayloadCapacity() {
        return bb.capacity() - getPayloadStartPosition();
    }

    /**
     * Grab a byte from the header
     * @param byteIndex the index to grab the byte from
     * @return the grabbed header byte
     * @throws ArrayIndexOutOfBoundsException if the given index is outside the header range
     */
    public final byte grabHeaderByte(int byteIndex) {
        if(byteIndex > getPayloadStartPosition()) throw new ArrayIndexOutOfBoundsException(byteIndex);
        return bb.array()[byteIndex];
    }

    /**
     * Grabs X header bytes starting from a given offset
     * @param grabAmount the amount of bytes we'll grab from the header
     * @param offset grabbing start position
     * @return the grabbed bytes
     */
    public final byte[] grabHeaderBytes(int grabAmount, int offset) {
        byte[] grabbedBytes = new byte[grabAmount];

        for(int i = 0 ; i < grabAmount ; i++) {
            grabbedBytes[i] = grabHeaderByte(i + offset);
        }

        return grabbedBytes;
    }

    /**
     * Grab a byte from the payload
     * @param byteIndex the index to grab the byte from
     * @return the grabbed payload byte
     * @throws ArrayIndexOutOfBoundsException if the given index is outside the payload range
     */
    public final byte grabPayloadByte(int byteIndex) {
        return bb.array()[byteIndex + getPayloadStartPosition()];
    }

    /**
     * Grabs X payload bytes starting from a given offset
     * @param grabAmount the amount of bytes we'll grab from the payload
     * @param offset grabbing start position
     * @return the grabbed bytes
     */
    public final byte[] grabPayloadBytes(int grabAmount, int offset) {
        byte[] grabbedBytes = new byte[grabAmount];

        for(int i = 0 ; i < grabAmount ; i++) {
            grabbedBytes[i] = grabPayloadByte(i + offset);
        }

        return grabbedBytes;
    }

}
