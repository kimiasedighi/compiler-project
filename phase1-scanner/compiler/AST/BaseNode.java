package compiler.AST;

import compiler.cgen.SymbolInfo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BaseNode implements Node {
    private List<Node> children = new ArrayList<>();
    private Node parent;
    private NodeType nodeType;
    SymbolInfo symbolInfo;

    public BaseNode(NodeType nodeType) {
        this.nodeType = nodeType;
    }

    public NodeType getNodeType() {
        return nodeType;
    }

    public void setSymbolInfo(SymbolInfo symbolInfo) {
        this.symbolInfo = symbolInfo;
    }

    public SymbolInfo getSymbolInfo() {
        return symbolInfo;
    }

    public String toString() {
        return nodeType.toString() + (symbolInfo != null ? " (" + symbolInfo.toString() + ")" : "");
    }

    public void addChild(Node node) {
        children.add(node);
    }

    public void addChild(Node... nodes) {
        Collections.addAll(children, nodes);
    }

    public Node getChild(int index) {
        return children.get(index);
    }

    public List<Node> getChildren() {
        return children;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }
}
