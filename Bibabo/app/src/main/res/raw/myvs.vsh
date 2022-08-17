attribute vec4 vPosition;
attribute vec2 aUV;
varying vec2 vUV;

uniform float u_RotDeg;
const float PI = 3.1415;

mat4 getRotByZMat4(float rad)
{
    mat4 rotMat = mat4(
        cos(rad),-sin(rad),0.0,0.0,
        sin(rad), cos(rad),0.0,0.0,
        0.0,0.0,1.0,0.0,
        0.0,0.0,0.0,1.0
    );
    return rotMat;
}

mat2 getRotMat2(float rad)
{
    mat2 rotMat = mat2(
        cos(rad),-sin(rad),
        sin(rad),cos(rad)
    );
    return rotMat;
}

void main() {
    // float rotDeg = 90.0;
    float rotRad = PI/180.0 * u_RotDeg;

    vec4 pos = getRotByZMat4(rotRad) * vPosition;
    pos.x = -pos.x;
    vUV = aUV;

   gl_Position = pos;
}

