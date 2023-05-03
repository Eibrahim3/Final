package Final;
import Final.Token;
import java.util.List;

public class Parser {
    private final List<Token> tokens;
    private int currentTokenIndex;

    public Parser(List<Token> tokens) {
        this.tokens = tokens;
        this.currentTokenIndex = 0;
    }
    private Node parseAssignmentStatement(Token identifierToken) {
        expectToken(Token.Type.ASSIGN);
        Node expressionNode = parseExpressionFromOutside();
        expectToken(Token.Type.SEMICOLON);
        return new Node("AssignmentStatement", new Node(identifierToken), expressionNode);
    }

    private Node parseDeclarationStatement() {
        Token currentToken = getCurrentToken();
        Node declarationNode = null;
        switch (currentToken.getType()) {
            case VAR:
                declarationNode = parseVariableDeclarationStatement();
                break;
            case FUNCTION:
                declarationNode = parseFunctionDeclarationStatement();
                break;
            default:
                // Invalid token at declaration level
                throw new RuntimeException("Invalid token: " + currentToken.getText());
        }
        return declarationNode;
    }

    private Node parseVariableDeclarationStatement() {
        expectToken(Token.Type.VAR);
        Node variableNode = new Node("Variable");
        Token identifierToken = expectToken(Token.Type.IDENTIFIER);
        variableNode.addChild(new Node(identifierToken));
        if (getCurrentToken().getType() == Token.Type.ASSIGN) {
            getNextToken();
            Node valueNode = parseExpressionFromOutside();
            variableNode.addChild(valueNode);
        }
        expectToken(Token.Type.SEMICOLON);
        return new Node("VariableDeclarationStatement", variableNode);
    }

    private Node parseFunctionDeclarationStatement() {
        expectToken(Token.Type.FUNCTION);
        Token identifierToken = expectToken(Token.Type.IDENTIFIER);
        Node functionNameNode = new Node(identifierToken);
        expectToken(Token.Type.LEFT_PAREN);
        Node parameterListNode = parseParameterList();
        expectToken(Token.Type.RIGHT_PAREN);
        Node codeBlockNode = parseCodeBlock();
        return new Node("FunctionDeclarationStatement", functionNameNode, parameterListNode, codeBlockNode);
    }

    private Node parseParameterList() {
        Node parameterListNode = new Node("ParameterList");
        while (getCurrentToken().getType() == Token.Type.IDENTIFIER) {
            Token identifierToken = expectToken(Token.Type.IDENTIFIER);
            parameterListNode.addChild(new Node(identifierToken));
            if (getCurrentToken().getType() == Token.Type.COMMA) {
                getNextToken();
            } else {
                break;
            }
        }
        return parameterListNode;
    }

    public Node parse() {
        Node rootNode = new Node("Program");
        while (currentTokenIndex < tokens.size()) {
            Node statementNode = parseStatement();
            if (statementNode != null) {
                rootNode.addChild(statementNode);
            }
        }
        return rootNode;
    }
   
    private Node parseStatement() {
        Token currentToken = getCurrentToken();
        if (currentToken == null) {
            return null;
        }
        Node statementNode = null;
        
        switch (currentToken.getType()) {
            case LEFT_BRACE:
                statementNode = parseCodeBlock();
                break;
            case IF:
            case SWITCH:
            case WHILE:
            case DO:
                statementNode = parseSelectionOrLoopStatement();
                break;
            case IDENTIFIER:
                Token nextToken = getNextToken();
                if (nextToken != null && nextToken.getType() == Token.Type.ASSIGN) {
                    statementNode = parseAssignmentStatement(currentToken);
                } else {
                    statementNode = parseFunctionCall(new Node(currentToken));
                }
                break;
            case VAR:
            case FUNCTION:
                statementNode = parseDeclarationStatement();
                break;
            default:
                // Invalid token at statement level
                throw new RuntimeException("Invalid token: " + currentToken.getText());
        }
        return statementNode;
    }

    private Node parseCodeBlock() {
        expectToken(Token.Type.LEFT_BRACE);
        Node codeBlockNode = new Node("CodeBlock");
        while (getCurrentToken().getType() != Token.Type.RIGHT_BRACE) {
            Node statementNode = parseStatement();
            if (statementNode != null) {
                codeBlockNode.addChild(statementNode);
            }
        }
        expectToken(Token.Type.RIGHT_BRACE);
        return codeBlockNode;
    }
    private Node parseIfStatement() {
        expectToken(Token.Type.IF);
        expectToken(Token.Type.LEFT_PAREN);
        Node conditionNode = parseExpressionFromOutside();
        expectToken(Token.Type.RIGHT_PAREN);
        Node ifStatementNode = parseStatement();
        Node elseStatementNode = null;
        if (getCurrentToken().getType() == Token.Type.ELSE) {
            getNextToken();
            elseStatementNode = parseStatement();
        }
        return new Node("IfStatement", conditionNode, ifStatementNode, elseStatementNode);
    }

