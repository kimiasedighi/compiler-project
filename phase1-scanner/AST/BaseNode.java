package AST;

import cgen.SymbolInfo;

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

    @Override
    public String toString() {
        String str = nodeType.toString();
        if (symbolInfo != null) {
            str += " (" + symbolInfo.toString() + ")";
        }
        return str;
    }

    @Override
    public void addChild(Node node) {
        children.add(node);
    }

    @Override
    public void addChild(int index, Node node) {
        children.add(index, node);
    }

    public void addChild(Node... nodes) {
        Collections.addAll(children, nodes);
    }

    @Override
    public void addChildren(List<Node> nodes) {
        children.addAll(nodes);
    }


    @Override
    public void setChildren(List<Node> nodes) {
        children = nodes;
    }

    @Override
    public Node getChild(int index) {
        return children.get(index);
    }

    @Override
    public List<Node> getChildren() {
        return children;
    }

    @Override
    public Node getParent() {
        return parent;
    }

    @Override
    public void setParent(Node parent) {
        this.parent = parent;
    }
}
