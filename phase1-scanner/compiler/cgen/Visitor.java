package compiler.cgen;

import compiler.AST.Node;

public interface Visitor {
  void visit(Node node) throws Exception ;
}
