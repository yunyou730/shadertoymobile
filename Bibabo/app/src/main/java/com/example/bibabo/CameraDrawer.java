package com.example.bibabo;

import android.graphics.SurfaceTexture;
import android.opengl.GLES11Ext;
import android.opengl.GLES30;

import com.example.bibabo.utils.OpenGLUtils;
import com.example.bibabo.utils.ShaderUtil;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

public class CameraDrawer {

    private FloatBuffer vertexBuffer = null;

    static final int COORDS_PER_VERTEX = 3;
    static final int UV_PER_VERTEX = 2;
    static final int ATTR_PER_VERTEX = COORDS_PER_VERTEX + UV_PER_VERTEX;

    static float coords[] = {
            // pos & uv
//            -0.5f,-0.5f,0.0f, 0.0f,0.0f,
//             0.5f,-0.5f,0.0f, 1.0f,0.0f,
//            -0.5f, 0.5f,0.0f, 0.0f,1.0f,
//
//             0.5f,-0.5f,0.0f, 1.0f,0.0f,
//             0.5f, 0.5f,0.0f, 1.0f,1.0f,
//            -0.5f, 0.5f,0.0f, 0.0f,1.0f,

            -1.0f,-1.0f,0.0f, 0.0f,0.0f,
             1.0f,-1.0f,0.0f, 1.0f,0.0f,
            -1.0f, 1.0f,0.0f, 0.0f,1.0f,

             1.0f,-1.0f,0.0f, 1.0f,0.0f,
             1.0f, 1.0f,0.0f, 1.0f,1.0f,
            -1.0f, 1.0f,0.0f, 0.0f,1.0f,
    };


    private final String vertexShaderCode =
            "attribute vec4 vPosition;" +
            "attribute vec2 aUV;" +
            "varying vec2 vUV;" +
            "void main() {" +
            "   vUV = aUV;" +
            "  gl_Position = vPosition;" +
            "}";

    private final String fragmentShaderCode =
            "#extension GL_OES_EGL_image_external : require\n" + // declare OEM texture
            "precision mediump float;" +
            "varying vec2 vUV;" +
            "uniform samplerExternalOES s_texture;" +
            "void main() {" +
            "   vec4 texColor = texture2D(s_texture,vUV);" +
//            "   vec4 texColor = texture2D(s_texture,vec2(vUV.x,1.0-vUV.y));" +
            "  gl_FragColor = texColor * vec4(vUV.x,vUV.y,0.0,1.0);" +
//            "  gl_FragColor = texColor;" +
            "}";

    private final int mProgram;

    private final int vertexCount = coords.length / ATTR_PER_VERTEX;
    private final int vertexStride = ATTR_PER_VERTEX * 4; // 4 bytes per vertex

    private SurfaceTexture mCameraSurfaceTexture = null;
    private int mTextureID = 0;

    public CameraDrawer() {
        // Prepare shaders
        mProgram = ShaderUtil.createProgram(vertexShaderCode,fragmentShaderCode);

        // Prepare mesh data
        ByteBuffer bb = ByteBuffer.allocateDirect(coords.length * 4); // 4 bytes per float
        bb.order(ByteOrder.nativeOrder());

        //  create a floating point buffer from the ByteBuffer
        vertexBuffer = bb.asFloatBuffer();
        vertexBuffer.put(coords);
        vertexBuffer.position(0);


        // Create texture
        int[] textures = OpenGLUtils.createTextures(
                GLES11Ext.GL_TEXTURE_EXTERNAL_OES,
                1,
                GLES30.GL_NEAREST,
                GLES30.GL_LINEAR,
                GLES30.GL_CLAMP_TO_EDGE,
                GLES30.GL_CLAMP_TO_EDGE
        );
        mTextureID = textures[0];
        mCameraSurfaceTexture = new SurfaceTexture(mTextureID);
    }


    public void draw() {

        GLES30.glBindVertexArray(0); // unbind all VAOs, to use the default VAO

        // Add program to OpenGL ES environment
        GLES30.glUseProgram(mProgram);

        // pos attribute
        vertexBuffer.position(0);
        int positionHandle = GLES30.glGetAttribLocation(mProgram, "vPosition");
        GLES30.glEnableVertexAttribArray(positionHandle);
        GLES30.glVertexAttribPointer(positionHandle, COORDS_PER_VERTEX,
                GLES30.GL_FLOAT, false,
                vertexStride, vertexBuffer);

        // uv attribute
        vertexBuffer.position(COORDS_PER_VERTEX); // make pointer an offset
        int uvHandle = GLES30.glGetAttribLocation(mProgram,"aUV");
        GLES30.glEnableVertexAttribArray(uvHandle);
        GLES30.glVertexAttribPointer(uvHandle, UV_PER_VERTEX,
                GLES30.GL_FLOAT, false,
                vertexStride, vertexBuffer);

        // Texture
        GLES30.glBindTexture(GLES11Ext.GL_TEXTURE_EXTERNAL_OES,mTextureID);

        // Draw the triangle
        GLES30.glDrawArrays(GLES30.GL_TRIANGLES, 0, vertexCount);

        // Disable vertex array
        GLES30.glDisableVertexAttribArray(positionHandle);
        GLES30.glDisableVertexAttribArray(uvHandle);
    }

    SurfaceTexture getSurfaceTexture() {
        return mCameraSurfaceTexture;
    }

}
