package compiler.AST;

public class BooleanLiteralNode extends Literal {
    private boolean value;

    public BooleanLiteralNode(boolean value) {
        super(new Type(".word", 1));
        this.value = value;
    }

    public boolean getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value ? "true" : "false";
    }
}
