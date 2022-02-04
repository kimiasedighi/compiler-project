package compiler.AST;

public class IntegerLiteralNode extends Literal {
    private int value;

    public IntegerLiteralNode(int value) {
        super(PrimitiveType.INT);
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public String toString() {
        return Integer.toString(value);
    }
}
