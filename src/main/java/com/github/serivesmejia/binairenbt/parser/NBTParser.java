package com.github.serivesmejia.binairenbt.parser;

import com.github.serivesmejia.binairenbt.tag.TAG;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public class NBTParser {

    private ByteBuffer bb;

    private List<TAG> tags = new ArrayList<>();

    public NBTParser(byte[] bytes) {
        bb = ByteBuffer.wrap(bytes);
    }

    public NBTParser(ByteBuffer byteBuffer) {
        bb = byteBuffer;
    }

    public void parse() {

    }

}