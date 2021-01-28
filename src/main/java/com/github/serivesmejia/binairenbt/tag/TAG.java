package com.github.serivesmejia.binairenbt.tag;

import com.github.serivesmejia.binairenbt.exception.UnmatchingTagIdException;

public interface TAG<T> {

    T toJava();
    void fromJava(T value);

    void fromPayloadBytes(byte[] bytes);
    void fromBytes(byte[] bytes) throws UnmatchingTagIdException;

    void position(int position);

    byte readByte();
    byte readByte(int byteIndex);

    byte[] bytes();
    byte[] headerBytes();
    byte[] payloadBytes();

    int payloadCapacity();
    int payloadPosition();


    String name();
    byte[] nameBytes();

    byte[] nameLengthBytes();

    int id();

    default byte idByte() {
        return (byte)id();
    }

}
