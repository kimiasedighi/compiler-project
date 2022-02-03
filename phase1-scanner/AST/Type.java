package AST;

public interface Type {
    String getSignature();
    int getMemory();
    PrimitiveType getPrimitive();
}
