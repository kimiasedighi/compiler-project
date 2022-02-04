package compiler.AST;

public class IntegerLiteralNode extends Literal {
    private int value;

    public IntegerLiteralNode(int value) {
        super(new Type(".word", 4));
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public String toString() {
        return Integer.toString(value);
    }
}
