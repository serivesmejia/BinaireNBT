package com.github.serivesmejia.binairenbt.tag;

public class TAGEnd implements TAG<Void> {

    @Override
    public Void toJava() {
        throw new UnsupportedOperationException("toJava() method is not supported for this type of TAG (END)");
    }

    @Override
    public void fromJava(Void value) {
        throw new UnsupportedOperationException("fromJava() method is not supported for this type of TAG (END)");
    }

    @Override
    public void copyToPayloadBytes(byte[] bytes) {
        throw new UnsupportedOperationException("fromPayloadBytes() method is not supported for this type of TAG (END)");
    }

    @Override
    public void copyBytes(byte[] bytes) {
        throw new UnsupportedOperationException("fromBytes() method is not supported for this type of TAG (END)");
    }

    @Override
    public void position(int position) {
        throw new UnsupportedOperationException("position() method is not supported for this type of TAG (END)");
    }

    @Override
    public byte readByte() {
        throw new UnsupportedOperationException("readByte() method is not supported for this type of TAG (END)");
    }

    @Override
    public byte readByte(int byteIndex) {
        throw new UnsupportedOperationException("readByte(byteIndex) method is not supported for this type of TAG (END)");
    }

    @Override
    public byte[] bytes() {
        return new byte[] { (byte)id() };
    }

    @Override
    public byte[] headerBytes() {
        return bytes();
    }

    @Override
    public byte[] payloadBytes() {
        return bytes();
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
        throw new UnsupportedOperationException("name() method is not supported for this type of TAG (END)");
    }

    @Override
    public byte[] nameBytes() {
        throw new UnsupportedOperationException("nameBytes() method is not supported for this type of TAG (END)");
    }

    @Override
    public byte[] nameLengthBytes() {
        throw new UnsupportedOperationException("nameLengthBytes() method is not supported for this type of TAG (END)");
    }

}
