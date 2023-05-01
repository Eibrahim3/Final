package Final;
import Final.TokenCode;
import Final.Token;
import Final.Compiler;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Lexer {
    private String inputString;

    public Lexer(String inputString) {
        this.inputString = inputString;
    }

    public List<Token> tokenize() {
        List<Token> tokens = new ArrayList<>();

        // regex patterns for tokens
        Pattern realLiteralPattern = Pattern.compile("\\d+\\.\\d+");
        Pattern naturalLiteralPattern = Pattern.compile("\\d+");
        Pattern boolLiteralPattern = Pattern.compile("true|false");
        Pattern charLiteralPattern = Pattern.compile("'([^\\\\']|\\\\.)'");
        Pattern stringLiteralPattern = Pattern.compile("\"([^\\\\\"]|\\\\.)*\"");
        Pattern selectionStatementPattern = Pattern.compile("if|else");
        Pattern loopStatementPattern = Pattern.compile("while|for");
        Pattern stringDeclarationPattern = Pattern.compile("String");
        Pattern naturalDeclarationPattern = Pattern.compile("int");
        Pattern charDeclarationPattern = Pattern.compile("char");
        Pattern realDeclarationPattern = Pattern.compile("double|float");
        Pattern booleanDeclarationPattern = Pattern.compile("boolean");
        Pattern additionPattern = Pattern.compile("\\+");
        Pattern subtractionPattern = Pattern.compile("-");
        Pattern multiplicationPattern = Pattern.compile("\\*");
        Pattern divisionPattern = Pattern.compile("/");
        Pattern exponentiationPattern = Pattern.compile("\\^");
        Pattern orderOfOperationsPattern = Pattern.compile("\\(|\\)");
        Pattern greaterThanPattern = Pattern.compile(">");
        Pattern lessThanPattern = Pattern.compile("<");
        Pattern greaterThanOrEqualPattern = Pattern.compile(">=");
        Pattern lessThanOrEqualPattern = Pattern.compile("<=");
        Pattern equalToPattern = Pattern.compile("==");
        Pattern notEqualPattern = Pattern.compile("!=");
        Pattern unaryNegationPattern = Pattern.compile("-");
        Pattern logicalNotPattern = Pattern.compile("!");
        Pattern logicalAndPattern = Pattern.compile("&&");
        Pattern logicalOrPattern = Pattern.compile("\\|\\|");
        Pattern groupingCodeBlocksPattern = Pattern.compile("\\{|\\}");
        Pattern parameterSeparatorPattern = Pattern.compile(",");
        Pattern functionParametersPattern = Pattern.compile("\\(");
        Pattern variableFunctionIdentifierPattern = Pattern.compile("[a-zA-Z][a-zA-Z0-9]*");

        // ignore block comments
        inputString = inputString.replaceAll("/\\*.*?\\*/", "");

        // ignore single line comments
        inputString = inputString.replaceAll("//.*", "");

        // create a matcher for the input string
        Matcher matcher = Pattern.compile("\\s*([\\S]+)\\s*").matcher(inputString);

        // iterate over the input string and match tokens
        while (matcher.find()) {
            String lexeme = matcher.group(1);

            if (realLiteralPattern.matcher(lexeme).matches()) {
                tokens.add(new Token(lexeme, TokenCode.REAL_LITERAL));
            } else if (naturalLiteralPattern.matcher(lexeme).matches()) {
                tokens.add(new Token(lexeme, TokenCode.NATURAL_LITERAL));
            } else if (boolLiteralPattern.matcher(lexeme).matches()) {
                tokens.add(new Token(lexeme, TokenCode.BOOL_LITERAL));
            } else if (charLiteralPattern.matcher(lexeme).matches()) {
                tokens.add(new Token(lexeme, TokenCode.CHAR_LITERAL));
            } else if (stringLiteralPattern.matcher(lexeme).matches()) {
                tokens.add(new Token(lexeme, TokenCode.STRING_LITERAL));
            } else if (selectionStatementPattern.matcher(lexeme).matches()) {
                tokens.add(new Token(lexeme, TokenCode.SELECTION_STATEMENT));
            } else if (loopStatementPattern.matcher(lexeme).matches()) {
                tokens.add(new Token(lexeme, TokenCode.LOOP_STATEMENT));
            } else if (stringDeclarationPattern.matcher(lexeme).matches()) {
                tokens.add(new Token(lexeme, TokenCode.STRING_DECLARATION));
            } else if (naturalDeclarationPattern.matcher(lexeme).matches()) {
                tokens.add(new Token(lexeme, TokenCode.NATURAL_DECLARATION));
            } else if (charDeclarationPattern.matcher(lexeme).matches()) {
                tokens.add(new Token(lexeme, TokenCode.CHAR_DECLARATION));
            } else if (realDeclarationPattern.matcher(lexeme).matches()) {
                tokens.add(new Token(lexeme, TokenCode.REAL_DECLARATION));
            } else if (booleanDeclarationPattern.matcher(lexeme).matches()) {
                tokens.add(new Token(lexeme, TokenCode.BOOLEAN_DECLARATION));
            } else if (additionPattern.matcher(lexeme).matches()) {
                tokens.add(new Token(lexeme, TokenCode.ADDITION));
            } else if (subtractionPattern.matcher(lexeme).matches()) {
                if (tokens.isEmpty() || tokens.get(tokens.size() - 1).getType() != TokenCode.REAL_LITERAL) {
                    tokens.add(new Token(lexeme, TokenCode.SUBTRACTION));
                } else {
                    // handle unary negation operator
                    tokens.remove(tokens.size() - 1);
                    tokens.add(new Token("-", TokenCode.UNARY_NEGATION));
                }
            } else if (multiplicationPattern.matcher(lexeme).matches()) {
                tokens.add(new Token(lexeme, TokenCode.MULTIPLICATION));
            } else if (divisionPattern.matcher(lexeme).matches()) {
                tokens.add(new Token(lexeme, TokenCode.DIVISION));
            } else if (exponentiationPattern.matcher(lexeme).matches()) {
                tokens.add(new Token(lexeme, TokenCode.EXPONENTIATION));
            } else if (orderOfOperationsPattern.matcher(lexeme).matches()) {
                tokens.add(new Token(lexeme, TokenCode.ORDER_OF_OPERATIONS));
            } else if (greaterThanOrEqualPattern.matcher(lexeme).matches()) {
                tokens.add(new Token(lexeme, TokenCode.GREATER_THAN_OR_EQUAL_TO));
            } else if (lessThanOrEqualPattern.matcher(lexeme).matches()) {
                tokens.add(new Token(lexeme, TokenCode.LESS_THAN_OR_EQUAL_TO));
            } else if (greaterThanPattern.matcher(lexeme).matches()) {
                tokens.add(new Token(lexeme, TokenCode.GREATER_THAN));
            } else if (lessThanPattern.matcher(lexeme).matches()) {
                tokens.add(new Token(lexeme, TokenCode.LESS_THAN));
            } else if (equalToPattern.matcher(lexeme).matches()) {
                tokens.add(new Token(lexeme, TokenCode.EQUAL_TO));
            } else if (notEqualPattern.matcher(lexeme).matches()) {
                tokens.add(new Token(lexeme, TokenCode.NOT_EQUAL_TO));
            } else if (unaryNegationPattern.matcher(lexeme).matches()) {
                tokens.add(new Token(lexeme, TokenCode.UNARY_NEGATION));
            } else if (logicalNotPattern.matcher(lexeme).matches()) {
                tokens.add(new Token(lexeme, TokenCode.LOGICAL_NOT));
            } else if (logicalAndPattern.matcher(lexeme).matches()) {
                tokens.add(new Token(lexeme, TokenCode.LOGICAL_AND));
            } else if (logicalOrPattern.matcher(lexeme).matches()) {
                tokens.add(new Token(lexeme, TokenCode.LOGICAL_OR));
            } else if (groupingCodeBlocksPattern.matcher(lexeme).matches()) {
                tokens.add(new Token(lexeme, TokenCode.GROUPING_CODE_BLOCKS));
            } else if (parameterSeparatorPattern.matcher(lexeme).matches()) {
                tokens.add(new
                		Token(lexeme, TokenCode.PARAMETER_SEPARATOR));
            } else if (functionParametersPattern.matcher(lexeme).matches()) {
                tokens.add(new Token(lexeme, TokenCode.FUNCTION_PARAMETERS));
            } else if (variableFunctionIdentifierPattern.matcher(lexeme).matches()) {
                // check if the previous token is a dot, indicating a method call
                if (!tokens.isEmpty() && tokens.get(tokens.size() - 1).getType() == TokenCode.DOT) {
                    tokens.remove(tokens.size() - 1);
                    tokens.add(new Token(tokens.get(tokens.size() - 1).getLexeme() + "." + lexeme, TokenCode.VARIABLE_FUNCTION_IDENTIFIER));
                } else {
                    tokens.add(new Token(lexeme, TokenCode.VARIABLE_FUNCTION_IDENTIFIER));
                }
            } else if (lexeme.equals(".")) {
                tokens.add(new Token(lexeme, TokenCode.DOT));
            } else {
                // handle unknown tokens
                throw new RuntimeException("Unknown token: " + lexeme);
            }
        }

        return tokens;
    }
}
