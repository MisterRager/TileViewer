package lighting.woe.tileviewer.gl.program;

public interface Shader {
    String TYPE_VOID = "void";

    String TYPE_MAT4 = "mat4";
    String TYPE_VEC2 = "vec2";
    String TYPE_VEC4 = "vec4";

    String PARAM_GL_POSITION = "gl_Position";

    String FUNCTION_MAIN = "main";

    String source();
}
