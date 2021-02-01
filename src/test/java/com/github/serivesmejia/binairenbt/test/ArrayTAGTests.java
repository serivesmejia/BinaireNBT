package com.github.serivesmejia.binairenbt.test;

import com.github.serivesmejia.binairenbt.tag.TAGIntArray;
import com.github.serivesmejia.binairenbt.tag.TAGLongArray;
import com.github.serivesmejia.binairenbt.util.MathUtil;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ArrayTAGTests {

    @Test
    public void GetSetIntArrayTest() {
        Integer[] intArray = new Integer[] { 26, 0, 7, 20, 21 };

        TAGIntArray intArrayTag = new TAGIntArray("tagIntArray", intArray.length);
        intArrayTag.fromJava(intArray);

        assertEquals(intArrayTag.arrayCapacity(), intArray.length);

        int randomIndex = MathUtil.getRandomNumber(0, intArray.length);
        assertEquals((int)intArray[randomIndex], intArrayTag.get(randomIndex));

        int randomNumber = MathUtil.getRandomNumber(-9999, 9999);
        intArrayTag.set(randomIndex, randomNumber);
        assertEquals(intArrayTag.get(randomIndex), randomNumber);
    }

    @Test
    public void GetSetLongArrayTest() {
        Long[] longArray = new Long[] { 265343523L, 0L, 711425343L, 2056475345345L, 2112312523534L };

        TAGLongArray longArrayTag = new TAGLongArray("tagLongArray", longArray.length);
        longArrayTag.fromJava(longArray);

        assertEquals(longArrayTag.arrayCapacity(), longArray.length);

        int randomIndex = MathUtil.getRandomNumber(0, longArray.length);
        assertEquals((long)longArray[randomIndex], longArrayTag.get(randomIndex));

        long randomNumber = MathUtil.getRandomNumber(-534234, 745764);
        longArrayTag.set(randomIndex, randomNumber);
        assertEquals(longArrayTag.get(randomIndex), randomNumber);
    }

}
