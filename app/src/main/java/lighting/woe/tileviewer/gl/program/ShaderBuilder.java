package lighting.woe.tileviewer.gl.program;

import java.util.Arrays;
import java.util.List;

import lighting.woe.tileviewer.gl.program.shader.Declare;

public class ShaderBuilder {
    private final List<Declare> mFields;

    public ShaderBuilder(Declare... declarations) {
        mFields = Arrays.asList(declarations);
    }

    @Override
    public String toString() {
        StringBuilder source = new StringBuilder();

        if (!mFields.isEmpty()) {
            boolean isFirst = true;

            for (Declare d : mFields) {
                if (isFirst) {
                    isFirst = false;
                } else {
                    source.append("\n");
                }
                source.append(d.toString());
            }
        }

        return source.toString();
    }

}
