package cgen;

import AST.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class cgenVisitor implements Visitor {

    String dataSegment;
    String codeSegment;
    private SymbolTable symbolTable = new SymbolTable();
    private List<Function> functions = new ArrayList<>();
    public static List<DefinedClass> classes = new ArrayList<>();

    private int blockIndex;
    private int labelIndex; // for generating label

    List<String> regs = Arrays.asList(
            "$zero", "$at",
            "$v0", "$v1",
            "$a0", "$a1", "$a2", "$a3",
            "$t0", "$t1", "$t2", "$t3", "$t4", "$t5", "$t6", "$t7",
            "$s0", "$s1", "$s2", "$s3", "$s4", "$s5", "$s6",
            "$t7", "$t8", "$t9",
            "$k0", "$k1",
            "$gp", "$sp", "fp", "ra"
    );

    @Override
    public void visit(Node node) throws Exception{
        switch (node.getNodeType()){
            case START:
                visitStartNode(node);
                break;
            case INTEGER_TYPE:
                node.setSymbolInfo(new SymbolInfo(node, PrimitiveType.INT));
                node.getSymbolInfo().setDimensionArray(node.getChildren().size());
                break;
            case DOUBLE_TYPE:
                node.setSymbolInfo(new SymbolInfo(node, PrimitiveType.DOUBLE));
                node.getSymbolInfo().setDimensionArray(node.getChildren().size());
                break;
            case BOOLEAN_TYPE:
                node.setSymbolInfo(new SymbolInfo(node, PrimitiveType.BOOL));
                node.getSymbolInfo().setDimensionArray(node.getChildren().size());
                break;
            case STRING_TYPE:
                node.setSymbolInfo(new SymbolInfo(node, PrimitiveType.STRING));
                node.getSymbolInfo().setDimensionArray(node.getChildren().size());
                break;
            case VOID_TYPE:
                node.setSymbolInfo(new SymbolInfo(node, PrimitiveType.VOID));
                node.getSymbolInfo().setDimensionArray(node.getChildren().size());
                break;
            case EMPTY_ARRAY:
                break;
            case DECLARATIONS:
                visitAllChildren(node);
                break;
            case FUNCTION_DECLARATION:
                visitFunctionDeclarationNode(node);
                break;
            case CLASS_DECLARATION:
                visitClassDeclarationNode(node);
                break;
            case VARIABLE_DECLARATION:
                visitVariableDeclaration(node);
                break;
            case VARIABLE_DECLARATIONS:
                visitAllChildren(node);
                break;
            case FIELD_DECLARATION:
                //todo
                visitAllChildren(node);
                break;
            case FIELDS:
                visitAllChildren(node);
                break;
//            case VARIABLE_PLUS_COMMA://they are function arguments in function declaration
//                visitArgumentsNode(node);
//                break;
            case ARGUMENT:
                visitAllChildren(node);
                break;
            case ARGUMENTS:
                visitArgumentsNode(node);
                break;
            case EXPRESSION_PLUS_COMMA://they are expressions that we pass to functions when we call them
                //todo
                visitAllChildren(node);
                break;
            case FUNCTION_ACCESS:
                break;
            case PRIVATE_ACCESS:
                break;
            case PUBLIC_ACCESS:
                break;
            case BLOCK:
                visitBlockNode(node);
                break;
            case STATEMENTS:
                visitAllChildren(node);
                break;
            case STATEMENT:
                visitAllChildren(node);
                break;
            case IF_STATEMENT:
                visitIfNode(node);
                break;
            case ELSE_STATEMENT:
                visitAllChildren(node);
                break;
            case WHILE_STATEMENT:
                visitWhileNode(node);
                break;
            case FOR_STATEMENT:
                visitForNode(node);
                break;
            case EMPTY_STATEMENT:
                break;
            case RETURN_STATEMENT:
                visitReturnNode(node);
                break;
            case BREAK_STATEMENT:
                visitBreakNode(node);
                break;
            case CONTINUE_STATEMENT:
                visitContinueNode(node);
                break;
            case PRINT_STATEMENT:
                visitPrintNode(node);
                break;
            case EXPRESSION_STATEMENT:
                visitExpressionNode(node);
                break;
            case ASSIGN:
                visitAssignNode(node);
                break;
            case ASSIGN_ADD:
                visitAssignAddNode(node);
                break;
            case ASSIGN_SUB:
                visitAssignSubNode(node);
                break;
            case ASSIGN_MUL:
                visitAssignMullNode(node);
                break;
            case ASSIGN_DIV:
                visitAssignDivNode(node);
                break;
            case THIS:
                break;
            case ADDITION:
                visitAdditionNode(node);
                break;
            case SUBTRACTION:
                visitSubtractionNode(node);
                break;
            case MULTIPLICATION:
                visitMultiplicationNode(node);
                break;
            case DIVISION:
                visitDivisionNode(node);
                break;
            case MOD:
                visitModNode(node);
                break;
            case NEGATIVE:
                visitNegativeNode(node);
                break;
            case LESS_THAN:
                visitLessThanNode(node);
                break;
            case LESS_EQUAL:
                visitLessEqualNode(node);
                break;
            case GREATER_THAN:
                visitGreaterThanNode(node);
                break;
            case GREATER_EQUAL:
                visitGreaterEqualNode(node);
                break;
            case EQUAL:
                visitEqualNode(node);
                break;
            case NOT_EQUAL:
                visitNotEqualNode(node);
                break;
            case AND:
                visitAndNode(node);
                break;
            case OR:
                visitOrNode(node);
                break;
            case NOT:
                visitNotNode(node);
                break;
            case READ_INTEGER:
                visitReadIntegerNode(node);
                break;
            case READ_LINE:
                visitReadLineNode(node);
                break;
            case NEW_IDENTIFIER:
                //todo
                break;
            case NEW_ARRAY:
                visitNewArrayNode(node);
                break;
            case ITOD:
                visitItoDNode(node);
                break;
            case DTOI:
                visitDtoINode(node);
                break;
            case ITOB:
                visitItoBNode(node);
                break;
            case BTOI:
                visitBtoINode(node);
                break;
            case LINE:
                visitLineNode(node);
                break;
            case FUNCTION:
                visitFunctionNode(node);
                break;
            case LVALUE:
                visitLValueNode(node);
                break;
            case IDENTIFIER:
                IdentifierNode idNode = (IdentifierNode) node;
                node.setSymbolInfo(new SymbolInfo(node, new IdentifierType(idNode.getValue())));
                node.getSymbolInfo().setDimensionArray(node.getChildren().size());
                break;
            case CALL:
                visitCallNode(node);
                break;
            case ACTUALS:
                visitAllChildren(node);
                break;
            case LITERAL:
                visitLiteralNode(node);
                break;
            case NULL_LITERAL:
                break;
            case EMPTY_NODE:
                break;
            default:
        }
    }


    /*..........*/
    private void visitStartNode(Node node) throws Exception {
        dataSegment = ".data";
        codeSegment += ".text\n" + "\t.globl main\n\n";
        codeSegment += "\tmain:\n";

        symbolTable.enterScope("global");
        visitAllChildren(node);

        codeSegment += "\t\t#END OF PROGRAM\n";
        codeSegment += "\t\tli $v0,10\n\t\tsyscall\n";

        System.out.println(dataSegment);
        System.out.println(codeSegment);
    }

    /*..........*/
    private void visitAllChildren(Node node) throws Exception {
        for (Node child : node.getChildren()) {
            visit(child);
        }
    }
    /*..........*/
    private void visitFunctionDeclarationNode(Node node) throws Exception {
        Node returnTypeNode = node.getChild(0); // type - identifier - void
        visit(returnTypeNode);
        SymbolInfo returnType = returnTypeNode.getSymbolInfo();

        IdentifierNode idNode = (IdentifierNode) node.getChild(1); // identifier node
        String funcName = idNode.getValue(); // function name
        Function function = new Function(funcName, returnType, symbolTable.getCurrentScope());
        if (functions.contains(function)) {
            throw new Exception(funcName + " function declared before");
        }
        functions.add(function);
        Function.currentFunction = function;

        String label = symbolTable.getCurrentScopeName() + "_" + funcName;
        codeSegment += "\t" + label + ":\n";
        symbolTable.enterScope(label);
        codeSegment += "\t\tsw $ra,0($sp)\n";
        Node argumentsNode = node.getChild(2);
        visit(argumentsNode);
        codeSegment += "\t\taddi $sp,$sp,4\n";
        Node statementsNode = node.getChild(3);
        visit(statementsNode);
        codeSegment += "\t\taddi $sp,$sp,-4\n";
        codeSegment += "\t\tlw $ra,0($sp)\n";
        codeSegment += "\t\tjr $ra\n";

        symbolTable.leaveCurrentScope();
        if (symbolTable.getCurrentScopeName().equals("global")) {
            function.setAccessMode(AccessMode.Public);
        } else {
            function.setAccessMode(Field.currentAccessMode);
            DefinedClass.currentClass.getMethods().add(function);
        }
    }
    /*..........*/
    private void visitClassDeclarationNode(Node node) throws Exception {
        IdentifierNode idNode = (IdentifierNode) node.getChild(0);
        String className = idNode.getValue();
        DefinedClass definedClass = new DefinedClass(className);
        if (classes.contains(definedClass))
            throw new Exception(className + " class declared before");
        DefinedClass.currentClass = definedClass;
        classes.add(definedClass);
        symbolTable.enterScope(className);
        visitAllChildren(node);
        if (node.getChild(node.getChildren().size() - 1).getNodeType().equals(NodeType.FIELDS)) {
            //visit(node.getChild(node.getChildren().size() - 1));
            DefinedClass.currentClass.setObjectSize(DefinedClass.currentClass.getFields().size() * 4);
        }
        symbolTable.leaveCurrentScope();
    }
    /*..........*/
    private void visitVariableDeclaration(Node node) throws Exception {
        setParentSymbolInfo(node, node.getChild(0));
        IdentifierNode idNode = (IdentifierNode) node.getChild(1);
        String fieldName = idNode.getValue();
        if (DefinedClass.currentClass != null) {
            if (symbolTable.getCurrentScopeName().equals(DefinedClass.currentClass.getName())) {
                // it is field not argument
                Field field = new Field(fieldName);
                field.setSymbolInfo(node.getSymbolInfo());
                field.setAccessMode(Field.getCurrentAccessMode());
                field.setDefinedClass(DefinedClass.currentClass);
                if (DefinedClass.currentClass.getFields().contains(field))
                    throw new Exception(fieldName + " declared before");
                else
                    DefinedClass.currentClass.getFields().add(field);
            }
        }
        if (DefinedClass.currentClass == null || !symbolTable.getCurrentScopeName().equals(DefinedClass.currentClass.getName())) {
            String varName = idNode.getValue();
            String label = symbolTable.getCurrentScopeName() + "_" + varName + " :";
            int dimensionArray = node.getSymbolInfo().getDimensionArray();
            if (!node.getChild(0).getNodeType().equals(NodeType.IDENTIFIER)) { // so it has primitive type
                Type typePrimitive = node.getSymbolInfo().getType();
                if (dimensionArray == 0 && !typePrimitive.getSignature().equals(".ascii"))
                    dataSegment += "\t" + label + " " + typePrimitive.getSignature() + " " + typePrimitive.getPrimitive().getInitialValue() + "\n";
                else
                    dataSegment += "\t" + label + " .word 0" + "\n";
            } else { // so it is an object of defined classes
                IdentifierNode typeNode = (IdentifierNode) node.getChild(0);
                String typeName = typeNode.getValue();
                DefinedClass definedClass = findClass(typeName);
                if (definedClass == null)
                    throw new Exception(typeName + " class not Declared");
                dataSegment += "\t" + label + "\t" + ".space" + "\t" + definedClass.getObjectSize() + "\n";
            }
            symbolTable.put(varName, node.getSymbolInfo());
        }

    }
    /*..........*/
    private void visitArgumentsNode(Node node) throws Exception {
        int argumentsLen = node.getChildren().size() * (-4);
        Function function = Function.currentFunction;
        if (argumentsLen < 0)
            codeSegment += "\t\taddi $sp,$sp," + argumentsLen + "\n";
        for (int i = argumentsLen / (-4); i >= 1; i--) {
            Node ArgumentNode = node.getChild(i - 1);
            Node variable = ArgumentNode.getChild(0);
            visit(variable);
            IdentifierNode idNode = (IdentifierNode) variable.getChild(1);
            String idName = idNode.getValue();
            SymbolInfo si = variable.getSymbolInfo();
            switch (si.getType().getAlign()) {
                case 1: //bool
                case 4: // int
                case 6: //String
                case 10:
                    //TODO
                    codeSegment += "\t\tla $a1, " + findNameOfId(idName) + '\n';
                    codeSegment += "\t\tlw $t1, 0($sp)\n";
                    codeSegment += "\t\tsw $t1, 0($a1)\n";
                    codeSegment += "\t\taddi $sp, $sp, " + 4 + "\n";
                    break;
                case 8: // float
                    codeSegment += "\t\tla $a1, " + findNameOfId(idName) + '\n';
                    codeSegment += "\t\tl.s $f1, 0($sp)\n";
                    codeSegment += "\t\ts.s $f1, 0($a1)\n";
                    codeSegment += "\t\taddi $sp, $sp, " + 4 + "\n";
                    break;
                default:
                    break;
            }
        }

    }
    /*..........*/
    private void visitBlockNode(Node node) throws Exception {
        if (node.getParent().getNodeType() != NodeType.FIELD_DECLARATION) {
            symbolTable.enterScope("block_" + blockIndex++);
            visitAllChildren(node);
            symbolTable.leaveCurrentScope();
        } else {
            visitAllChildren(node);
        }
    }

    /*..........*/
    private void visitIfNode(Node node) throws Exception {

        String ifFalseLabel = labelGenerator();

        String ifType;
        if (node.getChild(2).getNodeType().equals(NodeType.EMPTY_NODE)) {
            ifType = "if";
        } else if(node.getChild(2).getNodeType().equals(NodeType.ELSE_STATEMENT)){
            ifType = "if_else";
        }else
            ifType="invalid";

        Node expression = node.getChild(0);
        visit(expression);
        if (node.getChild(0).getSymbolInfo().getType().getAlign() == 1) {
            codeSegment += "\t\tbeq " + regs.get(8) + ", 0" + ", " + ifFalseLabel + "\n"; // $t0
        } else {
            throw new Exception("Invalid Expression in if expression");
        }

        Node ifStatement =node.getChild(1);
        visit(ifStatement);

        codeSegment += "\t\tj " + ifFalseLabel + "exit" + "\n";

        codeSegment += ifFalseLabel + ":\n";

        if (ifType.equals("if_else")) {
            Node elseNode = node.getChild(2);
            Node elseNodeStatement = elseNode.getChild(0);
            visit(elseNodeStatement);
        } else if (ifType.equals("invalid")) {
            throw new Exception("invalid if");
        }

        codeSegment += ifFalseLabel + "exit:\n";
    }
    /*..........*/

    /*..helping functions..*/
    private DefinedClass findClass(String name) {
        for (DefinedClass c : classes) {
            if (c.getName().equals(name))
                return c;
        }
        return null;
    }

    private void setParentSymbolInfo(Node node, Node child) throws Exception {
        visit(child);
        Type type = child.getSymbolInfo().getType();
        SymbolInfo si = new SymbolInfo(node, type);
        si.setDimensionArray(child.getSymbolInfo().getDimensionArray());
        node.setSymbolInfo(si);
    }

    private String findNameOfId(String id) {
        return symbolTable.getScopeNameOfIdentifier(id) + "_" + id;
    }

    private String labelGenerator() {
        return "L" + (++labelIndex);
    }

    /*..........*/

}
