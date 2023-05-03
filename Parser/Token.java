package Final;
import Final.Token;
import Final.Parser;

public class Token {
    private String lexeme;
    private Type type;

    public Token(String lexeme, Type type) {
        this.lexeme = lexeme;
        this.type = type;
    }

    public String getLexeme() {
        return lexeme;
    }

    public Type getType() {
        return type;
    }
    public String getText() {
        return lexeme;
    }
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
