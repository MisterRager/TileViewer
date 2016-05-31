uniform mat4 mView;
uniform mat4 mProjection;
attribute mat4 a_mModel;
attribute vec4 a_vPosition;
attribute vec2 a_vTexPosition;
varying vec2 v_vTexPosition;
void main() {
  gl_Position = mProjection * mView * a_mModel * a_vPosition;
  v_vTexPosition = a_vTexPosition;
}