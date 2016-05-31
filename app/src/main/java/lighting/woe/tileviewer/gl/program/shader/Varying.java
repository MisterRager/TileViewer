package lighting.woe.tileviewer.gl.program.shader;

public class Varying extends Declare {
    private static final String FIELD = "varying ";

    public Varying(String type, String name) {
        super(type, name);
    }

    @Override
    public String toString() {
        return FIELD + super.toString();
    }
}
