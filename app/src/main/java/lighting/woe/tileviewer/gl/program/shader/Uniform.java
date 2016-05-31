package lighting.woe.tileviewer.gl.program.shader;

public class Uniform extends Declare {
    private static final String FIELD = "uniform ";

    public Uniform(String type, String name) {
        super(type, name);
    }

    @Override
    public String toString() {
        return FIELD + super.toString();
    }
}
