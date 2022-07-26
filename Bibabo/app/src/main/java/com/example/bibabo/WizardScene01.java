package com.example.bibabo;

import android.opengl.GLES20;
import android.opengl.GLES30;

import java.nio.IntBuffer;

public class WizardScene01 {

    private final String vertexShaderCode =
            "#version 300 es\n" +
            "layout (location = 0) in vec4 av_Position;" +
            "void main() {" +
                "gl_Position = av_Position;"+
            "}";
    private final String fragmentShaderCode =
            "#version 300 es\n"+
            "precision mediump float;" +
            "out vec4 fragColor;"+
            "void main(){"+
                "fragColor = vec4(1.0,0.0,0.5,1.0);"+
            "}";

    private IntBuffer mVAO;
    private IntBuffer mVBO;

    private int mProgram;


    public WizardScene01() {
//        mProgram = 1;

//        mVAO = IntBuffer.allocate(1);
//        GLES30.glGenVertexArrays(1,mVAO);
//        GLES30.glBufferData();

//        GLUtils.readRawTextFile();

    }

    public void draw() {
//        GLES20.glUseProgram(mProgram);


    }

    void prepareShader() {
        mProgram = ShaderUtil.createProgram(vertexShaderCode,fragmentShaderCode);
    }
}
