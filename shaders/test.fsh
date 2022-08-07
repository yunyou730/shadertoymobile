#extension GL_OES_EGL_image_external : require
precision mediump float;
varying vec2 vUV;
uniform samplerExternalOES s_texture;
void main() {
    vec4 texColor = texture2D(s_texture,vUV);
    gl_FragColor = texColor * vec4(vUV.x,vUV.y,0.0,1.0);
}
