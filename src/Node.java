public class Node {

    private Node left;
    private Node right;

    private String expression;
    private Boolean value = null;

    public Node(String expression) {
        this.expression = expression;
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

    public String getExpression() {
        return expression;
    }

    public void setValue(boolean value) {
        this.value = value;
    }
}
