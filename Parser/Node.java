package Final;
import java.util.ArrayList;
import java.util.List;
import Final.Parser;

public class Node {
    private String name;
    private List<Node> children;
    private Token token;

    public Node(String name) {
        this.name = name;
        this.children = new ArrayList<>();
        this.token = null;
    }

    public Node(Token token) {
        this.name = token.getType().toString();
        this.children = new ArrayList<>();
        this.token = token;
    }

    public Node(String name, Node... children) {
        this.name = name;
        this.children = new ArrayList<>();
        for (Node child : children) {
            this.children.add(child);
        }
        this.token = null;
    }

    public Node(Token token, Node... children) {
        this.name = token.getType().toString();
        this.children = new ArrayList<>();
        for (Node child : children) {
            this.children.add(child);
        }
        this.token = token;
    }

    public void addChild(Node child) {
        this.children.add(child);
    }

    public List<Node> getChildren() {
        return children;
    }

    public Token getToken() {
        return token;
    }

    @Override
    public String toString() {
        if (token != null) {
            return token.getValue();
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append("(");
            sb.append(name);
            for (Node child : children) {
                sb.append(" ");
                sb.append(child.toString());
            }
            sb.append(")");
            return sb.toString();
        }
    }
}
