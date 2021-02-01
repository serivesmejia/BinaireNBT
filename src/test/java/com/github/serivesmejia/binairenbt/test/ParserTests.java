package com.github.serivesmejia.binairenbt.test;

import com.github.serivesmejia.binairenbt.tag.TAG;
import com.github.serivesmejia.binairenbt.util.nbt.NBTSimpleTagParser;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ParserTests {

    byte[] tagLongArrayBytes = new byte[] { 12, 0, 12, 108, 111, 110, 103, 65, 114, 114, 97, 121, 84, 97, 103, 0, 0, 0, 4, 0, 0, 8, -84, -106, -51, -99, 40, 0, 0, 0, 12, 114, 21, -23, -2, 0, 0, 0, 0, 34, 79, -86, -8, 0, 0, 0, 0, 72, 80, 52, -10 };

    @Test
    public void TestSimpleTagParser() {
        NBTSimpleTagParser parser = new NBTSimpleTagParser(tagLongArrayBytes);

        assertEquals(parser.getId(), TAG.Type.LONG_ARRAY.id);
        assertEquals(parser.getName(), "longArrayTag");
    }

}
