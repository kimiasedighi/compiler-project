package compiler.cgen;

import compiler.AST.Type;

import java.util.Objects;

public class Field {
    String name;
    Type symbolInfo;
    AccessMode accessMode = AccessMode.Public;
    DefinedClass definedClass = null;
    public static AccessMode currentAccessMode;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Type getSymbolInfo() {
        return symbolInfo;
    }

    public void setSymbolInfo(Type symbolInfo) {
        this.symbolInfo = symbolInfo;
    }

    public AccessMode getAccessMode() {
        return accessMode;
    }

    public void setAccessMode(AccessMode accessMode) {
        this.accessMode = accessMode;
    }

    public DefinedClass getDefinedClass() {
        return definedClass;
    }

    public void setDefinedClass(DefinedClass definedClass) {
        this.definedClass = definedClass;
    }

    public static AccessMode getCurrentAccessMode() {
        return currentAccessMode;
    }

    public static void setCurrentAccessMode(AccessMode currentAccessMode) {
        Field.currentAccessMode = currentAccessMode;
    }

    public Field(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Field{" +
                "name='" + name + '\'' +
                ", symbolInfo=" + symbolInfo +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Field field = (Field) o;

        if (!Objects.equals(name, field.name)) return false;
        return Objects.equals(definedClass, field.definedClass);
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (definedClass != null ? definedClass.hashCode() : 0);
        return result;
    }
}