    private Node parseSwitchStatement() {
        expectToken(Token.Type.SWITCH);
        expectToken(Token.Type.LEFT_PAREN);
        Node expressionNode = parseExpressionFromOutside();
        expectToken(Token.Type.RIGHT_PAREN);
        expectToken(Token.Type.LEFT_BRACE);
        Node switchStatementNode = new Node("SwitchStatement", expressionNode);
        while (getCurrentToken().getType() == Token.Type.CASE) {
            getNextToken();
            Node caseExpressionNode = parseExpressionFromOutside();
            expectToken(Token.Type.COLON);
            Node caseStatementNode = parseStatement();
            switchStatementNode.addChild(new Node("CaseClause", caseExpressionNode, caseStatementNode));
        }
        if (getCurrentToken().getType() == Token.Type.DEFAULT) {
            getNextToken();
            expectToken(Token.Type.COLON);
            Node defaultStatementNode = parseStatement();
            switchStatementNode.addChild(new Node("DefaultClause", defaultStatementNode));
        }
        expectToken(Token.Type.RIGHT_BRACE);
        return switchStatementNode;
    }

    private Node parseWhileStatement() {
        expectToken(Token.Type.WHILE);
        expectToken(Token.Type.LEFT_PAREN);
        Node conditionNode = parseExpressionFromOutside();
        expectToken(Token.Type.RIGHT_PAREN);
        Node whileStatementNode = parseStatement();
        return new Node("WhileStatement", conditionNode, whileStatementNode);
    }

    private Node parseDoWhileStatement() {
        expectToken(Token.Type.DO);
        Node doStatementNode = parseStatement();
        expectToken(Token.Type.WHILE);
        expectToken(Token.Type.LEFT_PAREN);
        Node conditionNode = parseExpressionFromOutside();
        expectToken(Token.Type.RIGHT_PAREN);
        expectToken(Token.Type.SEMICOLON);
        return new Node("DoWhileStatement", conditionNode, doStatementNode);
    }

    public Node parseExpressionFromOutside() {
        return parseExpressionFromOutside();
    }

    private Node parseSelectionOrLoopStatement() {
        Token currentToken = getCurrentToken();
        Node statementNode;
        switch (currentToken.getType()) {
            case IF:
                statementNode = parseIfStatement();
                break;
            case SWITCH:
                statementNode = parseSwitchStatement();
                break;
            case WHILE:
                statementNode = parseWhileStatement();
                break;
            case DO:
                statementNode = parseDoWhileStatement();
                break;
            default:
                // Invalid token at statement level
                throw new RuntimeException("Invalid token: " + currentToken.getText());
        }
        return statementNode;
    }
                // Invalid
 private Node parseFunctionCall(Node functionNameNode) {
	 expectToken(Token.Type.LEFT_PAREN);
	 Node argumentListNode = new Node("ArgumentList");
	 while (getCurrentToken().getType() != Token.Type.RIGHT_PAREN) {
	     Node argumentNode = parseExpressionFromOutside();
	     argumentListNode.addChild(argumentNode);
	     if (getCurrentToken().getType() == Token.Type.COMMA) {
	         getNextToken();
	     } else {
	         break;
	     }
	 }
	 expectToken(Token.Type.RIGHT_PAREN);
	 return new Node("FunctionCall", functionNameNode, argumentListNode);
 }

 private Token getCurrentToken() {
 if (currentTokenIndex < tokens.size()) {
 return tokens.get(currentTokenIndex);
 } else {
 return null;
 }
 }

 private Token getNextToken() {
 if (currentTokenIndex < tokens.size() - 1) {
 return tokens.get(++currentTokenIndex);
 } else {
   return null;
 }
 }

 private Token expectToken(Token.Type expectedType) {
 Token currentToken = getCurrentToken();
 if (currentToken.getType() == expectedType) {
 getNextToken();
 return currentToken;
 } else {
 throw new RuntimeException("Expected token type: " + expectedType + ", but found: " + currentToken.getType());
 }
 }

 private Token expectToken(Token.Type expectedType1, Token.Type expectedType2) {
 Token currentToken = getCurrentToken();
 if (currentToken.getType() == expectedType1 || currentToken.getType() == expectedType2) {
 getNextToken();
 return currentToken;
 } else {
 throw new RuntimeException("Expected token type: " + expectedType1 + " or " + expectedType2 + ", but found: " + currentToken.getType());
 }
 }
 }

