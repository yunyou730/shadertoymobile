package com.example.bibabo;

import android.opengl.GLES20;
import android.opengl.GLES30;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

public class Rectangle {
    private FloatBuffer vertexBuffer = null;

    static final int COORDS_PER_VERTEX = 3;
    static float coords[] = {

            -0.5f,-0.5f,0.0f,
             0.5f,-0.5f,0.0f,
            -0.5f, 0.5f,0.0f,

             0.5f,-0.5f,0.0f,
             0.5f, 0.5f,0.0f,
            -0.5f, 0.5f,0.0f,
    };

    float color[] = {0.5f,0.7f,0.8f,1.0f};

    private final String vertexShaderCode =
            "attribute vec4 vPosition;" +
                    "void main() {" +
                    "  gl_Position = vPosition;" +
                    "}";

    private final String fragmentShaderCode =
            "precision mediump float;" +
                    "uniform vec4 vColor;" +
                    "void main() {" +
                    "  gl_FragColor = vColor;" +
                    "}";

    private final int mProgram;


    private int positionHandle;
    private int colorHandle;

    private final int vertexCount = coords.length / COORDS_PER_VERTEX;
    private final int vertexStride = COORDS_PER_VERTEX * 4; // 4 bytes per vertex



    public Rectangle() {
        // Prepare shaders
        int vertexShader = WizardRenderer.loadShader(GLES30.GL_VERTEX_SHADER,
                vertexShaderCode);
        int fragmentShader = WizardRenderer.loadShader(GLES30.GL_FRAGMENT_SHADER,
                fragmentShaderCode);
        mProgram = GLES30.glCreateProgram();
        GLES30.glAttachShader(mProgram, vertexShader);
        GLES30.glAttachShader(mProgram, fragmentShader);
        GLES30.glLinkProgram(mProgram);

        // Prepare mesh data
        ByteBuffer bb = ByteBuffer.allocateDirect(coords.length * 4); // 4 bytes per float
        bb.order(ByteOrder.nativeOrder());

        //  create a floating point buffer from the ByteBuffer
        vertexBuffer = bb.asFloatBuffer();
        vertexBuffer.put(coords);
        vertexBuffer.position(0);
    }


    public void draw() {

        GLES30.glBindVertexArray(0); // unbind all VAOs, use the default VAO

        // Add program to OpenGL ES environment
        GLES30.glUseProgram(mProgram);

        // get handle to vertex shader's vPosition member
        positionHandle = GLES30.glGetAttribLocation(mProgram, "vPosition");

        // Enable a handle to the triangle vertices
        GLES30.glEnableVertexAttribArray(positionHandle);

        // Prepare the triangle coordinate data
        GLES30.glVertexAttribPointer(positionHandle, COORDS_PER_VERTEX,
                GLES30.GL_FLOAT, false,
                vertexStride, vertexBuffer);

        // get handle to fragment shader's vColor member
        colorHandle = GLES30.glGetUniformLocation(mProgram, "vColor");
        // Set color for drawing the triangle
        GLES30.glUniform4fv(colorHandle, 1, color, 0);

        // Draw the triangle
        GLES30.glDrawArrays(GLES30.GL_TRIANGLES, 0, vertexCount);

        // Disable vertex array
        GLES30.glDisableVertexAttribArray(positionHandle);
    }

}
