package lighting.woe.tileviewer.gl.program.shader;

import java.util.Arrays;
import java.util.List;

public class Function extends Declare {
    private static final String FORMAT = "%s() {%s\n}";

    private final List<Assign> mAssignments;

    public Function(String type, String name, Assign... assigns) {
        super(type, name);
        mAssignments = Arrays.asList(assigns);
    }

    @Override
    public String toString() {
        StringBuilder body = new StringBuilder();

        for (Assign a : mAssignments) {
            body.append("\n  ").append(a.toString());
        }

        String sup = super.toString();

        return String.format(FORMAT, sup.substring(0, sup.length() - 1), body.toString());
    }
}
