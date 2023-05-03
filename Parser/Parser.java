package Final;
import Final.Token;
import java.util.List;
//This statement declares a new public class called Parser. The class contains instance variables, methods, and constructors that define the behavior of the parser.
public class Parser {
	//This statement declares a private final instance variable of type List<Token> called tokens. 
	//The tokens variable stores a list of tokens that the parser will parse.
    private final List<Token> tokens;
    private int currentTokenIndex;
//This statement declares a public constructor for the Parser class that takes a List<Token> parameter called tokens.
//The constructor initializes the tokens instance variable and sets the currentTokenIndex variable to 0.
    public Parser(List<Token> tokens) {
        this.tokens = tokens;
        this.currentTokenIndex = 0;
    }
    //This statement declares a private method called parseAssignmentStatement that takes a Token parameter called identifierToken and returns a Node object. 
    //The method parses an assignment statement and returns a new Node object that represents the statement.
    private Node parseAssignmentStatement(Token identifierToken) {
        expectToken(Token.Type.ASSIGN);
        Node expressionNode = parseExpressionFromOutside();
        expectToken(Token.Type.SEMICOLON);
        return new Node("AssignmentStatement", new Node(identifierToken), expressionNode);
    }
//This statement declares a private method called parseDeclarationStatement that returns a Node object. 
//The method parses a declaration statement and returns a new Node object that represents the statement.
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
//This statement declares a private method called parseVariableDeclarationStatement that returns a Node object. 
//The method parses a variable declaration statement and returns a new Node object that represents the statement.
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
//This statement declares a private method called parseFunctionDeclarationStatement that returns a Node object. 
//The method parses a function declaration statement and returns a new Node object that represents the statement.
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
//This statement declares a private method called parseParameterList that returns a Node object. 
//The method parses a parameter list and returns a new Node object that represents the list.
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
//This statement declares a public method called parse that returns a Node object. 
//The method parses the list of tokens and returns a new Node object that represents the entire program.
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
 //This statement declares a private method called parseStatement that returns a Node object. 
//The method parses a statement and returns a new Node object that represents the statement.
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
//This statement declares a private method called parseCodeBlock that returns a Node object. 
//The method parses a code block and returns a new Node object that represents the block.
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
 //This statement declares a private method called parseIfStatement that returns a Node object. 
//The method parses an if statement and returns a new Node object that represents the statement.
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
    //This statement declares a private method called parseSwitchStatement that returns a Node object. 
    //The method parses a switch statement and returns a new Node object that represents the statement.
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
//This statement declares a private method called parseWhileStatement that returns a Node object. 
//The method parses a while statement and returns a new Node object that represents the statement.
    private Node parseWhileStatement() {
        expectToken(Token.Type.WHILE);
        expectToken(Token.Type.LEFT_PAREN);
        Node conditionNode = parseExpressionFromOutside();
        expectToken(Token.Type.RIGHT_PAREN);
        Node whileStatementNode = parseStatement();
        return new Node("WhileStatement", conditionNode, whileStatementNode);
    }
//This statement declares a private method called parseDoWhileStatement that returns a Node object. 
//The method parses a do-while statement and returns a new Node object that represents the statement.
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
    //This statement declares a public method called parseExpressionFromOutside that returns a Node object. 
    //The method parses an expression and returns a new Node object that represents the expression.
    public Node parseExpressionFromOutside() {
        return parseExpressionFromOutside();
    }
//This statement declares a private method called parseSelectionOrLoopStatement that returns a Node object. 
//The method parses a selection or loop statement and returns a new Node object that represents the statement.
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
//This statement declares a private method called parseFunctionCall that takes a Node parameter called functionNameNode and returns a Node object. 
//The method parses a function call and returns a new Node object that represents the call.
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
//This statement declares a private method called getCurrentToken that returns a Token object. 
//The method returns the current token in the list of tokens being parsed.
 private Token getCurrentToken() {
 if (currentTokenIndex < tokens.size()) {
 return tokens.get(currentTokenIndex);
 } else {
 return null;
 }
 }
//This statement declares a private method called getNextToken that returns a Token object.
//The method returns the next token in the list of tokens being parsed and increments the current token index
 private Token getNextToken() {
 if (currentTokenIndex < tokens.size() - 1) {
 return tokens.get(++currentTokenIndex);
 } else {
   return null;
 }
 }
//This statement declares a private method called expectToken that takes a Token.Type parameter called expectedType and returns a Token object. 
//The method checks if the current token is of the expected type, returns the current token, and increments the current token index.
 private Token expectToken(Token.Type expectedType) {
 Token currentToken = getCurrentToken();
 if (currentToken.getType() == expectedType) {
 getNextToken();
 return currentToken;
 } else {
 throw new RuntimeException("Expected token type: " + expectedType + ", but found: " + currentToken.getType());
 }
 }
 }

