package com.github.serivesmejia.binairenbt.tag;

import com.github.serivesmejia.binairenbt.exception.IllegalTagFormatException;
import org.apache.commons.lang3.ArrayUtils;

import java.util.Arrays;

public class TAGByteArray extends ByteBufferTAG<Byte[]>{

    TAGInt capacityInt = new TAGInt("capacityInt");

    public TAGByteArray(String name, int arrayCapacity) {
        if(arrayCapacity < 1) throw new IllegalArgumentException("Capacity should be bigger than 0");

        capacityInt.fromJava(arrayCapacity);
        init(name, capacityInt.payloadCapacity() + arrayCapacity);
    }

    public TAGByteArray(byte[] bytes) {
        init(bytes);
        //grab the array capacity from the first 4 bytes of this tag's payload
        capacityInt.fromPayloadBytes(grabPayloadBytes(capacityInt.payloadCapacity(), 0));

        if(arrayCapacity() < 1) throw new IllegalTagFormatException("Capacity should be bigger than 0");
    }

    @Override
    public Byte[] toJava() {
        byte[] byteArray = grabPayloadBytes(arrayCapacity(), capacityInt.payloadCapacity());

        return ArrayUtils.toObject(byteArray);
    }

    @Override
    public void fromJava(Byte[] value) {
        if(value.length > arrayCapacity())
            throw new ArrayIndexOutOfBoundsException("Java Byte[] is bigger than the NBT byte array");
        else if(value.length < arrayCapacity())
            throw new ArrayIndexOutOfBoundsException("Java Byte[] is smaller than the NBT byte array");

        bb.position(payloadPosition());

        //put the 4 bytes telling the size of the array
        bb.put(capacityInt.payloadBytes());

        //put the actual bytes
        for(Byte b : value) {
            bb.put(b);
        }
    }

    public final int arrayCapacity() {
        return capacityInt.toJava();
    }

    @Override
    public int id() {
        return 7;
    }

}
