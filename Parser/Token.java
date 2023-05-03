package Final;
import Final.Token;
import Final.Parser;

public class Token {
//A private instance variable that stores the textual representation of the token, also known as its lexeme.
    private String lexeme;
//A private instance variable that stores the type of the token. 
    //The type is an enumerated value defined in the Type enumeration.
    private Type type;
//A constructor that creates a new Token object with the given lexeme and type.
    public Token(String lexeme, Type type) {
        this.lexeme = lexeme;
        this.type = type;
    }
//A method that returns the lexeme of the token.
    public String getLexeme() {
        return lexeme;
    }
//A method that returns the type of the token.
    public Type getType() {
        return type;
    }
//A method that returns the lexeme of the token. 
    //This method is provided for compatibility with older versions of the Token class.
    public String getText() {
        return lexeme;
    }
    //A method that returns the string representation of the type of the token. 
    //This method is provided for convenience when printing out the token
    public String getValue() {
        return type.toString();
    }
    
    public enum Type {
        LEFT_BRACE,
        RIGHT_BRACE,
        LEFT_PAREN,
        RIGHT_PAREN,
        LEFT_BRACKET,
        RIGHT_BRACKET,
        COMMA,
        DOT,
        COLON,
        SEMICOLON,
        PLUS,
        MINUS,
        TIMES,
        DIVIDE,
        MODULO,
        ASSIGN,
        EQUALS,
        NOT_EQUALS,
        GREATER_THAN,
        GREATER_THAN_OR_EQUALS,
        LESS_THAN,
        LESS_THAN_OR_EQUALS,
        AND,
        OR,
        NOT,
        IF,
        ELSE,
        SWITCH,
        CASE,
        DEFAULT,
        WHILE,
        DO,
        FOR,
        BREAK,
        CONTINUE,
        VAR,
        FUNCTION,
        RETURN,
        IDENTIFIER,
        NATURAL_LITERAL,
        REAL_LITERAL,
        BOOL_LITERAL,
        CHAR_LITERAL,
        STRING_LITERAL,
        EOF
    }
}
