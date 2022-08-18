#include <jni.h>
#include <android/log.h>
#include <string>
#include "stb_image.h"

extern "C" JNIEXPORT jstring JNICALL
Java_com_example_bibabo_utils_ImageFileConvert_stringFromJNI(
        JNIEnv* env,
        jobject obj /* this */,
        jobject fileBuffer)
{

//stbi_uc *stbi_load_from_memory   (const *buffer, int len
// , int *x, int *y,
// int *channels_in_file,
// int desired_channels);
    int w,h;
    int channels;
    int len = 0;
    stbi_load_from_memory(nullptr,len,&w,&h,&channels,0);

    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}
