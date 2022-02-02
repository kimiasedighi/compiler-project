package cgen;

import AST.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class cgenVisitor implements Visitor {

    String dataSegment;
    String codeSegment;
    private SymbolTable symbolTable = new SymbolTable();
    private List<Function> functions = new ArrayList<>();
    public static List<DefinedClass> classes = new ArrayList<>();

    private int blockIndex;
    private int labelIndex; // for generating label
    private Stack<String> labels = new Stack<>(); // use for break

    private int tempRegsNumber = 8;
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

    private int tempfRegsNumber = 0;
    List<String> fregs = Arrays.asList(
            "$f0", "$f1", "$f2", "$f3", "$f4", "$f5", "$f6", "$f7", "$f8",
            "$f9", "$f10", "$f11", "$f12", "$f13", "$f14", "$f15", "$f16",
            "$f17", "$f18", "$f19", "$f20", "$f21", "$f22", "$f23", "$f24", "$f25",
            "$f26", "$f27", "$f28", "$f29", "$f30", "$f31"
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
                //visitAssignAddNode(node); //todo
                break;
            case ASSIGN_SUB:
                //visitAssignSubNode(node); //todo
                break;
            case ASSIGN_MUL:
                //visitAssignMullNode(node); //todo
                break;
            case ASSIGN_DIV:
                //visitAssignDivNode(node); //todo
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
        dataSegment = ".data \n\ttrue: .asciiz \"true\"\n\tfalse : .asciiz \"false\"\n\n";
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
        tempRegsNumber = 8;

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
            codeSegment += "\t\tbeq " + regs.get(tempRegsNumber) + ", 0" + ", " + ifFalseLabel + "\n"; // $t0
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
    private void visitWhileNode(Node node) throws Exception {
        String label = labelGenerator();
        labels.push(label);

        codeSegment += "\t\t" + label + ":" + "\n";
        Node expression = node.getChild(0);
        visit(expression);
        codeSegment += "\t\tbeq $t0, $zero exit" + label + "\n";

        Node statement = node.getChild(1);
        visit(statement);
        codeSegment += "\t\tj " + label + "\n";
        codeSegment += "\t\texit" + label + ":\n";

        labels.pop();
    }
    /*..........*/
    private void visitForNode(Node node) throws Exception {
        String label = labelGenerator();
        label += "F";
        labels.push(label);

        Node expression1; //init
        Node expression2; //condition
        Node expression3; //update
        Node statement;
        int children_size = node.getChildren().size();
        if (children_size == 4) {
            expression1=node.getChild(0);
            visit(expression1);
            codeSegment += "\t\t" + label + ":" + "\n";
            expression2=node.getChild(1);
            visit(expression2);
            codeSegment += "\t\tbeq $t0, $zero exit" + label + "\n";
            statement=node.getChild(2);
            visit(statement);
            codeSegment += "\t\t" + label + "update:\n";
            expression3=node.getChild(3);
            visit(expression3);
            codeSegment += "\t\tj " + label + "\n";
            codeSegment += "\t\texit" + label + ":\n";
        } else if (children_size == 3) {
            if (node.getChild(0).getChild(0).getNodeType().equals(NodeType.ASSIGN)) {
                expression1=node.getChild(0);
                visit(expression1);
                codeSegment += "\t\t" + label + ":" + "\n";
                expression2 =  node.getChild(1);
                visit(expression2);
                codeSegment += "\t\tbeq $t0, $zero exit" + label + "\n";
                statement = node.getChild(2);
                visit(statement);
                codeSegment += "\t\t" + label + "update:\n";
            } else {
                codeSegment += "\t\t" + label + ":" + "\n";
                expression2 = node.getChild(0);
                visit(expression2);
                codeSegment += "\t\tbeq $t0, $zero exit" + label + "\n";
                statement = node.getChild(1);
                visit(statement);
                codeSegment += "\t\t" + label + "update:\n";
                expression3=node.getChild(2);
                visit(expression3);
            }
            codeSegment += "\t\tj " + label + "\n";
            codeSegment += "\t\texit" + label + ":\n";
        } else if (children_size == 2) {
            codeSegment += "\t\t" + label + ":" + "\n";
            expression2=node.getChild(0);
            visit(expression2);
            codeSegment += "\t\tbeq $t0, $zero exit" + label + "\n";
            statement=node.getChild(1);
            visit(statement);
            codeSegment += "\t\t" + label + "update:\n";
            codeSegment += "\t\tj " + label + "\n";
            codeSegment += "\t\texit" + label + ":\n";
        }

        labels.pop();
    }

    /*..........*/

    private void visitReturnNode(Node node) throws Exception {
        Function method = Function.currentFunction;
        SymbolInfo returnType = method.getReturnType();
        visit(node.getChild(0));
        if (node.getChild(0).getChild(0).equals(NodeType.EMPTY_NODE)){
            // it doesn't return anything
            if (!returnType.getType().equals(PrimitiveType.VOID)){
                throw new Exception("Return type of " + method.getName() + " is incorrect");
            }
        }

        if (!isTypesEqual(returnType, node.getChild(0).getSymbolInfo()))
            throw new Exception("Return type of " + method.getName() + " is incorrect");
        codeSegment += "\t\taddi $sp,$sp,-4\n";
        codeSegment += "\t\tlw $ra,0($sp)\n";
        codeSegment += "\t\tjr $ra\n";
    }
    /*..........*/
    private void visitBreakNode(Node node) {
        codeSegment += "\t\tj exit" + labels.peek() + "\n";
    }
    /*..........*/
    private void visitContinueNode(Node node) throws Exception {
        if (labels.peek().charAt(labels.peek().length() - 1) == 'F') {
            codeSegment += "\t\tj " + labels.peek() + "update\n";
        } else {
            codeSegment += "\t\tj " + labels.peek() + "\n";
        }
    }
    /*..........*/
    private void visitPrintNode(Node node) throws Exception {
        Type exprType = PrimitiveType.INPUTSTRING;
        Node printExpressionsNode = node.getChild(0);
        for (Node child : printExpressionsNode.getChildren()) { //child is each expression in print
            visit(child);
            exprType = child.getSymbolInfo().getType();
            switch (exprType.getAlign()) {
                case 1: //bool
                    String generatedLabel = labelGenerator();
                    codeSegment += "\t\tli $v0, 1\n";
                    codeSegment += "\t\tbeq $t0, $zero, printFalse" + generatedLabel + "\n";
                    codeSegment +=
                            "\t\tla $t0, true\n" +
                                    "\t\tli $v0, 4\n" +
                                    "\t\tadd $a0, $t0, $zero\n" +
                                    "\t\tsyscall\n" +
                                    "\t\tb endPrintFalse" + generatedLabel + "\n" +
                                    "\tprintFalse" + generatedLabel + ":\n" +
                                    "\t\tla $t0, false\n" +
                                    "\t\tli $v0, 4\n" +
                                    "\t\tadd $a0, $t0, $zero\n" +
                                    "\t\tsyscall\n" +
                                    "\tendPrintFalse" + generatedLabel + ":\n";
                    break;
                case 4: //int
                    codeSegment += "\t\tli $v0, 1\n";
                    codeSegment += "\t\tadd $a0, $t0, $zero\n";
                    codeSegment += "\t\tsyscall\n";
                    break;
                case 6://string
                case 12:
                    codeSegment += "\t\tli $v0, 4\n";
                    codeSegment += "\t\tadd $a0, $t0, $zero\n";
                    codeSegment += "\t\tsyscall\n";
                    break;
                case 8://float
                    codeSegment += "\t\tli $v0, 2\n";
                    codeSegment += "\t\tmov.d\t$f12, $f0\n";
                    codeSegment += "\t\tsyscall\n";
                    break;
                default:
                    break;
            }
        }
        if (exprType.getAlign() != 12) {
            codeSegment += "\t\t#print new Line\n";
            codeSegment += "\t\taddi $a0, $0, 0xA\n\t\taddi $v0, $0, 0xB\n\t\tsyscall \n";
        }
    }
    /*..........*/
    private void visitExpressionNode(Node node) throws Exception {
        tempRegsNumber = 8;
        setParentSymbolInfo(node, node.getChild(0));
    }
    /*..........*/
    private void visitAssignNode(Node node) throws Exception {
        Node lvalue = node.getChild(0);
        setParentSymbolInfo(node, lvalue);
        SymbolInfo varType = node.getChild(0).getSymbolInfo();
        codeSegment += "\t\tla $a3, 0($a0) \n";

        Node expression = node.getChild(1);
        visit(expression);
        SymbolInfo exprType = expression.getSymbolInfo();
        if (exprType == null)
            throw new Exception("Assign Error");
        //TODO
        if (isTypesEqual(varType, exprType)) {
            switch (varType.getType().getAlign()) {
                case 6: //string
                case 1: //bool
                case 4: // int
                    codeSegment += "\t\tsw $t0, 0($a3)\n";
                    break;
                case 8: // float
                    codeSegment += "\t\ts.s $f0, 0($a3)\n";
                    break;
                case 10:
                    //todo
                    break;
                default:
                    break;
            }
        } else {
            throw new Exception("Type " + varType + " & " + exprType + " Doesn't Match");
        }
    }
    /*..........*/
    private void visitAdditionNode(Node node) throws Exception {
        Sub_ADD_Handler(node, "add");
    }
    /*..........*/
    private void visitSubtractionNode(Node node) throws Exception {
        Sub_ADD_Handler(node, "sub");
    }
    /*..........*/
    private void Sub_ADD_Handler(Node node, String type) throws Exception {

        setParentSymbolInfo(node, node.getChild(0));
        SymbolInfo first = node.getSymbolInfo();
        int firstType = first.getType().getAlign();

        int tempReg = firstType == 4 ? tempRegsNumber : tempfRegsNumber;
        List<String> reg = firstType == 4 ? regs : fregs;

        if (!(firstType == 4 || firstType == 8)) {
            throw new Exception("bad parameters for this " + type);
        }

        String op = firstType == 4 ? type + " " : type + ".s ";
        String op2 = firstType == 4 ? "move " : "mov.s ";
        String op3 = firstType == 4 ? "sw " : "s.s ";
        String op4 = firstType == 4 ? "lw " : "l.s ";

        codeSegment += "\t\t" + op2 + reg.get(tempReg + 1) + ", " + reg.get(tempReg) + "\n";
        codeSegment += "\t\t" + op3 + reg.get(tempReg + 1) + ", 0($sp)\n";
        codeSegment += "\t\taddi $sp, $sp, 4\n";

        setParentSymbolInfo(node, node.getChild(1));
        SymbolInfo second = node.getSymbolInfo();
        String secondType = second.getType().getSignature();

        codeSegment += "\t\taddi $sp, $sp, -4\n";
        codeSegment += "\t\t" + op4 + reg.get(tempReg + 1) + " 0($sp)\n";

        if (isTypesEqual(first, second)) {
            codeSegment += "\t\t" + op + reg.get(tempReg + 1) + ", " + reg.get(tempReg + 1) + ", " + reg.get(tempReg) + "\n";
        } else {
            throw new Exception("Type " + firstType + " & " + secondType + " are mismatched");
        }
        codeSegment += "\t\t" + op2 + reg.get(tempReg) + ", " + reg.get(tempReg + 1) + "\n";
    }
    /*..........*/
    private void visitModNode(Node node) throws Exception {
        Mul_Mod_Div_Handler(node, "mod");
    }
    /*..........*/
    private void visitDivisionNode(Node node) throws Exception {
        Mul_Mod_Div_Handler(node, "div");
    }
    /*..........*/
    private void visitMultiplicationNode(Node node) throws Exception {
        Mul_Mod_Div_Handler(node, "mul");
    }
    /*..........*/
    private void Mul_Mod_Div_Handler(Node node, String type) throws Exception {

        setParentSymbolInfo(node, node.getChild(0));
        SymbolInfo first = node.getSymbolInfo();
        int firstType = first.getType().getAlign();
        String main_type = type;
        int tempReg = firstType == 4 ? tempRegsNumber : tempfRegsNumber;
        List<String> reg = firstType == 4 ? regs : fregs;

        if (firstType != 4 && type.equals("mod")) {
            throw new Exception("bad parameters for mod operation");
        } else if (type.equals("mod")) {
            main_type = "div";
        }

        if (!(firstType == 4 || firstType == 8)) {
            throw new Exception("bad parameters for type " + type);
        }

        String op = firstType == 4 ? main_type + " " : main_type + ".s ";
        String op2 = firstType == 4 ? "move " : "mov.s ";
        String op3 = firstType == 4 ? "sw " : "s.s ";
        String op4 = firstType == 4 ? "lw " : "l.s ";

        codeSegment += "\t\t" + op2 + reg.get(tempReg + 1) + ", " + reg.get(tempReg) + "\n";
        codeSegment += "\t\t" + op3 + reg.get(tempReg + 1) + ", 0($sp)\n";
        codeSegment += "\t\taddi $sp, $sp, 4\n";

        setParentSymbolInfo(node, node.getChild(1));
        SymbolInfo second = node.getSymbolInfo();
        String secondType = second.getType().getSignature();

        codeSegment += "\t\taddi $sp, $sp, -4\n";
        codeSegment += "\t\t" + op4 + reg.get(tempReg + 1) + " 0($sp)\n";

        if (isTypesEqual(first, second)) {
            codeSegment += "\t\t" + op + reg.get(tempReg + 1) + ", " + reg.get(tempReg + 1) + ", " + reg.get(tempReg) + "\n";
            if (type.equals("mod")) {
                codeSegment += "\t\tmfhi $t1\n";
            }
        } else {
            throw new Exception("Type " + firstType + " & " + secondType + " are mismatched");
        }

        codeSegment += "\t\t" + op2 + reg.get(tempReg) + ", " + reg.get(tempReg + 1) + "\n";
    }
    /*..........*/
    private void visitNegativeNode(Node node) throws Exception {
        Node expression = node.getChild(0);
        setParentSymbolInfo(node, expression);
        int type = 4;
        if (expression.getChild(0).getNodeType() == NodeType.LVALUE) {
            IdentifierNode idNode = (IdentifierNode) expression.getChild(0).getChild(0);
            String varName = idNode.getValue();
            SymbolInfo varType = (SymbolInfo) symbolTable.get(varName);
            type = varType.getType().getAlign();
        } else if (expression.getChild(0).getNodeType() == NodeType.LITERAL) {
            Literal literalNode = (Literal) expression.getChild(0);
            type = literalNode.getType().getAlign();
        }

        if (type == 4) {
            codeSegment += "\t\tneg $t0, $t0\n";
        } else if (type == 8) {
            codeSegment += "\t\tneg.s $f0, $f0\n";
        }
    }
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

    private boolean isTypesEqual(SymbolInfo a, SymbolInfo b) {
        if (a.getType().getAlign() == b.getType().getAlign()) {
            if (a.getType().getSignature().equals(b.getType().getSignature())) {
                if (a.getDimensionArray() == b.getDimensionArray())
                    return a.getType().getPrimitive().equals(b.getType().getPrimitive());
            }
        }
        return false;
    }

    /*..........*/

}
