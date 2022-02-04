package compiler.cgen;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

enum AccessMode {
    Public,
    Private
}

public class Function {
    String Name;
    SymbolInfo returnType;
    Scope scope;
    List<SymbolInfo> argumentsType = new ArrayList<>();
    AccessMode accessMode = AccessMode.Public;
    public static Function currentFunction;

    public Function(String name, SymbolInfo returnType, Scope scope) {
        Name = name;
        this.returnType = returnType;
        this.scope = scope;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public SymbolInfo getReturnType() {
        return returnType;
    }

    public Scope getScope() {
        return scope;
    }

    public List<SymbolInfo> getArgumentsType() {
        return argumentsType;
    }

    @Override
    public String toString() {
        return "Function{" +
                "Name='" + Name + '\'' +
                ", returnType=" + returnType +
                ", scope=" + scope +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Function function = (Function) o;
        return Objects.equals(Name, function.Name) &&
                Objects.equals(scope, function.scope);
    }

    @Override
    public int hashCode() {
        return Objects.hash(Name, scope);
    }

    public void setAccessMode(AccessMode accessMode) {
        this.accessMode = accessMode;
    }
}
