package compiler.AST;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BaseNode implements Node {
    private List<Node> children = new ArrayList<>();
    private Node parent;
    private NodeType nodeType;
    Type typeInfo;

    public BaseNode(NodeType nodeType) {
        this.nodeType = nodeType;
    }

    public NodeType getNodeType() {
        return nodeType;
    }

    public void setTypeInfo(Type typeInfo) {
        this.typeInfo = typeInfo;
    }

    public Type getTypeInfo() {
        return typeInfo;
    }

    public String toString() {
        return nodeType.toString() + typeInfo;
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
