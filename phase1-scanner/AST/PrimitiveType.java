package AST;

import java.util.ArrayList;
import java.util.List;

public enum PrimitiveType implements Type {

    INT(".word", 4),
    DOUBLE(".float", 8),
    BOOL(".word", 1),
    STRING(".ascii", 6),

    //todo
    VOID("void", 0),
    INPUTSTRING(".space", 12);

    private final String signature;
    private final int align;

    PrimitiveType(String signature, int align) {
        this.signature = signature;
        this.align = align;
    }

    public String getSignature() {
        return signature;
    }

    public int getAlign() {
        return align;
    }


    @Override
    public String toString() {
        return signature;
    }


    @Override
    public PrimitiveType getPrimitive() {
        return this;
    }

    public String getInitialValue() {
        String value = "";
        switch (this.signature) {
            case ".word":
                value = "0";
                break;
            case ".float":
                value = "0.0";
                break;
            case ".ascii":
                value = "\"\"";
                break;
        }
        return value;
    }

}
