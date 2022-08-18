package com.example.bibabo.utils;

import java.nio.ByteBuffer;

public class ImageFileConvert {


    static {
        System.loadLibrary("bibabo");
    }

    public native ImageRawData getImageRawData(byte[] buffer);
}
