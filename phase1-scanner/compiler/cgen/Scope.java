package compiler.cgen;

import compiler.AST.Type;

import java.util.HashMap;
import java.util.Objects;

public class Scope {

    private String name;
    private HashMap<String, Type> variables;

    public Scope(String name) {
        this.name = name;
        this.variables = new HashMap<>();
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Scope scope = (Scope) o;

        return Objects.equals(name, scope.name);
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }

    public HashMap<String, Type> getVariables() {
        return variables;
    }

    @Override
    public String toString() {
        return "Scope{" +
                "name='" + name + '\'' +
                '}';
    }
}
