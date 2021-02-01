package com.github.serivesmejia.binairenbt.tag;

import com.github.serivesmejia.binairenbt.Constants;
import com.github.serivesmejia.binairenbt.exception.IllegalTagFormatException;
import org.apache.commons.lang3.ArrayUtils;

import java.nio.ByteBuffer;

public class TAGLongArray extends ByteBufferTAG<Long[]> {

    TAGInt capacityInt = new TAGInt("_");

    public TAGLongArray(String name, int arrayCapacity) {
        if(arrayCapacity < 1) throw new IllegalArgumentException("Capacity should be bigger than 0");

        capacityInt.fromJava(arrayCapacity);

        init(name, capacityInt.payloadCapacity() + (arrayCapacity * Constants.TAG_LONG_PAYLOAD_CAPACITY));

        //moving to where the payload starts
        bb.position(payloadPosition());
        //put the 4 bytes telling the size of the array
        bb.put(capacityInt.payloadBytes());
    }

    public TAGLongArray(byte[] bytes) {
        init(bytes);
        //grab the array capacity from the first 4 bytes of this tag's payload
        capacityInt.copyToPayloadBytes(grabPayloadBytes(capacityInt.payloadCapacity(), 0));

        if(arrayCapacity() < 1) throw new IllegalTagFormatException("Capacity should be bigger than 0");
    }

    @Override
    public Long[] toJava() {
        long[] longArray = new long[arrayCapacity()];

        for(int i = 0 ; i < longArray.length ; i++) {
            ByteBuffer longbb = ByteBuffer.allocate(Constants.TAG_LONG_PAYLOAD_CAPACITY);

            //grab 4 bytes, starting from (i * 4) + 4
            //which means: "grab eight bytes starting after
            //the first four bytes specifying the array position,
            //plus i value times the length in bytes of a long, so
            //that we grab the exact bytes of our current long value."
            longbb.put(grabPayloadBytes(longbb.capacity(),
                    i * Constants.TAG_LONG_PAYLOAD_CAPACITY + capacityInt.payloadCapacity()));

            //starting from zero to grab int
            longbb.position(0);
            longArray[i] = longbb.getLong();
        }

        return ArrayUtils.toObject(longArray);
    }

    @Override
    public void fromJava(Long[] value) {
        if(value.length > arrayCapacity())
            throw new ArrayIndexOutOfBoundsException("Java Long[] is bigger than the NBT byte array");
        else if(value.length < arrayCapacity())
            throw new ArrayIndexOutOfBoundsException("Java Long[] is smaller than the NBT byte array");

        bb.position(payloadPosition() + capacityInt.payloadCapacity());

        //put the actual bytes
        for(Long longVal : value) {
            bb.putLong(longVal);
        }
    }

    public final long get(int index) {
        bb.position(payloadPosition() + capacityInt.payloadCapacity() + (Constants.TAG_LONG_PAYLOAD_CAPACITY * index));
        return bb.getLong();
    }

    public final void set(int index, long value) {
        bb.position(payloadPosition() + capacityInt.payloadCapacity() + (Constants.TAG_LONG_PAYLOAD_CAPACITY * index));
        bb.putLong(value);
    }

    public final int arrayCapacity() {
        return capacityInt.toJava();
    }

}
