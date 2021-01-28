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
    byte[] payloadBytes();

    int payloadSize();

    int id();

}
