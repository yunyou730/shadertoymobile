package com.example.bibabo.utils;

import java.nio.ByteBuffer;

public class ImageFileConvert {


    static {
        System.loadLibrary("bibabo");
    }

//    public native static void convertImageFileDataToRGBAA();
    public native String stringFromJNI(ByteBuffer buffer);
}
