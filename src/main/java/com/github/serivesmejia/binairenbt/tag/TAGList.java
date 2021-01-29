package com.github.serivesmejia.binairenbt.tag;

import com.github.serivesmejia.binairenbt.exception.UnmatchingTagIdException;

import java.lang.reflect.ParameterizedType;
import java.util.List;

public class TAGList<T extends TAG<?>> implements TAG<List<T>> {

    public TAGList(String name, int size) {
    }

    public TAGList(byte[] bytes) {
    }

    @Override
    public List<T> toJava() {
        return null;
    }

    @Override
    public void fromJava(List<T> value) {

    }

    @Override
    public void copyToPayloadBytes(byte[] bytes) {

    }

    @Override
    public void copyBytes(byte[] bytes) throws UnmatchingTagIdException {

    }

    @Override
    public void position(int position) {

    }

    @Override
    public byte readByte() {
        return 0;
    }

    @Override
    public byte readByte(int byteIndex) {
        return 0;
    }

    @Override
    public byte[] bytes() {
        return new byte[0];
    }

    @Override
    public byte[] headerBytes() {
        return new byte[0];
    }

    @Override
    public byte[] payloadBytes() {
        return new byte[0];
    }

    @Override
    public int payloadCapacity() {
        return 0;
    }

    @Override
    public int payloadPosition() {
        return 0;
    }

    @Override
    public String name() {
        return null;
    }

    @Override
    public byte[] nameBytes() {
        return new byte[0];
    }

    @Override
    public byte[] nameLengthBytes() {
        return new byte[0];
    }

    @Override
    public int id() {
        return 9;
    }

    private Class<? extends TAG> getTClass() {
        ParameterizedType pt = (ParameterizedType) this.getClass().getGenericInterfaces()[0];
        return  (Class<? extends TAG>) pt.getActualTypeArguments()[0];
    }

}