#version 120

attribute vec3 vertices;
attribute vec2 textures;

varying vec2 texCoords;

uniform mat4 projection;
void main(){
    gl_Position = projection* vec4(vertices, 1);
    texCoords = textures;
}