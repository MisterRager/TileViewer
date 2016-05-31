package lighting.woe.tileviewer.gl.program.shader;

public class Assign {
    private static final String FORMAT = "%s = %s;";
    private final String target;
    private final String expression;

    public Assign(String target, String expression) {
        this.target = target;
        this.expression = expression;
    }

    @Override
    public String toString() {
        return String.format(FORMAT, target, expression);
    }
}
