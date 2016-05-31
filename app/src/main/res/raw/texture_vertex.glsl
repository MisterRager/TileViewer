uniform mat4 mView;
uniform mat4 mProjection;

attribute mat4 mModel;
attribute vec4 vPosition;
attribute vec2 a_texCoord;

varying vec2 v_texCoord;

void main() {
  gl_Position = mProjection * mView * mModel * vPosition;
  v_texCoord = a_texCoord;
}
