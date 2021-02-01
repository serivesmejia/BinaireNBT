package com.github.serivesmejia.binairenbt.tag;

import com.github.serivesmejia.binairenbt.exception.UnmatchingTagIdException;

public interface TAG<T> {

    enum Type {
        END(0, TAGEnd.class),
        BYTE(1, TAGByte.class),
        SHORT(2, TAGShort.class),
        INT(3, TAGInt.class),
        LONG(4, TAGLong.class),
        FLOAT(5, TAGFloat.class),
        DOUBLE(6, TAGDouble.class),
        BYTE_ARRAY(7, TAGByteArray.class),
        STRING(8, TAGString.class),
        LIST(9, TAGList.class),
        COMPOUND(10, null),
        INT_ARRAY(11, TAGIntArray.class),
        LONG_ARRAY(12, TAGLongArray.class),
        UNKNOWN(-1, null);

        public final int id;
        public final Class clazz;

        Type(int id, Class<? extends TAG> clazz) {
            this.id = id;
            this.clazz = clazz;
        }

        public static Type fromId(int typeId) {
            for(Type val : values()) {
                if(val.id == typeId) {
                    return val;
                }
            }
            return UNKNOWN;
        }

        public static Type fromClass(Class<?> clazz) {
            for(Type val : values()) {
                if(val.clazz == clazz) {
                    return val;
                }
            }
            return UNKNOWN;
        }
    }

    T toJava();
    void fromJava(T value);

    void copyToPayloadBytes(byte[] bytes);
    void copyBytes(byte[] bytes) throws UnmatchingTagIdException;

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
