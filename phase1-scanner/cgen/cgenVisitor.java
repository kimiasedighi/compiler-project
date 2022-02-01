package cgen;

import AST.IdentifierNode;
import AST.IdentifierType;
import AST.Node;
import AST.PrimitiveType;

public class cgenVisitor implements Visitor {
    String dataSegment;
    String codeSegment;

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
            case VARIABLE_PLUS_COMMA:
                //they are function arguments in function declaration
                visitArgumentsNode();
                break;
            case EXPRESSION_PLUS_COMMA:
                //they are expressions that we pass to functions when we call them
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
                visitStatementsNode(node);
                break;
            case STATEMENT:
                visitStatementNode(node);
                break;
            case IF_STATEMENT:
                visitIfNode(node);
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
}
