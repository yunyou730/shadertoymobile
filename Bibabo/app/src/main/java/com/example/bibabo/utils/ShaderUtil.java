package com.example.bibabo.utils;

import android.content.res.Resources;
import android.opengl.GLES30;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.IntBuffer;
import java.nio.charset.StandardCharsets;

//加载顶点Shader与片元Shader的工具类
public class ShaderUtil {
    //加载制定shader的方法
    /*
    * shader的类型  GLES30.GL_VERTEX_SHADER   GLES30.GL_FRAGMENT_SHADER
    * shader的脚本字符串
    * */
    public static int loadShader(int shaderType, String source) {
        //创建一个新shader
        int shader = GLES30.glCreateShader(shaderType);
        //若创建成功则加载shader
        if (shader != 0) {
            //加载shader的源代码
            GLES30.glShaderSource(shader, source);
            //编译shader
            GLES30.glCompileShader(shader);
            //存放编译成功shader数量的数组
            int[] compiled = new int[1];
            //获取Shader的编译情况
            GLES30.glGetShaderiv(shader, GLES30.GL_COMPILE_STATUS, compiled, 0);
            if (compiled[0] == 0) {//若编译失败则显示错误日志并删除此shader
                ;
                String shaderError = GLES30.glGetShaderInfoLog(shader);
                Log.e("ES30_ERROR", "Could not compile shader " + shaderType + ":");
                Log.e("ES30_ERROR", shaderError);

                GLES30.glDeleteShader(shader);

                String shaderTypeTag = "[shadertype]";
                if(shaderType == GLES30.GL_VERTEX_SHADER)
                {
                    shaderTypeTag = "[vs]";
                }
                else if(shaderType == GLES30.GL_FRAGMENT_SHADER)
                {
                    shaderTypeTag = "[fs]";
                }

                throw new RuntimeException(shaderTypeTag + shaderError);
//                shader = 0;
            }
        }
        else
        {
            throw new RuntimeException("glCreateShader error");
        }
        return shader;
    }

    //创建shader程序的方法
    public static int createProgram(String vertexSource, String fragmentSource) {
        //加载顶点着色器
        int vertexShader = loadShader(GLES30.GL_VERTEX_SHADER, vertexSource);
        if (vertexShader == 0) {
            return 0;
        }

        //加载片元着色器
        int pixelShader = loadShader(GLES30.GL_FRAGMENT_SHADER, fragmentSource);
        if (pixelShader == 0) {
            String errorInfo = GLES30.glGetShaderInfoLog(pixelShader);
//            throw new RuntimeException("fs err:" + errorInfo);
            return 0;
        }

        //创建程序
        int program = GLES30.glCreateProgram();
        //若程序创建成功则向程序中加入顶点着色器与片元着色器
        if (program != 0) {
            //向程序中加入顶点着色器
            GLES30.glAttachShader(program, vertexShader);
            checkGlError("glAttachShader");
            //向程序中加入片元着色器
            GLES30.glAttachShader(program, pixelShader);
            checkGlError("glAttachShader");
            //链接程序
            GLES30.glLinkProgram(program);
            //存放链接成功program数量的数组
            int[] linkStatus = new int[1];
            //获取program的链接情况
            GLES30.glGetProgramiv(program, GLES30.GL_LINK_STATUS, linkStatus, 0);
            //若链接失败则报错并删除程序
            if (linkStatus[0] != GLES30.GL_TRUE) {
                Log.e("ES30_ERROR", "Could not link program: ");
//                Log.e("ES30_ERROR", );
                GLES30.glDeleteProgram(program);
                throw new RuntimeException("link err:" + GLES30.glGetProgramInfoLog(program));

//                program = 0;
            }
        }
        return program;
    }

    //检查每一步操作是否有错误的方法
    public static void checkGlError(String op) {
        int error;
        while ((error = GLES30.glGetError()) != GLES30.GL_NO_ERROR) {
            Log.e("ES30_ERROR", op + ": glError " + error);
            throw new RuntimeException(op + ": glError " + error);
        }
    }

    //从sh脚本中加载shader内容的方法
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static String loadFromAssetsFile(String fname, Resources r) {
        String result = null;
        try {
            InputStream in = r.getAssets().open(fname);
            int ch = 0;
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            while ((ch = in.read()) != -1) {
                baos.write(ch);
            }
            byte[] buff = baos.toByteArray();
            baos.close();
            in.close();
            result = new String(buff, StandardCharsets.UTF_8);
            result = result.replaceAll("\\r\\n", "\n");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


    public static String loadFromResourceFile(Resources r,int fileRes) {
        try {
            InputStream fileStream = r.openRawResource(fileRes);
            int fileLen = fileStream.available();
// Read the entire resource into a local byte buffer.
            byte[] fileBuffer = new byte[fileLen];

            fileStream.read(fileBuffer);
            fileStream.close();
//            displayText = new String(fileBuffer);

            return new String(fileBuffer);
        } catch (IOException e) {
            // exception handling
            return "";
        }
    }
}