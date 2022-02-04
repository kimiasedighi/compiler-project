package compiler.cgen;

public class Field {
    String name;
    SymbolInfo symbolInfo;
    AccessMode accessMode = AccessMode.Public;
    DefinedClass definedClass = null;
    public static AccessMode currentAccessMode;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SymbolInfo getSymbolInfo() {
        return symbolInfo;
    }

    public void setSymbolInfo(SymbolInfo symbolInfo) {
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

        if (name != null ? !name.equals(field.name) : field.name != null) return false;
        return definedClass != null ? definedClass.equals(field.definedClass) : field.definedClass == null;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (definedClass != null ? definedClass.hashCode() : 0);
        return result;
    }
}
