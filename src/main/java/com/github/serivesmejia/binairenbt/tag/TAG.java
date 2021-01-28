package com.github.serivesmejia.binairenbt.tag;

public interface TAG<T> {

    T toJava();
    void fromJava(T value);

    void fromBytes(byte[] bytes);

    void position(int position);

    byte readByte();
    byte readByte(int byteIndex);

    byte[] toBytes();

    int payloadSize();

    int getId();

}
