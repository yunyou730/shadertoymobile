#extension GL_OES_EGL_image_external : require
precision mediump float;
varying vec2 vUV;
uniform samplerExternalOES s_texture;
uniform float u_Time;

float speed =10.0;
float blockSize=15.0;
float maxRGBSplitX = 10.0;
float maxRGBSplitY = 10.0;
float randomNoise(vec2 seed){
    return fract(sin(dot(seed*floor(u_Time*speed),vec2(17.13,3.71)))*43758.5453);
}

float randomNoise(float seed){
    return randomNoise(vec2(seed,1.0));
}    

void main() {
    // Normalized pixel coordinates (from 0 to 1)
    vec2 uv = vec2(1.0-vUV.y,1.0 - vUV.x);



    float block = randomNoise(floor(uv*blockSize*vec2(20.0/9.0,1.0)));

    float displaceNoise = pow(block,8.0)*pow(block,3.0);
    float splitRGBNoise = pow(randomNoise(7.2341),17.0);
    float offsetX = displaceNoise - splitRGBNoise * maxRGBSplitX;
    float offsetY = displaceNoise - splitRGBNoise * maxRGBSplitY;

    float noiseX = 0.05 * randomNoise(13.0);
    float noiseY = 0.05 * randomNoise(7.0);
    vec2 offset = vec2(offsetX*noiseX,offsetY*noiseY);

    vec4 colorR = texture2D(s_texture,uv);
    vec4 colorG = texture2D(s_texture,uv+offset);
    vec4 colorB = texture2D(s_texture,uv-offset);

    vec4 color = vec4(colorR.r,colorG.g,colorB.b,1);
    // Output to screen
    gl_FragColor = color;


}