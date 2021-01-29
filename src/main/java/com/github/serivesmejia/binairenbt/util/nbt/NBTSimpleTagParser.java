package com.github.serivesmejia.binairenbt.util.nbt;

import com.github.serivesmejia.binairenbt.Constants;
import com.github.serivesmejia.binairenbt.tag.TAG;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public class NBTSimpleTagParser {

    private final ByteBuffer bb;
    private int cachedNameLength = -1;

    public NBTSimpleTagParser(byte[] tagBytes) {
        bb = ByteBuffer.wrap(tagBytes);
    }

    public byte getId() {
        return bb.get(0);
    }

    public TAG.Type getTagType() {
        return TAG.Type.fromId(getId());
    }

    public int getNameLength() {
        if(cachedNameLength <= 0) {
            ByteBuffer nameLengthBB = ByteBuffer.allocate(Constants.TAG_SHORT_PAYLOAD_CAPACITY);
            nameLengthBB.put(bb.get(1));
            nameLengthBB.put(bb.get(2));

            cachedNameLength = nameLengthBB.getInt();
        }

        return cachedNameLength;
    }

    public String getName() {
        byte[] nameBytes = new byte[getNameLength()];

        for(int i = 0 ; i < nameBytes.length ; i++) {
             nameBytes[i] = bb.get(i + Constants.NONAME_HEADER_BYTES);
        }

        return new String(nameBytes, StandardCharsets.UTF_8);
    }

    public int getPayloadStartPosition() {
        return Constants.NONAME_HEADER_BYTES + getNameLength();
    }

    public int getPayloadSize() {
        return bb.capacity() - getPayloadStartPosition();
    }

    public final byte grabHeaderByte(int byteIndex) {
        if(byteIndex > getPayloadStartPosition()) throw new ArrayIndexOutOfBoundsException(byteIndex);
        return bb.array()[byteIndex];
    }

    public final byte[] grabHeaderBytes(int grabAmount, int offset) {
        byte[] grabbedBytes = new byte[grabAmount];

        for(int i = 0 ; i < grabAmount ; i++) {
            grabbedBytes[i] = grabHeaderByte(i + offset);
        }

        return grabbedBytes;
    }

    public final byte grabPayloadByte(int byteIndex) {
        return bb.array()[byteIndex + getPayloadStartPosition()];
    }

    public final byte[] grabPayloadBytes(int grabAmount, int offset) {
        byte[] grabbedBytes = new byte[grabAmount];

        for(int i = 0 ; i < grabAmount ; i++) {
            grabbedBytes[i] = grabPayloadByte(i + offset);
        }

        return grabbedBytes;
    }

}
