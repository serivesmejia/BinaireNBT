package com.github.serivesmejia.binairenbt.util.nbt;

import com.github.serivesmejia.binairenbt.Constants;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public class NBTHeaderBuilder {

    private ByteBuffer bb;
    private byte tagId = -1;

    private byte[] nameBytes;
    private byte[] nameLengthBytes = new byte[Constants.TAG_SHORT_PAYLOAD_CAPACITY];

    /**
     * Sets the tag id for this header
     * @param tagId the tag id
     * @return this
     */
    public NBTHeaderBuilder setTagId(byte tagId) {
        if(bb != null) {
            bb.position(0);
            bb.put(tagId);
        }
        this.tagId = tagId;

        return this;
    }

    /**
     * Sets the name for this tag's header
     * @param name the name
     * @throws IllegalStateException if the name has already been set
     * @throws IllegalArgumentException if the name length in bytes is bigger than an unsigned short max value
     * @return this
     */
    public NBTHeaderBuilder setName(String name) {

        if(bb != null) throw new IllegalStateException("Name has already been set");

        nameBytes = name.getBytes(StandardCharsets.UTF_8);

        if(nameBytes.length > Character.MAX_VALUE) {
            throw new IllegalArgumentException("Name length in bytes cannot be bigger than " + Character.MAX_VALUE);
        } else if(nameBytes.length <= 0) {
            throw new IllegalArgumentException("Name cannot be empty!");
        }

        char nameLengthBytes = (char)nameBytes.length;

        bb = ByteBuffer.allocate(Constants.NONAME_HEADER_BYTES + nameBytes.length);

        bb.put(tagId);
        bb.putChar(nameLengthBytes);
        bb.put(nameBytes);

        this.nameLengthBytes[0] = bb.get(1);
        this.nameLengthBytes[1] = bb.get(2);

        return this;
    }

    /**
     * Builds this tag header
     * @throws IllegalStateException if the tag's name or id hasn't been defined
     * @return the bytes of this header
     */
    public byte[] build() {
        if(tagId < 0)
            throw new IllegalStateException("Cannot build before setting tag id");
        if(bb == null)
            throw new IllegalStateException("Cannot build before setting name");

        return bb.array();
    }

    /**
     * Get the tag's name in UTF-8 bytes
     * @return the tag name in UTF-8 bytes
     */
    public byte[] getNameBytes() {
        return nameBytes;
    }

    /**
     * Get the name UTF-8 bytes length, in bytes
     * @return the name UTF-8 bytes length as byte array of an unsigned short
     */
    public byte[] getNameLengthBytes() {
        return nameLengthBytes;
    }

}
