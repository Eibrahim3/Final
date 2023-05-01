package Final;
import Final.Lexer;
import Final.Compiler;
import Final.TokenCode;
public class Token {
    private String lexeme;
    private int type;

    public Token(String lexeme, int type) {
        this.lexeme = lexeme;
        this.type = type;
    }

    public String getLexeme() {
        return lexeme;
    }

    public int getType() {
        return type;
    }
}                