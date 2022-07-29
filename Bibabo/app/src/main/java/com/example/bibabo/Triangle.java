package com.example.bibabo;

import android.opengl.GLES20;
import android.opengl.GLES30;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class Triangle {
    private FloatBuffer vertexBuffer = null;

    static final int COORDS_PER_VERTEX = 3;
    static float triangleCoords[] = {
        0.0f,   0.6f,     0.0f,
        -0.5f, -0.3f, 0.0f,
        0.5f, -0.3f, 0.0f,
    };

    float color[] = {1.0f,0.7f,0.2f,1.0f};

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

    private final int vertexCount = triangleCoords.length / COORDS_PER_VERTEX;
    private final int vertexStride = COORDS_PER_VERTEX * 4; // 4 bytes per vertex


    int[] mVAO = new int[1];
    int[] mVBO = new int[1];
    int[] mEBO = new int[1];

    public Triangle() {
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
        // 4 bytes per float
        ByteBuffer bb = ByteBuffer.allocateDirect(triangleCoords.length * 4);
        bb.order(ByteOrder.nativeOrder());

        //  create a floating point buffer from the ByteBuffer
        vertexBuffer = bb.asFloatBuffer();
        vertexBuffer.put(triangleCoords);
        vertexBuffer.position(0);

        // @miao @todo
        // Try to with VAO
//        mVAO = new IntBuffer(1);


        GLES30.glGenVertexArrays(1,mVAO,0);
        GLES30.glGenBuffers(1,mVBO,0);
        GLES30.glGenBuffers(1,mEBO,0);

        GLES30.glBindVertexArray(mVAO[0]);
        {
            GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER,mVBO[0]);
            GLES30.glBufferData(GLES30.GL_ARRAY_BUFFER,triangleCoords.length * 4,vertexBuffer, GLES30.GL_STATIC_DRAW);

            GLES30.glVertexAttribPointer(0,3,GLES30.GL_FLOAT,false,vertexStride,0);
            GLES30.glEnableVertexAttribArray(0);
            GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER,0);
        }
        GLES30.glBindVertexArray(0);
    }

    // Draw with VAO
    public void draw() {
        GLES30.glBindVertexArray(mVAO[0]);

        // Add program to OpenGL ES environment
        GLES30.glUseProgram(mProgram);

        // get handle to fragment shader's vColor member
        colorHandle = GLES30.glGetUniformLocation(mProgram, "vColor");
        // Set color for drawing the triangle
        GLES30.glUniform4fv(colorHandle, 1, color, 0);

        // Draw the triangle
        GLES30.glDrawArrays(GLES30.GL_TRIANGLES, 0, vertexCount);

    }

    // Draw without VAO
//    public void draw() {
//        // Add program to OpenGL ES environment
//        GLES30.glUseProgram(mProgram);
//
//        // get handle to vertex shader's vPosition member
//        positionHandle = GLES30.glGetAttribLocation(mProgram, "vPosition");
//
//        // Enable a handle to the triangle vertices
//        GLES30.glEnableVertexAttribArray(positionHandle);
//
//
//        //vertexBuffer.position(0);
//        // Prepare the triangle coordinate data
//        GLES30.glVertexAttribPointer(positionHandle, COORDS_PER_VERTEX,
//                GLES30.GL_FLOAT, false,
//                vertexStride, vertexBuffer);
//
//        // get handle to fragment shader's vColor member
//        colorHandle = GLES30.glGetUniformLocation(mProgram, "vColor");
//        // Set color for drawing the triangle
//        GLES30.glUniform4fv(colorHandle, 1, color, 0);
//
//        // Draw the triangle
//        GLES30.glDrawArrays(GLES30.GL_TRIANGLES, 0, vertexCount);
//
//        // Disable vertex array
//        GLES30.glDisableVertexAttribArray(positionHandle);
//    }

}
