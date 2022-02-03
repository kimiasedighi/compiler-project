package AST;

public enum PrimitiveType implements Type {

    INT(".word", 4),
    DOUBLE(".float", 8),
    BOOL(".word", 1),
    STRING(".ascii", 6),
    INPUTSTRING(".space", 12),

    VOID("void", 0);

    private final String signature;
    private final int memory;

    PrimitiveType(String signature, int memory) {
        this.signature = signature;
        this.memory = memory;
    }

    public String toString() {
        return signature;
    }

    public PrimitiveType getPrimitive() {
        return this;
    }

    public String getSignature() {
        return signature;
    }

    public int getMemory() {
        return memory;
    }

    public String getInitialValue() {
        return this.signature.equals(".word") ? "0" : this.signature.equals(".float") ? "0.0" : "\"\"";
    }

}
