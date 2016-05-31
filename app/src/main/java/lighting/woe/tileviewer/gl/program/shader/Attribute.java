package lighting.woe.tileviewer.gl.program.shader;

public class Attribute extends Declare {
    private static final String FIELD = "attribute ";

    public Attribute(String type, String name) {
        super(type, name);
    }

    @Override
    public String toString() {
        return FIELD + super.toString();
    }
}
