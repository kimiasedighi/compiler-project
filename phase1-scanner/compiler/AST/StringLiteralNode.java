package compiler.AST;

public class StringLiteralNode extends Literal {
    private String value;

    public StringLiteralNode(String value) {
        super(new Type(".ascii", 6));
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
