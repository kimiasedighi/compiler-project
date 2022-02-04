package compiler.AST;

import compiler.cgen.SymbolTable;

public class IdentifierNode extends BaseNode {
    private String name;
    private TypeNode type;


    public IdentifierNode(String name) {
        super(NodeType.IDENTIFIER);
        this.name = name;

    }

    public IdentifierNode(String name, Node type) {
        super(NodeType.IDENTIFIER);
        this.name = name;
        this.type = (TypeNode) type;
        this.typeInfo = this.type.getType();
    }

    public String getName() {
        return name;
    }


    public TypeNode getType() {
        return type;
    }

    public void setType(TypeNode type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "%" + name;
    }

}
