package com.kandrac.matej;

public class Node {

    private Node left;
    private Node right;
    private Node parent;

    /**
     * Label refers to a variable this node divides.
     */
    private String label;
    /**
     * Value is set to true or false if the node is a terminal node.
     */
    private final Boolean value;

    /**
     * Constructor for simple nodes within tree
     * @param parent parent node of this node (root is null)
     */
    public Node(Node parent) {
        this.parent = parent;
        value = null;
    }

    /**
     * Constructor for terminal nodes
     * @param value true or false terminal value
     */
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
