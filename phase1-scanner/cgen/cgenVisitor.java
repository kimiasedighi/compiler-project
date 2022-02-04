package cgen;

import AST.*;

import java.util.*;

public class cgenVisitor implements Visitor {

    String dataSegment="";
    String codeSegment="";
    public  String mipsCode="";
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

    private int tempLabelCounter = 0;
    private int arrayNumbers = 0;

    private HashMap<String, String> stringLiterals = new HashMap<>();
    private int tempLiteralCounter = 0;
    private int DtoItoBLabel = 0;
    @Override
    public void visit(Node node) throws Exception{
        switch (node.getNodeType()){
            case START:
                startNode_visit(node);
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
                allChildren_visit(node);
                break;
            case FUNCTION_DECLARATION:
                functionDeclarationNode_visit(node);
                break;
            case CLASS_DECLARATION:
                visitClassDeclarationNode(node);
                break;
            case VARIABLE_DECLARATION:
                visitVariableDeclaration(node);
                break;
            case VARIABLE_DECLARATIONS:
                allChildren_visit(node);
                break;
            case FIELD_DECLARATION:
                //todo
                allChildren_visit(node);
                break;
            case FIELDS:
                allChildren_visit(node);
                break;
//            case VARIABLE_PLUS_COMMA://they are function arguments in function declaration
//                visitArgumentsNode(node);
//                break;
            case ARGUMENT:
                allChildren_visit(node);
                break;
            case ARGUMENTS:
                visitArgumentsNode(node);
                break;
            case EXPRESSION_PLUS_COMMA://they are expressions that we pass to functions when we call them
                //todo
                allChildren_visit(node);
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
                allChildren_visit(node);
                break;
            case STATEMENT:
                allChildren_visit(node);
                break;
            case IF_STATEMENT:
                visitIfNode(node);
                break;
            case ELSE_STATEMENT:
                allChildren_visit(node);
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
                //visitAssignAddNode(node); //todo write
                break;
            case ASSIGN_SUB:
                //visitAssignSubNode(node); //todo write
                break;
            case ASSIGN_MUL:
                //visitAssignMullNode(node); //todo write
                break;
            case ASSIGN_DIV:
                //visitAssignDivNode(node); //todo write
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
            case NEW_IDENTIFIER: //todo
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
                //visitLineNode(node); //todo write
                break;
            case FUNCTION:
                //visitFunctionNode(node); //todo write
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
                allChildren_visit(node);
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

    /********************** visit function for START **********************/
    private void startNode_visit(Node node) throws Exception {
        dataSegment = ".data \n\ttrue: .asciiz \"true\"\n\tfalse : .asciiz \"false\"\n\n";
        codeSegment += ".text\n" + "\t.global main\n\n";
        codeSegment += "\tmain:\n";

        symbolTable.enterScope("global");
        allChildren_visit(node);

        codeSegment += "\t\t#PROGRAM END\n";
        codeSegment += "\t\tli $v0,10\n\t\tsyscall\n";

        mipsCode = dataSegment + codeSegment;

        System.out.println(mipsCode);
    }

    /********** visit all child nodes of current node in a loop ***********/
    private void allChildren_visit(Node node) throws Exception {
        for (int i = 0; i < node.getChildren().size(); i++) {
            visit(node.getChild(i));
        }
    }


    /*..........*/
    private void functionDeclarationNode_visit(Node node) throws Exception {
        Node typeNode = node.getChild(0); // type - identifier - void
        visit(typeNode);
        SymbolInfo returnType = typeNode.getSymbolInfo();

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
        allChildren_visit(node);
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
        //test
        idNode.setSymbolInfo(node.getSymbolInfo());
        //
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
            switch (si.getType().getMemory()) {
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
            allChildren_visit(node);
            symbolTable.leaveCurrentScope();
        } else {
            allChildren_visit(node);
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
        if (node.getChild(0).getSymbolInfo().getType().getMemory() == 1) {
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
            switch (exprType.getMemory()) {
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
        if (exprType.getMemory() != 12) {
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
        Node lvalue =  node.getChild(0);
        setParentSymbolInfo(node, lvalue);
        SymbolInfo varType = lvalue.getSymbolInfo();
        codeSegment += "\t\tla $a3, 0($a0) \n";
        Node expression = node.getChild(1);
        visit(expression);
        SymbolInfo exprType = expression.getSymbolInfo();
        if (exprType == null)
            throw new Exception("Assign Error");
      //  if (isTypesEqual(varType, exprType)) {
            switch (varType.getType().getMemory()) {
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
       // } else {
          //  throw new Exception("Type " + varType + " & " + exprType + " Doesn't Match");
       // }
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
        int firstType = first.getType().getMemory();

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
        int firstType = first.getType().getMemory();
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
            type = varType.getType().getMemory();
        } else if (expression.getChild(0).getNodeType() == NodeType.LITERAL) {
            Literal literalNode = (Literal) expression.getChild(0);
            type = literalNode.getType().getMemory();
        }

        if (type == 4) {
            codeSegment += "\t\tneg $t0, $t0\n";
        } else if (type == 8) {
            codeSegment += "\t\tneg.s $f0, $f0\n";
        }
    }
    /*..........*/
    private void visitLessThanNode(Node node) throws Exception {
        LogicalOp1(node, "lt");
    }
    /*..........*/
    private void visitLessEqualNode(Node node) throws Exception {
        LogicalOp1(node, "le");
    }
    /*..........*/
    private void visitGreaterThanNode(Node node) throws Exception {
        LogicalOp1(node, "gt");
    }
    /*..........*/
    private void visitGreaterEqualNode(Node node) throws Exception {
        LogicalOp1(node, "ge");
    }
    /*..........*/
    private void visitEqualNode(Node node) throws Exception {
        LogicalOp1(node, "eq");
    }
    /*..........*/
    private void visitNotEqualNode(Node node) throws Exception {
        LogicalOp1(node, "ne");
    }
    /*..........*/
    private void LogicalOp1(Node node, String type) throws Exception {
        // for ==, >,...
        setParentSymbolInfo(node, node.getChild(0));
        SymbolInfo first = node.getSymbolInfo();
        int firstType = first.getType().getMemory();

        if (!(node.getChild(0).getSymbolInfo().getType().getMemory() == 4 || node.getChild(0).getSymbolInfo().getType().getMemory() == 8) && (!(type.equals("ne") || type.equals("eq")))) {
            throw new Exception("Invalid type for " + node.getNodeType().toString() + " operation");
        }

        int tempReg = firstType != 8 ? tempRegsNumber : tempfRegsNumber;
        List<String> reg = firstType != 8 ? regs : fregs;

        String op = firstType != 8 ? "s" + type + " " : "c." + type + ".s ";
        String op2 = firstType != 8 ? "move " : "mov.s ";
        String op3 = firstType != 8 ? "sw " : "swc1 ";
        String op4 = firstType != 8 ? "lw " : "lwc1 ";

        codeSegment += "\t\t" + op2 + reg.get(tempReg + 1) + ", " + reg.get(tempReg) + "\n";
        codeSegment += "\t\t" + op3 + reg.get(tempReg + 1) + ", 0($sp)\n";
        codeSegment += "\t\taddi $sp, $sp, 4\n";

        setParentSymbolInfo(node, node.getChild(1));
        SymbolInfo second = node.getSymbolInfo();
        String secondType = second.getType().getSignature();

        codeSegment += "\t\taddi $sp, $sp, -4\n";
        codeSegment += "\t\t" + op4 + reg.get(tempReg + 1) + " 0($sp)\n";

        if (isTypesEqual(node.getChild(0).getSymbolInfo(), node.getChild(1).getSymbolInfo())) {
            if (node.getChild(0).getSymbolInfo().getType().getMemory() == 8) {
                switch (op) {
                    case "c.gt.s ":
                        codeSegment += "\t\t" + "c.lt.s " + reg.get(tempReg) + ", " + reg.get(tempReg + 1) + "\n";
                        break;
                    case "c.ge.s ":
                        codeSegment += "\t\t" + "c.le.s " + reg.get(tempReg) + ", " + reg.get(tempReg + 1) + "\n";
                        break;
                    case "c.ne.s ":
                        codeSegment += "\t\t" + "c.eq.s " + reg.get(tempReg) + ", " + reg.get(tempReg + 1) + "\n";
                        break;
                    default:
                        codeSegment += "\t\t" + op + reg.get(tempReg + 1) + ", " + reg.get(tempReg) + "\n";
                }

                codeSegment += "\t\tbc1f L_CondFalse" + tempLabelCounter + "\n";
                if (!op.equals("c.ne.s ")) {
                    codeSegment += "\t\tli $t0 1\n";
                } else {
                    codeSegment += "\t\tli $t0 0\n";
                }
                codeSegment += "\t\tj L_CondEnd" + tempLabelCounter + "\n";
                if (!op.equals("c.ne.s ")) {
                    codeSegment += "\t\tL_CondFalse" + tempLabelCounter + " : li $t0 0\n";
                } else {
                    codeSegment += "\t\tL_CondFalse" + tempLabelCounter + ": li $t0 1\n";
                }
                codeSegment += "\t\tL_CondEnd" + tempLabelCounter++ + ":\n";
            } else {
                codeSegment += "\t\t" + op + reg.get(tempReg + 1) + ", " + reg.get(tempReg + 1) + ", " + reg.get(tempReg) + "\n";
            }
            node.getSymbolInfo().setType(PrimitiveType.BOOL);
        } else {
            throw new Exception("Type " + firstType + " & " + secondType + " are mismatched");
        }
        codeSegment += "\t\t" + op2 + reg.get(tempReg) + ", " + reg.get(tempReg + 1) + "\n";
    }
    /*..........*/
    private void visitAndNode(Node node) throws Exception {
        LogicalOp2(node, "and");
    }
    /*..........*/
    private void visitOrNode(Node node) throws Exception {
        LogicalOp2(node, "and");
    }
    /*..........*/
    private void LogicalOp2(Node node, String op) throws Exception {
        // for and & or
        setParentSymbolInfo(node, node.getChild(0));
        if (!(node.getChild(0).getSymbolInfo().getType().getMemory() == 1)) {
            throw new Exception("Invalid type for " + node.getNodeType().toString() + " operation");
        }

        codeSegment += "\t\t" + "move $t1" + ", " + "$t0" + "\n";
        codeSegment += "\t\t" + "sw " + "$t1" + ", 0($sp)\n";
        codeSegment += "\t\taddi $sp, $sp, 4\n";

        setParentSymbolInfo(node, node.getChild(1));

        codeSegment += "\t\taddi $sp, $sp, -4\n";
        codeSegment += "\t\t" + "lw " + "$t1" + ", 0($sp)\n";

        if (isTypesEqual(node.getChild(0).getSymbolInfo(), node.getChild(1).getSymbolInfo())) {
            codeSegment += "\t\t" + op + " $t1, $t1, $t0\n";
        }

        codeSegment += "\t\t" + "move $t0, $t1\n";
    }
    /*..........*/
    private void visitNotNode(Node node) throws Exception {
        setParentSymbolInfo(node, node.getChild(0));
        if (!(node.getChild(0).getSymbolInfo().getType().getMemory() == 1)) {
            throw new Exception("Invalid type for " + node.getNodeType().toString() + " operation");
        }
        codeSegment += "\t\t" + "xori $t0, $t0, 1\n";
    }
    /*..........*/
    private void visitReadIntegerNode(Node node) {
        SymbolInfo si = new SymbolInfo(node, PrimitiveType.INT);
        node.setSymbolInfo(si);
        codeSegment += "\t\tli $v0, 5\n\t\tsyscall\n";
        codeSegment += "\t\tmove $t0, $v0\n\n";
    }
    /*..........*/
    private void visitReadLineNode(Node node) {
        String label = "userInput_" + labelGenerator();
        dataSegment += "\t" + label + ":\t.space\t600\n";
        SymbolInfo si = new SymbolInfo(node, PrimitiveType.INPUTSTRING);
        node.setSymbolInfo(si);
        codeSegment += "\t\tli $v0, 8\n\t\tla $a0, " + label + "\n\t\tli $a1, 600\n\t\tsyscall\n";
        codeSegment += "\t\tmove $t0, $a0\n\n";
    }
    /*..........*/
    private void visitNewArrayNode(Node node) throws Exception {

        int literalNumber = ((IntegerLiteralNode) node.getChild(0).getChild(0)).getValue();

        setParentSymbolInfo(node, node.getChild(1));
        node.getSymbolInfo().setDimensionArray(node.getSymbolInfo().getDimensionArray() + 1);
        if (literalNumber <= 0)
            throw new Exception("array size must be greater than zero");
        String label = symbolTable.getCurrentScopeName() + "_NEW_ARRAY_" + arrayNumbers;
        arrayNumbers++;
        dataSegment += "\t" + label + ": .space " + (literalNumber + 1) * 4 + "\n";
        codeSegment += "\t\tla $t0, " + label + "\n";
        codeSegment += "\t\tli $t2, " + literalNumber + "\n";
        codeSegment += "\t\tsw $t2, 0($t0)\n";
    }
    /*..........*/
    private void visitItoDNode(Node node) throws Exception {
        setParentSymbolInfo(node, node.getChild(0));
        if (!(node.getChild(0).getSymbolInfo().getType().getMemory() == 4)) {
            throw new Exception("Invalid type for " + node.getNodeType().toString() + " operation");
        }
        node.getSymbolInfo().setType(PrimitiveType.DOUBLE);

        codeSegment += "\t\tmtc1 $t0 $f0\n";
        codeSegment += "\t\tcvt.s.w $f0 $f0\n";

    }
    /*..........*/
    private void visitDtoINode(Node node) throws Exception {

        setParentSymbolInfo(node, node.getChild(0));
        if (!(node.getChild(0).getSymbolInfo().getType().getMemory() == 8)) {
            throw new Exception("Invalid type for " + node.getNodeType().toString() + " operation");
        }

        node.getSymbolInfo().setType(PrimitiveType.INT);

        codeSegment += "\t\ts.s $f1, 0($sp)\n";
        codeSegment += "\t\taddi $sp, $sp, 4\n";
        codeSegment += "\t\ts.s $f2, 0($sp)\n";
        codeSegment += "\t\taddi $sp, $sp, 4\n";

        codeSegment += "\t\tmov.s $f1, $f0\n";
        codeSegment += "\t\tcvt.w.s $f1, $f1\n";

        codeSegment += "\t\tmfc1 $t0 $f1\n";
        codeSegment += "\t\tmtc1 $t0 $f1\n";
        codeSegment += "\t\tcvt.s.w $f1 $f1\n";

        codeSegment += "\t\tsub.s $f1, $f0, $f1\n";


        dataSegment += "\t" + symbolTable.getCurrentScopeName() + "_temp" + tempLiteralCounter + ": .float " + "0.5" + "\n";
        codeSegment += "\t\tla $a0, " + symbolTable.getCurrentScopeName() + "_temp" + (tempLiteralCounter++) + '\n';
        codeSegment += "\t\tl.s $f2, 0($a0)\n";

        codeSegment += "\t\tc.eq.s $f1 $f2\n";
        codeSegment += "\t\tbc1t " + "half_DtoI" + DtoItoBLabel + "\n";
        codeSegment += "\t\tbc1f " + "nhalf_DtoI" + DtoItoBLabel + "\n";

        codeSegment += "half_DtoI" + DtoItoBLabel + ":\n";
        codeSegment += "\t\tceil.w.s $f0 $f0\n";
        codeSegment += "\t\tmfc1 $t0 $f0\n";
        codeSegment += "\t\tj exit_DtoI" + DtoItoBLabel + "\n";

        codeSegment += "nhalf_DtoI" + DtoItoBLabel + ":\n";

        dataSegment += "\t" + symbolTable.getCurrentScopeName() + "_temp" + tempLiteralCounter + ": .float " + "-0.5" + "\n";
        codeSegment += "\t\tla $a0, " + symbolTable.getCurrentScopeName() + "_temp" + (tempLiteralCounter++) + '\n';
        codeSegment += "\t\tl.s $f2, 0($a0)\n";

        codeSegment += "\t\tc.eq.s $f1 $f2\n";
        codeSegment += "\t\tbc1f " + "else_DtoI" + DtoItoBLabel + "\n";
        codeSegment += "\t\tcvt.w.s $f0 $f0\n";
        codeSegment += "\t\tmfc1 $t0 $f0\n";
        codeSegment += "\t\tj exit_DtoI" + DtoItoBLabel + "\n";

        codeSegment += "else_DtoI" + DtoItoBLabel + ":\n";

        codeSegment += "\t\tround.w.s $f0 $f0\n";
        codeSegment += "\t\tmfc1 $t0 $f0\n";

        codeSegment += "exit_DtoI" + (DtoItoBLabel++) + ":\n";

        codeSegment += "\t\taddi $sp, $sp, -4\n";
        codeSegment += "\t\tl.s $f2, 0($sp)\n";
        codeSegment += "\t\taddi $sp, $sp, -4\n";
        codeSegment += "\t\tl.s $f1, 0($sp)\n";
    }
    /*..........*/
    private void visitItoBNode(Node node) throws Exception {
        setParentSymbolInfo(node, node.getChild(0));
        if (!(node.getChild(0).getSymbolInfo().getType().getMemory() == 4)) {
            throw new Exception("Invalid type for " + node.getNodeType().toString() + " operation");
        }
        codeSegment += "\t\tbeq $t0 ,0 ItoB" + DtoItoBLabel + "\n";

        codeSegment += "\t\tli $t0, 1\n";
        codeSegment += "\t\tj exit_ItoB" + DtoItoBLabel + "\n";

        codeSegment += "ItoB" + DtoItoBLabel + ":\n";
        codeSegment += "\t\tli $t0, 0\n";

        codeSegment += "exit_ItoB" + (DtoItoBLabel++) + ":\n";

        node.getSymbolInfo().setType(PrimitiveType.BOOL);
    }
    /*..........*/
    private void visitBtoINode(Node node) throws Exception {
        setParentSymbolInfo(node, node.getChild(0));
        if (!(node.getChild(0).getSymbolInfo().getType().getMemory() == 1)) {
            throw new Exception("Invalid type for " + node.getNodeType().toString() + " operation");
        }
        node.getSymbolInfo().setType(PrimitiveType.INT);
    }
    /*..........*/
    private void visitLValueNode(Node node) throws Exception {
        visit(node.getChild(0));
        if (node.getChildren().size() == 1) { //id
            IdentifierNode idNode = (IdentifierNode) node.getChild(0);
            String varName = idNode.getValue();
            SymbolInfo varType = (SymbolInfo) symbolTable.get(varName);
            SymbolInfo si = new SymbolInfo(node, varType.getType());
            si.setDimensionArray(varType.getDimensionArray());
            node.setSymbolInfo(si);
            switch (varType.getType().getMemory()) {
                case 1: //bool
                case 4: // int
                case 6: //String
                    codeSegment += "\t\tla $a0, " + findNameOfId(varName) + '\n';
                    codeSegment += "\t\tlw $t0, 0($a0)\n";
                    break;
                case 8: // float
                    codeSegment += "\t\tla $a0, " + findNameOfId(varName) + '\n';
                    codeSegment += "\t\tl.s $f0, 0($a0)\n";
                    break;
                //todo
                default:
                    break;
            }
        } else {
            if (node.getChild(1).getNodeType().equals(NodeType.IDENTIFIER)) {
                //TODO
            } else {
                visit(node.getChild(0));
                codeSegment += "\t\tmove $a3, $t0\n";
                codeSegment += "\t\tmove $s4, $a0\n";
                SymbolInfo varType = node.getChild(0).getSymbolInfo();
                visit(node.getChild(1));
                SymbolInfo varType2 = node.getChild(1).getSymbolInfo();
                if (varType2.getType().getMemory() == 4) {//int
                    if (varType.getDimensionArray() > 0) {
                        codeSegment += "\t\tli $t4, 4\n";
                        codeSegment += "\t\taddi $t0, $t0, 1\n";
                        codeSegment += "\t\tlw $t2, 0($a3)\n";
                        codeSegment += "\t\tblt $t2, $t0, runtime_error\n";
                        codeSegment += "\t\tmul $t0, $t0, $t4\n";
                        codeSegment += "\t\tadd $a0, $a3, $t0\n";
                        codeSegment += "\t\tlw $t0, 0($a0)\n";
                    } else
                        throw new Exception("error in array assign - type is not array");
                } else
                    throw new Exception("error in array assign - index array");

                SymbolInfo si = new SymbolInfo(node, varType.getType());
                si.setDimensionArray(varType.getDimensionArray() - 1);
                node.setSymbolInfo(si);
            }
        }

    }
    /*..........*/
    private void visitCallNode(Node node) throws Exception {
        String varName;
        Function method = null;
        int argNumber = 0;
        for (Node child : node.getChildren()) {
            if (child.getNodeType().equals(NodeType.IDENTIFIER)) {
                IdentifierNode idNode = (IdentifierNode) child;
                varName = idNode.getValue();
                method = findFunction(varName);
                if (method == null)
                    throw new Exception(varName + " function doesn't exist");
            }
            if (child.getNodeType().equals(NodeType.ACTUALS)) {

                for (Node childChild : child.getChild(0).getChildren()) {
                    visit(childChild);
                    SymbolInfo si = childChild.getSymbolInfo();
                    if (!isTypesEqual(si, method.getArgumentsType().get(argNumber)))
                        throw new Exception("types doesn't match");

                    argNumber++;
                    switch (si.getType().getMemory()) {
                        case 1: //bool
                        case 4: // int
                        case 6: //String
                        case 10:
                            //TODO
                            codeSegment += "\t\tsw $t0, 0($sp)\n";
                            codeSegment += "\t\taddi $sp, $sp, " + 4 + "\n";
                            break;
                        case 8: // float
                            codeSegment += "\t\tsw $t0, 0($sp)\n";
                            codeSegment += "\t\taddi $sp, $sp, " + 4 + "\n";
                            break;
                        default:
                            break;
                    }
                }

            }
        }
        if (argNumber != method.getArgumentsType().size())
            throw new Exception("expected " + method.getArgumentsType().size() + " args but " + argNumber + " passed");
        codeSegment += "\t\tjal " + method.getScope().getName() + "_" + method.getName() + "\n";
        codeSegment += "\t\taddi $sp, $sp, " + (argNumber) * (-4) + "\n";

        node.setSymbolInfo(method.getReturnType());
    }
    /*..........*/
    private void visitLiteralNode(Node node) {
        Literal literalNode = (Literal) node;
        node.setSymbolInfo(new SymbolInfo(node, literalNode.getType()));
        switch (literalNode.getType().getMemory()) {
            case 6: //string
                String str = ((StringLiteralNode) literalNode).getValue();
                System.out.println(str);
                str = str.replace("\\t","\\\\t");
                str = str.replace("\\n","\\\\n");
                String str_raw = str.substring(1, str.length() - 1);
                String label = "";
                if (!stringLiterals.keySet().contains(str_raw)) {
                    label = "StringLiteral_" + stringLiterals.keySet().size() + 1;
                    stringLiterals.put(str_raw, label);
                    dataSegment += "\t" + label + ": .asciiz " + str + "\n";
                } else
                    label = stringLiterals.get(str_raw);
                codeSegment += "\t\tla $t0, " + label + "\n";
                node.setSymbolInfo(new SymbolInfo(node, PrimitiveType.STRING));
                break;
            case 1: //bool
                String bool_type = node.toString().equals("true") ? "1" : "0";
                codeSegment += "\t\tli " + regs.get(tempRegsNumber) + ", " + bool_type + "\n";
                break;
            case 4: //int
                codeSegment += "\t\tli " + regs.get(tempRegsNumber) + ", " + node + "\n";
                break;
            case 8: //double
                dataSegment += "\t" + symbolTable.getCurrentScopeName() + "_temp" + tempLiteralCounter + ": .float " + node + "\n";
                codeSegment += "\t\tla $a0, " + symbolTable.getCurrentScopeName() + "_temp" + (tempLiteralCounter++) + '\n';
                codeSegment += "\t\tl.s $f0, 0($a0)\n";
                break;
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
        if (a.getType().getMemory() == b.getType().getMemory()) {
            if (a.getType().getSignature().equals(b.getType().getSignature())) {
               if (a.getDimensionArray() == b.getDimensionArray())
                    return a.getType().getPrimitive().equals(b.getType().getPrimitive());
            }
        }
        return false;
    }

    private Function findFunction(String varName) {
        Function method = null;
        for (Function function : functions) {
            if (function.getName().equals(varName)) {
                for (Scope scope : symbolTable.getScopes()) {
                    if (scope.equals(function.getScope())) {
                        method = function;
                        break;
                    }
                }
                if (method != null)
                    break;
            }
        }
        return method;
    }

}
