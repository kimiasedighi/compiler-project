package compiler.cgen;

import compiler.AST.*;

import java.util.*;

public class cgenVisitor implements Visitor {

    String dataSegment = "";
    String codeSegment = "";
    public String mipsCode = "";
    private SymbolTable symbolTable = new SymbolTable();
    private List<Function> functions = new ArrayList<>();
    public static List<DefinedClass> classes = new ArrayList<>();

    private int blockCounter;
    private int labelCounter; // for generating label
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

    @Override
    public void visit(Node node) throws Exception {
        switch (node.getNodeType()) {
            case START:
                startNode_visit(node);
                break;
            case INTEGER_TYPE:
                node.setTypeInfo(new Type(".word", 4));
                break;
            case DOUBLE_TYPE:
                node.setTypeInfo(new Type(".float", 8));
                break;
            case BOOLEAN_TYPE:
                node.setTypeInfo(new Type(".word", 1));
                break;
            case STRING_TYPE:
                node.setTypeInfo(new Type(".ascii", 6));
                break;
            case VOID_TYPE:
                node.setTypeInfo(new Type("void", 0));
                break;
            case EMPTY_ARRAY:
                break;
            case DECLARATIONS:
                visit_allChildren(node);
                break;
            case FUNCTION_DECLARATION:
                visit_functionDeclarationNode(node);
                break;
            case CLASS_DECLARATION:
                visit_classDeclarationNode(node);
                break;
            case VARIABLE_DECLARATION:
                visit_variableDeclarationNode(node);
                break;
            case VARIABLE_DECLARATIONS:
                visit_allChildren(node);
                break;
            case FIELD_DECLARATION:
                //todo
                visit_allChildren(node);
                break;
            case FIELDS:
                visit_allChildren(node);
                break;
            case ARGUMENT:
                visit_allChildren(node);
                break;
            case ARGUMENTS:
                visit_argumentsNode(node);
                break;
            case EXPRESSION_PLUS_COMMA://they are expressions that we pass to functions when we call them
                visit_allChildren(node);
                break;
            case FUNCTION_ACCESS:
                break;
            case PRIVATE_ACCESS:
                break;
            case PUBLIC_ACCESS:
                break;
            case BLOCK:
                visit_blockNode(node);
                break;
            case STATEMENTS:
                visit_allChildren(node);
                break;
            case STATEMENT:
                visit_allChildren(node);
                break;
            case IF_STATEMENT:
                visit_ifNode(node);
                break;
            case ELSE_STATEMENT:
                visit_allChildren(node);
                break;
            case WHILE_STATEMENT:
                visit_whileNode(node);
                break;
            case FOR_STATEMENT:
                visit_forNode(node);
                break;
            case EMPTY_STATEMENT:
                break;
            case RETURN_STATEMENT:
                visit_returnNode(node);
                break;
            case BREAK_STATEMENT:
                visit_breakNode();
                break;
            case CONTINUE_STATEMENT:
                visit_continueNode();
                break;
            case PRINT_STATEMENT:
                visit_printNode(node);
                break;
            case EXPRESSION_STATEMENT:
                visit_expressionNode(node);
                break;
            case ASSIGN:
                visit_assignNode(node);
                break;
            case ASSIGN_ADD:
                visit_assign_add(node);
                break;
            case ASSIGN_SUB:
                visit_assign_sub(node);
                break;
            case ASSIGN_MUL:
                visit_assign_mul(node);
                break;
            case ASSIGN_DIV:
                visit_assign_div(node);
                break;
            case THIS:
                break;
            case ADDITION:
                visit_additionNode(node);
                break;
            case SUBTRACTION:
                visit_subtractionNode(node);
                break;
            case MULTIPLICATION:
                visit_multiplicationNode(node);
                break;
            case DIVISION:
                visit_divisionNode(node);
                break;
            case MOD:
                visit_modNode(node);
                break;
            case NEGATIVE:
                visit_negativeNode(node);
                break;
            case LESS_THAN:
                visit_lessThanNode(node);
                break;
            case LESS_EQUAL:
                visit_lessEqualNode(node);
                break;
            case GREATER_THAN:
                visit_greaterThanNode(node);
                break;
            case GREATER_EQUAL:
                visit_greaterEqualNode(node);
                break;
            case EQUAL:
                visit_equalNode(node);
                break;
            case NOT_EQUAL:
                visit_notEqualNode(node);
                break;
            case AND:
                visit_andNode(node);
                break;
            case OR:
                visit_orNode(node);
                break;
            case NOT:
                visit_notNode(node);
                break;
            case READ_INTEGER:
                visit_readIntegerNode(node);
                break;
            case READ_LINE:
                visit_readLineNode(node);
                break;
            case NEW_IDENTIFIER: //todo
                break;
            case ITOD:
                visit_ItoDNode(node);
                break;
            case DTOI:
                //TODO
                break;
            case ITOB:
                //TODO
                break;
            case BTOI:
                visit_BtoINode(node);
                break;
            case LINE:
                //TODO
                break;
            case FUNCTION:
                //visitFunctionNode(node); //todo write
                break;
            case LVALUE:
                visit_lValueNode(node);
                break;
            case IDENTIFIER:
                visit_identifier(node);
                break;
            case CALL:
                visit_callNode(node);
                break;
            case ACTUALS:
                visit_allChildren(node);
                break;
            case LITERAL:
                visit_literalNode(node);
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
        try {
            dataSegment = ".data \n\ttrue: .asciiz \"true\"\n\tfalse : .asciiz \"false\"\n\n";
            codeSegment += ".text\n" + "\t.globl main\n\n";
            codeSegment += "\tmain:\n";

            symbolTable.scope_init("global");

            visit_allChildren(node);

            codeSegment += "\t\t#PROGRAM END\n";
            codeSegment += "\t\tli $v0,10\n\t\tsyscall\n";

            mipsCode = dataSegment + codeSegment;

//            System.out.println(mipsCode);
        } catch (Exception e) {
            mipsCode = e.getMessage();
            System.out.println(e.getMessage());
        }


    }

    /********** visit all child nodes of current node in a loop ***********/
    private void visit_allChildren(Node node) throws Exception {
        for (int i = 0; i < node.getChildren().size(); i++) {
            visit(node.getChild(i));
        }
    }

    /*************** visit function declaration signature *****************/
    private void visit_functionDeclarationNode(Node node) throws Exception {
        Node typeNode = node.getChild(0); // type - identifier - void
        visit(typeNode);
        Type returnType = typeNode.getTypeInfo();

        IdentifierNode idNode = (IdentifierNode) node.getChild(1); // identifier node
        String funcName = idNode.getName(); // function name
        List<Type> args = new ArrayList<>();
        for (Node n : node.getChild(2).getChildren()) {
            Node tempChild = n.getChild(0).getChild(0);
            switch (tempChild.getNodeType()) {
                case INTEGER_TYPE:
                    tempChild.setTypeInfo(new Type(".word", 4));
                    break;
                case DOUBLE_TYPE:
                    tempChild.setTypeInfo(new Type(".float", 8));
                    break;
                case BOOLEAN_TYPE:
                    tempChild.setTypeInfo(new Type(".word", 1));
                    break;
                case STRING_TYPE:
                    tempChild.setTypeInfo(new Type(".ascii", 6));
                    break;
                case VOID_TYPE:
                    tempChild.setTypeInfo(new Type("void", 0));
                    break;
            }
            args.add(tempChild.getTypeInfo());
        }


    Function function = new Function(funcName, returnType, symbolTable.getCurrentScope(), args);
    if(functions.contains(function))
    {
        throw new Exception(funcName + " function declared before");
    }
    functions.add(function);
    Function.currentFunction =function;

    String label = symbolTable.getCurrentScopeName() + "_" + funcName;
    codeSegment +="\t"+label +":\n";
        symbolTable.scope_init(label);
    codeSegment +="\t\tsw $ra,0($sp)\n";

    Node argumentsNode = node.getChild(2);
    visit(argumentsNode);

    codeSegment +="\t\taddi $sp,$sp,4\n";

    Node statementsNode = node.getChild(3);
    visit(statementsNode);

    codeSegment +="\t\taddi $sp,$sp,-4\n";
    codeSegment +="\t\tlw $ra,0($sp)\n";
    codeSegment +="\t\tjr $ra\n";

    symbolTable.scope_exit();
    if(symbolTable.getCurrentScopeName().equals("global"))
    {
        function.setAccessMode(AccessMode.Public);
    } else {
        function.setAccessMode(Field.currentAccessMode);
        DefinedClass.currentClass.getMethods().add(function);
    }
}

    /*********** visit class declaration and inside class scope ***********/
    private void visit_classDeclarationNode(Node node) throws Exception {
        IdentifierNode idNode = (IdentifierNode) node.getChild(0); //identifier node for class name
        String className = idNode.getName(); //class name
        DefinedClass definedClass = new DefinedClass(className); //add class name to defined classes

        if (classes.contains(definedClass)) //duplicate class name
            throw new Exception(className + " class declared before");

        DefinedClass.currentClass = definedClass; //scope handle
        classes.add(definedClass);
        symbolTable.scope_init(className); //add class to scopes

        visit_allChildren(node);

        // if class has fields, set its size accorfing to fields size
        if (node.getChild(node.getChildren().size() - 1).getNodeType().equals(NodeType.FIELDS)) {
            definedClass.setObjectSize(definedClass.getFields().size() * 4);
        }
        symbolTable.scope_exit();
    }

    /*********** visit when variable is declared with identifier **********/
    private void visit_variableDeclarationNode(Node node) throws Exception {
        setNodeTypeInfo(node, node.getChild(0));

        IdentifierNode idNode = (IdentifierNode) node.getChild(1);
        String fieldName = idNode.getName(); //variable name

        if (DefinedClass.currentClass != null) { //if we are in class scope
            if (symbolTable.getCurrentScopeName().equals(DefinedClass.currentClass.getName())) {
                // it is field not argument
                Field field = new Field(fieldName);
                field.setTypeInfo(node.getTypeInfo());
                field.setAccessMode(Field.getCurrentAccessMode());
                field.setDefinedClass(DefinedClass.currentClass);
                if (DefinedClass.currentClass.getFields().contains(field))
                    throw new Exception(fieldName + " declared before");
                else
                    DefinedClass.currentClass.getFields().add(field); //add variable to current class fields if not duplicate
            }
        }

        if (DefinedClass.currentClass == null || !symbolTable.getCurrentScopeName().equals(DefinedClass.currentClass.getName())) { //check scope

            String label = symbolTable.getCurrentScopeName() + "_" + fieldName + " :";

            if (!node.getChild(0).getNodeType().equals(NodeType.IDENTIFIER)) { // so it has primitive type
                Type type = node.getTypeInfo();
                if (!type.getSignature().equals(".ascii"))
                    dataSegment += "\t" + label + " " + type.getSignature() + " " + type.getInitialValue() + "\n";
                else
                    dataSegment += "\t" + label + " .word 0" + "\n";
            } else { // so it is an object of defined classes
                IdentifierNode typeNode = (IdentifierNode) node.getChild(0);
                String typeName = typeNode.getName();
                DefinedClass definedClass = findClass(typeName);
                if (definedClass == null)
                    throw new Exception(typeName + " class not Declared");
                dataSegment += "\t" + label + "\t" + ".space" + "\t" + definedClass.getObjectSize() + "\n";
            }

            symbolTable.add(fieldName, node.getTypeInfo()); //add variable to scope map
        }
    }

    /***************** visit function arguments with type *****************/
    private void visit_argumentsNode(Node node) throws Exception {
        int argumentsLen = node.getChildren().size();

        if (argumentsLen != 0)
            codeSegment += "\t\taddi $sp,$sp," + argumentsLen + "\n";

        for (int i = argumentsLen - 1; i >= 0; i--) {
            Node ArgumentNode = node.getChild(i);
            Node variable = ArgumentNode.getChild(0);

            visit(variable);

            IdentifierNode idNode = (IdentifierNode) variable.getChild(1);
            String idName = idNode.getName(); //get name of idnode of function arg
            Type t = variable.getTypeInfo();
            switch (t.getMemory()) {
                case 1: //bool
                case 4: // int
                case 6: //String
                    codeSegment += "\t\tla $a1, " + findNameOfId(idName) + '\n'
                            + "\t\tlw $t1, 0($sp)\n"
                            + "\t\tsw $t1, 0($a1)\n"
                            + "\t\taddi $sp, $sp, " + 4 + "\n";
                    break;
                case 8: // float
                    codeSegment += "\t\tla $a1, " + findNameOfId(idName) + '\n'
                            + "\t\tl.s $f1, 0($sp)\n"
                            + "\t\ts.s $f1, 0($a1)\n"
                            + "\t\taddi $sp, $sp, " + 4 + "\n";
                    break;
                default:
                    break;
            }
        }
    }

    /********************** visit for block scope *************************/
    private void visit_blockNode(Node node) throws Exception {
        // handle block scope
        if (node.getParent().getNodeType() != NodeType.FIELD_DECLARATION) {
            symbolTable.scope_init("block_" + blockCounter++);
            visit_allChildren(node);
            symbolTable.scope_exit();
        } else {
            visit_allChildren(node);
        }
    }

    /******************* visit and handle if or if else *******************/
    private void visit_ifNode(Node node) throws Exception {

        String elseLabel = generate_label();
        tempRegsNumber = 8;

        // check if or ifelse or invalid according to 2nd child
        String ifType;
        if (node.getChild(2).getNodeType().equals(NodeType.EMPTY_NODE)) {
            ifType = "if";
        } else if (node.getChild(2).getNodeType().equals(NodeType.ELSE_STATEMENT)) {
            ifType = "if_else";
        } else
            ifType = "invalid";

        Node expression = node.getChild(0);
        visit(expression);

        if (node.getChild(0).getTypeInfo().getMemory() == 1) { //boolean check
            codeSegment += "\t\tbeq " + regs.get(tempRegsNumber) + ", 0" + ", " + elseLabel + "\n"; // $t0
        } else {
            throw new Exception("Invalid Expression in if expression");
        }

        Node ifStatement = node.getChild(1); //statement inside if block
        visit(ifStatement);

        codeSegment += "\t\tj " + elseLabel + "exit" + "\n";

        codeSegment += elseLabel + ":\n";

        if (ifType.equals("if_else")) {
            Node elseNode = node.getChild(2);
            Node elseNodeStatement = elseNode.getChild(0); //statement inside else block
            visit(elseNodeStatement);
        } else if (ifType.equals("invalid")) {
            throw new Exception("invalid if");
        }

        codeSegment += elseLabel + "exit:\n";
    }

    /************************** visit while loop **************************/
    private void visit_whileNode(Node node) throws Exception {
        String label = generate_label();
        labels.push(label);

        codeSegment += "\t\t" + label + ":" + "\n";

        Node expression = node.getChild(0);
        visit(expression); //expression inside while paranthesis

        codeSegment += "\t\tbeq $t0, $zero exit" + label + "\n";

        Node statement = node.getChild(1);
        visit(statement); // statement in while block

        codeSegment += "\t\tj " + label + "\n";
        codeSegment += "\t\texit" + label + ":\n";

        labels.pop();
    }

    /******************* visit for loop with 4 types **********************/
    private void visit_forNode(Node node) throws Exception {
        String label = generate_label();
        label += "F";
        labels.push(label);

        Node expression1; //init
        Node expression2; //condition
        Node expression3; //update
        Node statement;

        int children_size = node.getChildren().size();
        if (children_size == 4) { //for ([1]i = 0; [2]i < n; [4]i++) [3]{}
            expression1 = node.getChild(0);
            visit(expression1);

            codeSegment += "\t\t" + label + ":" + "\n";

            expression2 = node.getChild(1);
            visit(expression2);

            codeSegment += "\t\tbeq $t0, $zero exit" + label + "\n";

            statement = node.getChild(2);
            visit(statement);

            codeSegment += "\t\t" + label + "update:\n";

            expression3 = node.getChild(3);
            visit(expression3);

            codeSegment += "\t\tj " + label + "\n";
            codeSegment += "\t\texit" + label + ":\n";
        } else if (children_size == 3) {
            if (node.getChild(0).getChild(0).getNodeType().equals(NodeType.ASSIGN)) {//for ([1]i = 0; [2]i < n;) [3]{}
                expression1 = node.getChild(0);
                visit(expression1);
                codeSegment += "\t\t" + label + ":" + "\n";

                expression2 = node.getChild(1);
                visit(expression2);
                codeSegment += "\t\tbeq $t0, $zero exit" + label + "\n";

                statement = node.getChild(2);
                visit(statement);
                codeSegment += "\t\t" + label + "update:\n";
            } else { //for (; [2]i < n; [4]i++) [3]{}
                codeSegment += "\t\t" + label + ":" + "\n";
                expression2 = node.getChild(0);
                visit(expression2);

                codeSegment += "\t\tbeq $t0, $zero exit" + label + "\n";
                statement = node.getChild(1);
                visit(statement);

                codeSegment += "\t\t" + label + "update:\n";
                expression3 = node.getChild(2);
                visit(expression3);
            }
            codeSegment += "\t\tj " + label + "\n";
            codeSegment += "\t\texit" + label + ":\n";
        } else if (children_size == 2) {//for (; [2]i < n;) [3]{}
            codeSegment += "\t\t" + label + ":" + "\n";
            expression2 = node.getChild(0);
            visit(expression2);

            codeSegment += "\t\tbeq $t0, $zero exit" + label + "\n";
            statement = node.getChild(1);
            visit(statement);

            codeSegment += "\t\t" + label + "update:\n";
            codeSegment += "\t\tj " + label + "\n";
            codeSegment += "\t\texit" + label + ":\n";
        }

        labels.pop();
    }

    /********************** visit function return *************************/
    private void visit_returnNode(Node node) throws Exception {
        Function method = Function.currentFunction; //get current function
        Type returnType = method.getReturnType();

        visit(node.getChild(0));

        if (node.getChild(0).getChild(0).getNodeType().equals(NodeType.EMPTY_NODE)) {
            // it doesn't return anything
            if (!returnType.getSignature().equals("void")) {
                throw new Exception("Return type of " + method.getName() + " is incorrect");
            }
        }

        if (!checkTypeEqual(returnType, node.getChild(0).getTypeInfo())) //check return type with function type
            throw new Exception("Return type of " + method.getName() + " is incorrect");

        codeSegment += "\t\taddi $sp,$sp,-4\n";
        codeSegment += "\t\tlw $ra,0($sp)\n";
        codeSegment += "\t\tjr $ra\n";

    }

    /**** visit and handle break with removing label stack's top label ****/
    private void visit_breakNode() {
        codeSegment += "\t\tj exit" + labels.peek() + "\n";
    }

    /************* visit and handle continue with label stack *************/
    private void visit_continueNode() {
        if (labels.peek().charAt(labels.peek().length() - 1) == 'F') {
            codeSegment += "\t\tj " + labels.peek() + "update\n";
        } else {
            codeSegment += "\t\tj " + labels.peek() + "\n";
        }
    }

    /**************** visit printing ****************/
    private void visit_printNode(Node node) throws Exception {
        Type exprType = new Type(".space", 12);
        Node printExpressionsNode = node.getChild(0);

        for (Node child : printExpressionsNode.getChildren()) { //child is each expression in print
            visit(child);
            exprType = child.getTypeInfo();
            switch (exprType.getMemory()) {
                case 1: //bool
                    String generatedLabel = generate_label();
                    codeSegment += "\t\tli $v0, 1\n"
                            + "\t\tbeq $t0, $zero, printFalse" + generatedLabel + "\n"
                            + "\t\tla $t0, true\n"
                            + "\t\tli $v0, 4\n"
                            + "\t\tadd $a0, $t0, $zero\n"
                            + "\t\tsyscall\n"
                            + "\t\tb endPrintFalse" + generatedLabel + "\n"
                            + "\tprintFalse" + generatedLabel + ":\n"
                            + "\t\tla $t0, false\n"
                            + "\t\tli $v0, 4\n"
                            + "\t\tadd $a0, $t0, $zero\n"
                            + "\t\tsyscall\n"
                            + "\tendPrintFalse" + generatedLabel + ":\n";
                    break;
                case 4: //int
                    codeSegment += "\t\tli $v0, 1\n"
                            + "\t\tadd $a0, $t0, $zero\n"
                            + "\t\tsyscall\n";
                    break;
                case 6://string
                case 12:
                    codeSegment += "\t\tli $v0, 4\n"
                            + "\t\tadd $a0, $t0, $zero\n"
                            + "\t\tsyscall\n";
                    break;
                case 8://float
                    codeSegment += "\t\tli $v0, 2\n"
                            + "\t\tmov.d\t$f12, $f0\n"
                            + "\t\tsyscall\n";
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

    /************************* visit expression ***************************/
    private void visit_expressionNode(Node node) throws Exception {
        tempRegsNumber = 8;
        setNodeTypeInfo(node, node.getChild(0));
    }

    /********** visit and handle assignment with type checking ************/
    private void visit_assignNode(Node node) throws Exception {

        IdentifierNode lvalue = (IdentifierNode) node.getChild(0);
        Type varType = symbolTable.get(lvalue.getName());
        setNodeTypeInfo(node, lvalue);

        codeSegment += "\t\tla $a3, 0($a0) \n";

        Node expression = node.getChild(1);
        visit(expression);
        Type exprType = expression.getTypeInfo();

        if (exprType == null)
            throw new Exception("Assign Error");

        if (checkTypeEqual(varType, exprType)) {
            switch (varType.getMemory()) {
                case 6: //string
                case 1: //bool
                case 4: // int
                    codeSegment += "\t\tsw $t0, 0($a3)\n";
                    break;
                case 8: // float
                    codeSegment += "\t\ts.s $f0, 0($a3)\n";
                    break;
                default:
                    break;
            }
        } else {
            throw new Exception("Type " + varType.getSignature() + " & " + exprType.getSignature() + " Doesn't Match");
        }
    }


    private void visit_additionNode(Node node) throws Exception {
        Sub_Add(node, "add");
    }
    private void visit_subtractionNode(Node node) throws Exception {
        Sub_Add(node, "sub");
    }


    /******************* handle add and sub for visit *********************/
    private void Sub_Add(Node node, String type) throws Exception {
        Node firstOperand = node.getChild(0);
        setNodeTypeInfo(node, firstOperand);
        Type first = node.getTypeInfo();

        int firstTypeMem = first.getMemory();

        int tempReg = firstTypeMem == 4 ? tempRegsNumber : tempfRegsNumber;
        List<String> reg = firstTypeMem == 4 ? regs : fregs;

        if (!(firstTypeMem == 4 || firstTypeMem == 8)) {
            throw new Exception("bad parameters for this " + type);
        }

        String op = firstTypeMem == 4 ? type + " " : type + ".s ";
        String op2 = firstTypeMem == 4 ? "move " : "mov.s ";
        String op3 = firstTypeMem == 4 ? "sw " : "s.s ";
        String op4 = firstTypeMem == 4 ? "lw " : "l.s ";

        codeSegment += "\t\t" + op2 + reg.get(tempReg + 1) + ", " + reg.get(tempReg) + "\n";
        codeSegment += "\t\t" + op3 + reg.get(tempReg + 1) + ", 0($sp)\n";
        codeSegment += "\t\taddi $sp, $sp, 4\n";

        Node secondOperand = node.getChild(1);
        setNodeTypeInfo(node, secondOperand);
        Type second = node.getTypeInfo();

        codeSegment += "\t\taddi $sp, $sp, -4\n";
        codeSegment += "\t\t" + op4 + reg.get(tempReg + 1) + " 0($sp)\n";

        if (checkTypeEqual(first, second)) {
            codeSegment += "\t\t" + op + reg.get(tempReg + 1) + ", " + reg.get(tempReg + 1) + ", " + reg.get(tempReg) + "\n";
        } else {
            throw new Exception("Type " + first.getSignature() + " & " + second.getSignature() + " are mismatched");
        }
        codeSegment += "\t\t" + op2 + reg.get(tempReg) + ", " + reg.get(tempReg + 1) + "\n";
    }

    /********* visit identifier after it was declared (get value) *********/
    private void visit_identifier(Node node) throws Exception {
        try {
            IdentifierNode identifierNode = (IdentifierNode) node;
            Type type = symbolTable.get(identifierNode.getName());
            ((IdentifierNode) node).setType(new TypeNode(NodeType.IDENTIFIER, type));
            node.setTypeInfo(type);
        } catch (Exception ex) { // identifier does not exist
            throw new Exception("identifier " + ((IdentifierNode) node).getName() + " not found!");
        }
    }


    private void visit_modNode(Node node) throws Exception {
        Mul_Mod_Div(node, "mod");
    }
    private void visit_divisionNode(Node node) throws Exception {
        Mul_Mod_Div(node, "div");
    }
    private void visit_multiplicationNode(Node node) throws Exception {
        Mul_Mod_Div(node, "mul");
    }

    /*************** handle mul and mod and div for visit ****************/
    private void Mul_Mod_Div(Node node, String type) throws Exception {

        setNodeTypeInfo(node, node.getChild(0));
        Type first = node.getTypeInfo();
        int firstType = first.getMemory();
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

        setNodeTypeInfo(node, node.getChild(1));
        Type second = node.getTypeInfo();
        String secondType = second.getSignature();

        codeSegment += "\t\taddi $sp, $sp, -4\n";
        codeSegment += "\t\t" + op4 + reg.get(tempReg + 1) + " 0($sp)\n";

        if (checkTypeEqual(first, second)) {
            codeSegment += "\t\t" + op + reg.get(tempReg + 1) + ", " + reg.get(tempReg + 1) + ", " + reg.get(tempReg) + "\n";
            if (type.equals("mod")) {
                codeSegment += "\t\tmfhi $t1\n";
            }
        } else {
            throw new Exception("Type " + firstType + " & " + secondType + " are mismatched");
        }

        codeSegment += "\t\t" + op2 + reg.get(tempReg) + ", " + reg.get(tempReg + 1) + "\n";
    }

    /************* visit negative before integer or double ***************/
    private void visit_negativeNode(Node node) throws Exception {
        Node expression = node.getChild(0);
        setNodeTypeInfo(node, expression);
        int type = 4;

        //check integer or double
        if (expression.getChild(0).getNodeType() == NodeType.LVALUE) {
            IdentifierNode idNode = (IdentifierNode) expression.getChild(0).getChild(0);
            String varName = idNode.getName();
            Type varType = symbolTable.get(varName);
            type = varType.getMemory();
        } else if (expression.getChild(0).getNodeType() == NodeType.LITERAL) {
            Literal literalNode = (Literal) expression.getChild(0);
            type = literalNode.getTypeInfo().getMemory();
        }

        if (type == 4) {
            codeSegment += "\t\tneg $t0, $t0\n";
        } else if (type == 8) {
            codeSegment += "\t\tneg.s $f0, $f0\n";
        }
    }

    private void visit_lessThanNode(Node node) throws Exception {
        compareOp(node, "lt");
    }
    private void visit_lessEqualNode(Node node) throws Exception {
        compareOp(node, "le");
    }
    private void visit_greaterThanNode(Node node) throws Exception {
        compareOp(node, "gt");
    }
    private void visit_greaterEqualNode(Node node) throws Exception {
        compareOp(node, "ge");
    }
    private void visit_equalNode(Node node) throws Exception {
        compareOp(node, "eq");
    }
    private void visit_notEqualNode(Node node) throws Exception {
        compareOp(node, "ne");
    }

    /****************** handle logical operations *******************/
    private void compareOp(Node node, String type) throws Exception {
        // for ==, >, >=, <=, <
        setNodeTypeInfo(node, node.getChild(0));
        Type first = node.getTypeInfo();
        int firstType = first.getMemory();

        if (!(node.getChild(0).getTypeInfo().getMemory() == 4 || node.getChild(0).getTypeInfo().getMemory() == 8) && (!(type.equals("ne") || type.equals("eq")))) {
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

        setNodeTypeInfo(node, node.getChild(1));
        Type second = node.getTypeInfo();
        String secondType = second.getSignature();

        codeSegment += "\t\taddi $sp, $sp, -4\n";
        codeSegment += "\t\t" + op4 + reg.get(tempReg + 1) + " 0($sp)\n";

        if (checkTypeEqual(node.getChild(0).getTypeInfo(), node.getChild(1).getTypeInfo())) {
            if (node.getChild(0).getTypeInfo().getMemory() == 8) {
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
            node.setTypeInfo(new Type(".word", 1));
        } else {
            throw new Exception("Type " + firstType + " & " + secondType + " are mismatched");
        }
        codeSegment += "\t\t" + op2 + reg.get(tempReg) + ", " + reg.get(tempReg + 1) + "\n";
    }


    private void visit_andNode(Node node) throws Exception {
        logicalOp(node, "and");
    }
    private void visit_orNode(Node node) throws Exception {
        logicalOp(node, "and");
    }


    private void logicalOp(Node node, String op) throws Exception {
        // for and & or
        setNodeTypeInfo(node, node.getChild(0));
        if (!(node.getChild(0).getTypeInfo().getMemory() == 1)) {
            throw new Exception("Invalid type for " + node.getNodeType().toString() + " operation");
        }

        codeSegment += "\t\t" + "move $t1" + ", " + "$t0" + "\n";
        codeSegment += "\t\t" + "sw " + "$t1" + ", 0($sp)\n";
        codeSegment += "\t\taddi $sp, $sp, 4\n";

        setNodeTypeInfo(node, node.getChild(1));

        codeSegment += "\t\taddi $sp, $sp, -4\n";
        codeSegment += "\t\t" + "lw " + "$t1" + ", 0($sp)\n";

        if (checkTypeEqual(node.getChild(0).getTypeInfo(), node.getChild(1).getTypeInfo())) {
            codeSegment += "\t\t" + op + " $t1, $t1, $t0\n";
        }

        codeSegment += "\t\t" + "move $t0, $t1\n";
    }

    /****************** not before boolean visit *******************/
    private void visit_notNode(Node node) throws Exception {
        setNodeTypeInfo(node, node.getChild(0));

        if (!(node.getChild(0).getTypeInfo().getMemory() == 1)) {
            throw new Exception("Invalid type for " + node.getNodeType().toString() + " operation");
        }

        codeSegment += "\t\t" + "xori $t0, $t0, 1\n";
    }

    /***************** visit read integer (type int) ***************/
    private void visit_readIntegerNode(Node node) {
        Type t = new Type(".word", 4);
        node.setTypeInfo(t);
        codeSegment += "\t\tli $v0, 5\n\t\tsyscall\n"
                    + "\t\tmove $t0, $v0\n\n";
    }

    /*************** visit readline (string of line) ***************/
    private void visit_readLineNode(Node node) {
        String label = "userInput_" + generate_label();
        dataSegment += "\t" + label + ":\t.space\t600\n";

        node.setTypeInfo(new Type(".space", 12));

        codeSegment += "\t\tli $v0, 8\n\t\tla $a0, " + label + "\n\t\tli $a1, 600\n\t\tsyscall\n"
                    + "\t\tmove $t0, $a0\n\n";
    }

    /****************** integer to double visit ********************/
    private void visit_ItoDNode(Node node) throws Exception {
        setNodeTypeInfo(node, node.getChild(0));
        if (!(node.getChild(0).getTypeInfo().getMemory() == 4)) { //check if int
            throw new Exception("Invalid type for " + node.getNodeType().toString() + " operation");
        }
        node.setTypeInfo(new Type(".float", 8)); //convert type to double

        codeSegment += "\t\tmtc1 $t0 $f0\n"
                    + "\t\tcvt.s.w $f0 $f0\n";
    }

    /***************** boolean to integer visit ********************/
    private void visit_BtoINode(Node node) throws Exception {
        setNodeTypeInfo(node, node.getChild(0));
        if (!(node.getChild(0).getTypeInfo().getMemory() == 1)) {
            throw new Exception("Invalid type for " + node.getNodeType().toString() + " operation");
        }
        node.setTypeInfo(new Type(".word", 4));
    }

    /*************** left value of expression visit ****************/
    private void visit_lValueNode(Node node) throws Exception {
        visit(node.getChild(0));
        if (node.getChildren().size() == 1) { //id
            IdentifierNode idNode = (IdentifierNode) node.getChild(0);
            String varName = idNode.getName();
            Type varType = symbolTable.get(varName);

            node.setTypeInfo(varType);
            switch (varType.getMemory()) {
                case 1: //bool
                case 4: // int
                case 6: //String
                    codeSegment += "\t\tla $a0, " + findNameOfId(varName) + '\n'
                                +  "\t\tlw $t0, 0($a0)\n";
                    break;
                case 8: // float
                    codeSegment += "\t\tla $a0, " + findNameOfId(varName) + '\n'
                                +  "\t\tl.s $f0, 0($a0)\n";
                    break;
                default:
                    break;
            }
        }
    }

    /****** function call visit (checking function and args) *******/
    private void visit_callNode(Node node) throws Exception {
        String varName;
        Function method = null;
        int argNumber = 0;

        for (Node child : node.getChildren()) {
            if (child.getNodeType().equals(NodeType.IDENTIFIER)) {
                IdentifierNode idNode = (IdentifierNode) child;
                varName = idNode.getName();
                method = findFunction(varName);
                if (method == null)
                    throw new Exception(varName + " function doesn't exist");
            }
            if (child.getNodeType().equals(NodeType.ACTUALS)) {

                for (Node childChild : child.getChild(0).getChildren()) {
                    visit(childChild);
                    Type si = childChild.getTypeInfo();
                    if (!checkTypeEqual(si, method.getArgumentsType().get(argNumber)))
                        throw new Exception("types doesn't match");

                    argNumber++;
                    codeSegment += "\t\tsw $t0, 0($sp)\n"
                                +  "\t\taddi $sp, $sp, " + 4 + "\n";
                }
            }
        }

        if (argNumber != method.getArgumentsType().size())
            throw new Exception("expected " + method.getArgumentsType().size() + " args but " + argNumber + " passed");

        codeSegment += "\t\tjal " + method.getScope().getName() + "_" + method.getName() + "\n"
                    + "\t\taddi $sp, $sp, " + (argNumber) * (-4) + "\n";

        node.setTypeInfo(method.getReturnType());
    }


    private void visit_literalNode(Node node) {
        Literal literalNode = (Literal) node;
        node.setTypeInfo(literalNode.getType());
        switch (literalNode.getType().getMemory()) {
            case 6: //string
                String str = ((StringLiteralNode) literalNode).getValue();
                str = str.replace("\\t", "\\\\t");
                str = str.replace("\\n", "\\\\n");
                String str_raw = str.substring(1, str.length() - 1);

                String label;
                if (!stringLiterals.keySet().contains(str_raw)) {
                    label = "StringLiteral_" + stringLiterals.keySet().size() + 1;
                    stringLiterals.put(str_raw, label);
                    dataSegment += "\t" + label + ": .asciiz " + str + "\n";
                } else
                    label = stringLiterals.get(str_raw);
                codeSegment += "\t\tla $t0, " + label + "\n";
                node.setTypeInfo(new Type(".ascii", 6));
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
    private void visit_assign_add(Node node) throws Exception {
        Node assign=new BaseNode(NodeType.ASSIGN);
        assign.addChild(node.getChild(0));
        Node expr=new ExpressionNode();
        expr.addChild(new BaseNode(NodeType.ADDITION));
        expr.getChild(0).addChild(node.getChild(0));
        expr.getChild(0).addChild(node.getChild(1));

        assign.addChild(expr);
        visit_assignNode(assign);
    }
    private void visit_assign_sub(Node node) throws Exception {
        Node assign=new BaseNode(NodeType.ASSIGN);
        assign.addChild(node.getChild(0));
        Node expr=new ExpressionNode();
        expr.addChild(new BaseNode(NodeType.SUBTRACTION));
        expr.getChild(0).addChild(node.getChild(0));
        expr.getChild(0).addChild(node.getChild(1));

        assign.addChild(expr);
        visit_assignNode(assign);
    }
    private void visit_assign_mul(Node node) throws Exception {
        Node assign=new BaseNode(NodeType.ASSIGN);
        assign.addChild(node.getChild(0));
        Node expr=new ExpressionNode();
        expr.addChild(new BaseNode(NodeType.MULTIPLICATION));
        expr.getChild(0).addChild(node.getChild(0));
        expr.getChild(0).addChild(node.getChild(1));

        assign.addChild(expr);
        visit_assignNode(assign);
    }
    private void visit_assign_div(Node node) throws Exception {
        Node assign=new BaseNode(NodeType.ASSIGN);
        assign.addChild(node.getChild(0));
        Node expr=new ExpressionNode();
        expr.addChild(new BaseNode(NodeType.DIVISION));
        expr.getChild(0).addChild(node.getChild(0));
        expr.getChild(0).addChild(node.getChild(1));

        assign.addChild(expr);
        visit_assignNode(assign);
    }

    /*..helping functions..*/
    private DefinedClass findClass(String name) {
        for (DefinedClass c : classes) {
            if (c.getName().equals(name))
                return c;
        }
        return null;
    }

    private void setNodeTypeInfo(Node node, Node child) throws Exception {
        visit(child);
        Type type = child.getTypeInfo();
        node.setTypeInfo(type);
    }

    private String findNameOfId(String id) {
        return symbolTable.getScopeNameOfIdentifier(id) + "_" + id;
    }

    /***** generate label for scope****/
    private String generate_label() {
        return "L" + (++labelCounter);
    }

    private boolean checkTypeEqual(Type a, Type b) {
        if (b.getSignature().equals(".space") && a.getSignature().equals(".ascii") && a.getMemory() == 6)
            return true;
        if (a.getMemory() == b.getMemory()) {
            if (a.getSignature().equals(b.getSignature())) {
                return true;
            }
        }
        return false;
    }

    private Function findFunction(String varName) {
        Function method = null;
        for (Function function : functions) {
            if (function.getName().equals(varName)) {
                for (Scope scope : symbolTable.getScopeList()) {
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
