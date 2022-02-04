package compiler.AST;

public abstract class Literal extends BaseNode {
    private Type type;

    public Literal(Type type) {
        super(NodeType.LITERAL);
        this.type = type;
    }

    public Type getType() {
        return type;
    }
}
