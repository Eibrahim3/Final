package Final;
import Final.Lexer;
import Final.Token;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class Compiler {
    private String inputString;

    public Compiler(String inputFilePath) throws IOException {
        // read the input file into a string
        Path path = Paths.get(inputFilePath);
        byte[] inputBytes = Files.readAllBytes(path);
        inputString = new String(inputBytes);
    }

    public List<Token> tokenize() {
        Lexer lexer = new Lexer(inputString);
        return lexer.tokenize();
    }

    public static void main(String[] args) {
        String inputFilePath = "input.txt";
        Compiler compiler = null;

        try {
            compiler = new Compiler(inputFilePath);
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
            return;
        }
        List<Token> tokens = compiler.tokenize();
        // do something with the list of tokens
        Parser parser = new Parser(tokens);
        Node rootNode = parser.parse();

        // Output the resulting parse tree
        System.out.println(rootNode.toString());
    }
        
}       
        
        
