package compiler.AST;

public class TypeNode extends BaseNode {
    private Type type;

    public TypeNode(NodeType nodeType, Type type) {
        super(nodeType);
        this.type = type;
    }

    public Type getType() {
        return type;
    }
}
