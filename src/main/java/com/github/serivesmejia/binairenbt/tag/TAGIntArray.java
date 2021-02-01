package com.github.serivesmejia.binairenbt.tag;

import com.github.serivesmejia.binairenbt.Constants;
import com.github.serivesmejia.binairenbt.exception.IllegalTagFormatException;
import org.apache.commons.lang3.ArrayUtils;

import java.nio.ByteBuffer;

public class TAGIntArray extends ByteBufferTAG<Integer[]>{

    TAGInt capacityInt = new TAGInt("_");

    public TAGIntArray(String name, int arrayCapacity) {
        if(arrayCapacity < 1) throw new IllegalArgumentException("Capacity should be bigger than 0");

        capacityInt.fromJava(arrayCapacity);

        init(name, capacityInt.payloadCapacity() + (arrayCapacity * Constants.TAG_INT_PAYLOAD_CAPACITY));

        //moving to where the payload starts
        bb.position(payloadPosition());
        //put the 4 bytes telling the size of the array
        bb.put(capacityInt.payloadBytes());
    }

    public TAGIntArray(byte[] bytes) {
        init(bytes);
        //grab the array capacity from the first 4 bytes of this tag's payload
        capacityInt.copyToPayloadBytes(grabPayloadBytes(capacityInt.payloadCapacity(), 0));

        if(arrayCapacity() < 1) throw new IllegalTagFormatException("Capacity should be bigger than 0");
    }

    @Override
    public Integer[] toJava() {
        int[] intArray = new int[arrayCapacity()];

        for(int i = 0 ; i < intArray.length ; i++) {
            ByteBuffer intbb = ByteBuffer.allocate(Constants.TAG_INT_PAYLOAD_CAPACITY);

            //grab 4 bytes, starting from (i * 4) + 4
            //which means: "grab four bytes starting after
            //the first four bytes specifying the array position,
            //plus i value times the length in bytes of an int, so
            //that we grab the exact bytes of our current int value."
            intbb.put(grabPayloadBytes(intbb.capacity(),
                    i * Constants.TAG_INT_PAYLOAD_CAPACITY + capacityInt.payloadCapacity()));

            //starting from zero to grab int
            intbb.position(0);
            intArray[i] = intbb.getInt();
        }

        return ArrayUtils.toObject(intArray);
    }

    @Override
    public void fromJava(Integer[] value) {
        if(value.length > arrayCapacity())
            throw new ArrayIndexOutOfBoundsException("Java Integer[] is bigger than the NBT byte array");
        else if(value.length < arrayCapacity())
            throw new ArrayIndexOutOfBoundsException("Java Integer[] is smaller than the NBT byte array");

        bb.position(payloadPosition() + capacityInt.payloadCapacity());

        //put the actual bytes
        for(Integer intVal : value) {
            bb.putInt(intVal);
        }
    }

    public final int arrayCapacity() {
        return capacityInt.toJava();
    }

    @Override
    public int id() {
        return 11;
    }

}
