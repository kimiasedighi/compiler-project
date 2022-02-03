package cgen;

import AST.Node;

public interface Visitor {
  void visit(Node node) throws Exception ;
}
