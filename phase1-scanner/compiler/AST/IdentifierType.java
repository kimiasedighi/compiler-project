package compiler.AST;

public class IdentifierType implements Type {
    private String signature;
    private int memory = 10;

    public IdentifierType(String signature) {
        this.signature = signature;
    }

    public String getSignature() {
        return signature;
    }

    public int getMemory() {
        return memory;
    }

    public PrimitiveType getPrimitive() {
        return null;
    }

}
