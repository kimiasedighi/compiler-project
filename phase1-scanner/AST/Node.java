package AST;

//import codegen.SymbolInfo;
//import codegen.SimpleVisitor;

import java.util.List;

public interface Node {

    NodeType getNodeType();

    //Sets the symbol info.
    //void setSymbolInfo(SymbolInfo si);

    //Gets the symbol info.
    //SymbolInfo getSymbolInfo();

    //Accepts a simple visitor.
    //void accept(SimpleVisitor visitor) throws Exception;

    //Adds a node to the end of the list of children.
    void addChild(Node node);

    //Adds a node to the list of children at the specified location.
    void addChild(int index, Node node);

    void addChild(Node... nodes);

    //Returns the child at the specified location.
    Node getChild(int index);

    //Adds a list of nodes to the end of the list of children.
    void addChildren(List<Node> nodes);

    void setChildren(List<Node> nodes);
    List<Node> getChildren();

    void setParent(Node parent);
    Node getParent();
}
