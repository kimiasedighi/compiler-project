package compiler.AST;

public interface Type {
    String getSignature();
    int getMemory();
    PrimitiveType getPrimitive();
}
