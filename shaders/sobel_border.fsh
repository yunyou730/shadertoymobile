#extension GL_OES_EGL_image_external : require
precision mediump float;
varying vec2 vUV;
uniform samplerExternalOES s_texture;
uniform float u_Time;

#define _EdgeWidth 10.3
#define _BackgroundFade 0.0
#define _BackgroundColor vec4(0.43, 0.1, 0.45, 0.0)
#define _Brightness 1.3

float intensity(in vec4 color)
{
	return sqrt((color.x * color.x) + (color.y * color.y) + (color.z * color.z));
}

float sobel(float stepx, float stepy, vec2 center)
{
	// get samples around pixel
	float topLeft = intensity(texture2D(s_texture, center + vec2(-stepx, stepy)));
	float bottomLeft = intensity(texture2D(s_texture, center + vec2(-stepx, -stepy)));
	float topRight = intensity(texture2D(s_texture, center + vec2(stepx, stepy)));
	float bottomRight = intensity(texture2D(s_texture, center + vec2(stepx, -stepy)));

	float Gx = -1.0 * topLeft + 1.0 * bottomRight;
	float Gy = -1.0 * topRight + 1.0 * bottomLeft;

	float sobelGradient = sqrt((Gx * Gx) + (Gy * Gy));
	return sobelGradient;
}
void main() {
   // Normalized pixel coordinates (from 0 to 1)
    vec2 uv = vec2(1.0-vUV.y,1.0 - vUV.x);
    vec4 sceneColor = texture2D(s_texture, uv);

    float sobelGradient = sobel(_EdgeWidth / 2736.0, _EdgeWidth / 3648.0, uv);

    vec4 backgroundColor = mix(sceneColor, _BackgroundColor, _BackgroundFade);

    // varying pixel color
    float ColFactor = u_Time*5.0;
    vec3 col = 0.5 + 0.5*cos(ColFactor + uv.xyx + vec3(0,2,4));
    vec3 edgeColor = mix(backgroundColor.rgb, col, sobelGradient);

    // Output to screen
    gl_FragColor = vec4(edgeColor * _Brightness,1.0);


}