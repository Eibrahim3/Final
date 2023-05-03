package Final;
import Final.Lexer;
import Final.Token;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
//This statement begins the declaration of the Compiler class.
public class Compiler {
//This statement declares a private instance variable inputString of type String to store the input program as a string.
    private String inputString;
//This statement begins the declaration of a public constructor for the Compiler class that takes an inputFilePath 
    //parameter of type String and throws an IOException if an I/O error occurs while reading the input file.
    public Compiler(String inputFilePath) throws IOException {
        // read the input file into a string
 //This statement creates a new Path object representing the input file path.
        Path path = Paths.get(inputFilePath);
//This statement reads all bytes from the input file into a byte array.
        byte[] inputBytes = Files.readAllBytes(path);
//This statement initializes the inputString instance variable with a new String object created from the byte array.
        inputString = new String(inputBytes);
    }
//This statement begins the declaration of a public tokenize method that returns a List of Token objects.
    public List<Token> tokenize() {
//This statement creates a new Lexer object with the inputString instance variable as input.
        Lexer lexer = new Lexer(inputString);
//This statement calls the tokenize method on the lexer object and returns the resulting List of Token objects
        return lexer.tokenize();
    }
//This statement begins the declaration of the main method for the Compiler class, which takes a String array args as input
    public static void main(String[] args) {
 //This statement initializes a String variable inputFilePath with the filename of the input file.
        String inputFilePath = "input.txt";
 //This statement initializes a Compiler object compiler to null.       
        Compiler compiler = null;
//This statement attempts to create a new Compiler object with the inputFilePath as input and assigns it to the compiler object.
        try {
            compiler = new Compiler(inputFilePath);
//This statement catches an IOException thrown by the Compiler 
            //constructor and prints an error message, then returns from the main method.
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
            return;
        }
// This statement calls the tokenize method on the compiler 
//object and assigns the resulting List of Token objects to a new List variable tokens.
        List<Token> tokens = compiler.tokenize();
//This statement creates a new Parser object with the tokens List as input.
        Parser parser = new Parser(tokens);
//This statement calls the parse method on the parser 
//object and assigns the resulting Node object to a new Node variable rootNode.
        Node rootNode = parser.parse();

        // Output the resulting parse tree
        System.out.println(rootNode.toString());
    }
        
}       
        
        
