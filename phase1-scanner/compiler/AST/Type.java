package compiler.AST;

public class Type {

    private String signature;
    private int memory;
    private int arrayDimension;

    public Type(String signature, int memory) {
        this.signature = signature;
        this.memory = memory;
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

    public int getArrayDimension() {
        return arrayDimension;
    }

    public void setArrayDimension(int arrayDimension) {
        this.arrayDimension = arrayDimension;
    }
}
