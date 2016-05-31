package lighting.woe.tileviewer.gl.program.shader;

abstract public class Declare {
    private static final String FORMAT = "%s %s;";

    private final String type;
    private final String name;

    public Declare(String type, String name) {
        this.type = type;
        this.name = name;
    }

    @Override
    public String toString() {
        return String.format(FORMAT, type, name);
    }
}
