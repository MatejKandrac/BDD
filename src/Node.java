public class Node {

    private Node left;
    private Node right;
    private Node parent;

    private String label;

    private String expression;
    private Boolean value = null;

    public Node(String expression, Node parent) {
        this.expression = expression;
        this.parent = parent;
    }

    public Node(String expression, char label, Node parent) {
        this.expression = expression;
        this.label = Character.toString(label);
        this.parent = parent;
    }

    public Node(boolean value){
        this.value = value;
    }

    public Node getLeft() {
        return left;
    }

    public void setLeft(Node left) {
        this.left = left;
    }

    public Node getRight() {
        return right;
    }

    public void setRight(Node right) {
        this.right = right;
    }

    public void setValue(boolean value) {
        this.value = value;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public void setLabel(String variable) {
        label =  variable;
    }

    public Boolean getValue() {
        return value;
    }

    public String getLabel() {
        return label;
    }
}
