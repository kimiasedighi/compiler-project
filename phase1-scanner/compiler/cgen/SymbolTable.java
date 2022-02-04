package compiler.cgen;

import compiler.AST.Type;

import java.util.ArrayList;

public class SymbolTable {

    private ArrayList<Scope> scopeList = new ArrayList<>();
    private Scope currentScope;

    public Scope getCurrentScope() {
        return currentScope;
    }

    public String getCurrentScopeName() {
        return currentScope.getName();
    }

    public void scope_init(String scopeName) {
        Scope newScope = new Scope(scopeName);
        currentScope = newScope;
        scopeList.add(newScope);
    }

    public void scope_exit() {
        scope_delete();
        currentScope = scopeList.get(scopeList.size() - 1);
    }
    private boolean var_exist(String id,int scopeNum){
        if (scopeNum==-1){

            if (currentScope.getVariables().containsKey(id))
                return true;
            else
                return false;
        }else {
            if (scopeList.get(scopeNum).getVariables().containsKey(id))
                return true;
            else
                return false;
        }
    }
    private Type get_symbol(String id,int scopeNum){
        return scopeList.get(scopeNum).getVariables().get(id);
    }
    void add(String id, Type t) throws Exception {
        if (!var_exist(id,-1)) {
            currentScope.getVariables().put(id, t);
        }else {
            throw new Exception("this scope already has " + id);
        }
    }

    public Type get(String id) throws Exception {
        for (int i = scopeList.size() - 1; i >= 0; i--) {
            if (var_exist(id,i))
                return get_symbol(id,i);
        }
        throw new Exception( id + "is not defined");
    }

    String getScopeNameOfIdentifier(String id) {
        for (int i = scopeList.size() - 1; i >= 0; i--) {
            if (var_exist(id,i))
                return scopeList.get(i).getName();
        }
        return currentScope.getName();
    }

    public ArrayList<Scope> getScopeList() {
        return scopeList;
    }
    private void scope_delete(){ // this method deletes current scope from scope list
        if (currentScope != null)
            scopeList.remove(currentScope);
    }
}
