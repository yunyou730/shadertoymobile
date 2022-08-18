#include <jni.h>
#include <android/log.h>
#include <string>
#include "stb_image.h"


extern "C"
JNIEXPORT jobject JNICALL
Java_com_example_bibabo_utils_ImageFileConvert_stringFromJNI(
        JNIEnv* env,
        jobject obj /* this */,
        jbyteArray fileBuffer)
{

    jboolean isCopy;
    jbyte* b = (*env).GetByteArrayElements(fileBuffer,&isCopy);
    jsize size = (*env).GetArrayLength(fileBuffer);

    int w,h;
    int channels;
    unsigned char* result = stbi_load_from_memory((unsigned char*)b,size,&w,&h,&channels,0);
    int newSize = w * h * channels;
//    int newSize = (*env).GetArrayLength(w * h * channels);

//    ImageRawData rawData;
//    rawData.width = w;
//    rawData.height = h;
//    rawData.channels = channels;

    jclass objectClass = (*env).FindClass("com/example/bibabo/utils/ImageRawData");
    jmethodID mid = (*env).GetMethodID(objectClass,"<init>","()V");
    jfieldID fieldWidth = (*env).GetFieldID(objectClass,"width","I");
    jfieldID fieldHeight = (*env).GetFieldID(objectClass,"height", "I");
    jfieldID fieldChannels = (*env).GetFieldID(objectClass,"channels", "I");
    jfieldID fieldRaw = (*env).GetFieldID(objectClass,"raw", "[B");

//    std::string hello = "Hello from C++";
//    return env->NewStringUTF(hello.c_str());
    jobject ret = (*env).NewObject(objectClass,mid);

    (*env).SetIntField(ret,fieldWidth,jint(w));
    (*env).SetIntField(ret,fieldHeight,jint(h));
    (*env).SetIntField(ret,fieldChannels,jint(channels));

    jbyteArray raw = (*env).NewByteArray(newSize);
    (*env).SetByteArrayRegion(raw,0,newSize,(jbyte*)result);
    (*env).SetObjectField(ret,fieldRaw,jbyteArray(raw));

    return ret;
}
