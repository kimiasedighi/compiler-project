package compiler.AST;

import java.util.List;

public interface Node {

    NodeType getNodeType();

    //Sets the symbol info.
    void setTypeInfo(Type si);

    //Gets the symbol info.
    Type getTypeInfo();


    //Adds a node to the end of the list of children.
    void addChild(Node node);

    void addChild(Node... nodes);

    //Returns the child at the specified location.
    Node getChild(int index);

    List<Node> getChildren();

    void setParent(Node parent);
    Node getParent();
}
