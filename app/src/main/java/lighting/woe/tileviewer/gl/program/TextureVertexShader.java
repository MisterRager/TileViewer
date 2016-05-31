package lighting.woe.tileviewer.gl.program;

import lighting.woe.tileviewer.gl.program.shader.Assign;
import lighting.woe.tileviewer.gl.program.shader.Attribute;
import lighting.woe.tileviewer.gl.program.shader.Function;
import lighting.woe.tileviewer.gl.program.shader.Uniform;
import lighting.woe.tileviewer.gl.program.shader.Varying;

public class TextureVertexShader implements Shader {

    public static final String UNIFORM_VIEW = "mView";
    public static final String UNIFORM_PROJ = "mProjection";
    public static final String ATTRIB_MODEL = "a_mModel";
    public static final String ATTRIB_POS = "a_vPosition";
    public static final String ATTRIB_TEX_POS = "a_vTexPosition";
    public static final String VARYING_TEX_POS = "v_vTexPosition";

    private static final ShaderBuilder sShaderBuilder = new ShaderBuilder(
            new Uniform(TYPE_MAT4, UNIFORM_VIEW),
            new Uniform(TYPE_MAT4, UNIFORM_PROJ),
            new Attribute(TYPE_MAT4, ATTRIB_MODEL),
            new Attribute(TYPE_VEC4, ATTRIB_POS),
            new Attribute(TYPE_VEC2, ATTRIB_TEX_POS),
            new Varying(TYPE_VEC2, VARYING_TEX_POS),
            new Function(TYPE_VOID, FUNCTION_MAIN,
                    new Assign(PARAM_GL_POSITION,
                            String.format("%s * %s * %s * %s",
                                    UNIFORM_PROJ, UNIFORM_VIEW, ATTRIB_MODEL, ATTRIB_POS)),
                    new Assign(VARYING_TEX_POS, ATTRIB_TEX_POS)));

    @Override
    public String source() {
        return sShaderBuilder.toString();
    }
}
