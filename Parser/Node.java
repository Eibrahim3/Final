package Final;
import java.util.ArrayList;
import java.util.List;
import Final.Parser;
//name: A String representing the name of the node, which is used to identify the type of statement or expression that the node represents.
//children: A list of Node objects representing the child nodes of the current node. Each child node is also a Node object, which can have its own children.
//token: A Token object representing the token that the node corresponds to, if any.
public class Node {
//A constructor that creates a new Node object with the given name and no children or token.
    private String name;
    private List<Node> children;
//A constructor that creates a new Node object with the name of the token's type and no children.
    private Token token;
//This statement begins the declaration of a public constructor for the Node class, 
 //which takes a String object representing the name of the node as a parameter.
    public Node(String name) {
//This statement assigns the name parameter to the name instance variable.
        this.name = name;
//This statement initializes the children instance variable to a new, empty ArrayList object
        this.children = new ArrayList<>();
//This statement initializes the token instance variable to null.
        this.token = null;
    }
//This statement begins the declaration of a public constructor for the Node class, 
//which takes a Token object representing the node's token as a parameter.
    public Node(Token token) {
//This statement sets the name instance variable to the string representation of the Token type
        this.name = token.getType().toString();
//This statement initializes the children instance variable to a new, empty ArrayList object.
        this.children = new ArrayList<>();
//This statement assigns the token parameter to the token instance variable.
        this.token = token;
    }
//This statement begins the declaration of a public constructor for the Node class, 
//which takes a String object representing the name of the node and an array of Node objects representing the node's children as parameters.
    public Node(String name, Node... children) {
//This statement assigns the name parameter to the name instance variable.
        this.name = name;
 //This statement initializes the children instance variable to a new, empty ArrayList object.       
        this.children = new ArrayList<>();
//This statement iterates over the children array and adds each child to the children instance variable.
        for (Node child : children) {
            this.children.add(child);
        }
//This statement initializes the token instance variable to null.
        
          this.token = null;
    }
 // This statement begins the declaration of a public constructor for the Node class, which takes a Token object representing the node's token and an array of Node objects representing the node's children as parameters.

    public Node(Token token, Node... children) {
//This statement sets the name instance variable to the string representation of the Token type.
        this.name = token.getType().toString();
//This statement initializes the children instance variable to a new, empty ArrayList object.
        this.children = new ArrayList<>();
//This statement iterates over the children array and adds each child to the children instance variable.
        for (Node child : children) {
            this.children.add(child);
        }
//This statement assigns the token parameter to the token instance variable.
        this.token = token;
    }
//This statement begins the declaration of a public method for the Node class, 
    //which takes a Node object representing a child of the node as a parameter.
    public void addChild(Node child) {
//This statement adds the child parameter to the children instance variable.
        this.children.add(child);
    }
//This statement begins the declaration of a public method for the Node class,
//which returns a List object representing the node's children.
    public List<Node> getChildren() {
//This statement returns the children instance variable
        return children;
    }
//This statement begins the declaration of a public method for the Node class,
    //which returns a Token object representing the node's token
    public Token getToken() {
        return token;
    }
//This statement begins the declaration of an overridden toString method for the Node class, which returns a String object representing the node.
    @Override
    public String toString() {
//This statement checks if the token instance variable is not null.
        if (token != null) {
//This statement returns the value of the token instance variable.
            return token.getValue();
//This statement indicates that the token instance variable is null, so the node represents a non-terminal symbol.
        } else {
//This statement creates a new StringBuilder object for constructing the String representation of the node.
            StringBuilder sb = new StringBuilder();
// This statement appends a left parenthesis to the StringBuilder object.
            sb.append("(");
//This statement appends the name instance variable to the StringBuilder object.
            sb.append(name);
//This statement iterates over the children 
//instance variable and recursively calls the toString method on each child node, appending the results to the StringBuilder object.
            for (Node child : children) {
                sb.append(" ");
                sb.append(child.toString());
            }
////This statement appends a right parenthesis to the StringBuilder object.

            sb.append(")");
//This statement returns the String representation of the StringBuilder object.
            return sb.toString();
        }
    }
}
