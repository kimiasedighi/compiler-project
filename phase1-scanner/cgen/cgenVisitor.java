package cgen;

import AST.Node;

public class cgenVisitor implements Visitor {
    String dataSegment;
    String codeSegment;

    @Override
    public void visit(Node node){
        switch (node.getNodeType()){
            case START:
                break;

        }
    }
}
