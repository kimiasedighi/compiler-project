package compiler.AST;

public class DoubleLiteralNode extends Literal {
    private float value;

    public DoubleLiteralNode(float value) {
        super(new Type(".float", 8));
        this.value = value;
    }

    public float getValue() {
        return value;
    }

    @Override
    public String toString() {
        return Float.toString(value);
    }
}
