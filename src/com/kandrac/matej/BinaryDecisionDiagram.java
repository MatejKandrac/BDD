package com.kandrac.matej;

import java.util.*;

public class BinaryDecisionDiagram {
    private Node root;
    private final Node terminalTrue;
    private final Node terminalFalse;
    private int nodeCount = 3;
    private int reducedNodes = 0;
    /**
     * This is a list of all layers where each layer is represented as a map with key as Expression and value as Node
     */
    private final List<Map<String, Node>> nodeLevels;

    /**
     * Constructor creates a tree with reductions.
     * @param binaryFunction function which will be used
     * @param order order of variables
     */
    public BinaryDecisionDiagram(String binaryFunction, String order) {
        nodeLevels = new ArrayList<>();

        // Format input
        String expression = ExpressionBuilder.formatInput(binaryFunction);

        // Create root and terminal nodes
        root = new Node(null);
        root.setLabel(Character.toString(order.charAt(0)));

        Map<String, Node> map = new HashMap<>();
        map.put(expression, root);
        nodeLevels.add(map);

        terminalTrue = new Node(true);
        terminalFalse = new Node(false);

        // Create every layer of a tree (each layer is one variable)
        // After level creation, it checks for duplicates in layer above.
        for (int i = 1; i < order.length()+1; i++) {
            createLevel(i, Character.toString(order.charAt(i-1)));
            simplifyLevel(i - 1);
        }

        // If the expression is always true or false, total nodes count will be reduced to 1
        if (root == terminalFalse || root == terminalTrue)
            nodeCount--;

        // Calculate number of reduced nodes
        for (int i = 0; i < order.length(); i++) {
            reducedNodes += Math.pow(2, i);
        }
        reducedNodes -= nodeCount;
    }

    /**
     * Function checks if a node within a layer can be reduces with type S reduction
     * @param index index of layer to check
     */
    void simplifyLevel(int index) {
        Map<String, Node> layer = nodeLevels.get(index);
        String[] array = new String[layer.keySet().size()];
        layer.keySet().toArray(array);
        for (String s : array) {
            Node node = layer.get(s);
            if (reduceS(node))
                layer.remove(s);
        }
    }

    /**
     * Function checks if reduction can be used. Recursively calls itself on parent if reduction was present.
     * @param node node to check
     * @return true if there was a reduction present
     */
    boolean reduceS(Node node) {
        if (node.getRight() == node.getLeft()) { // REDUCE S
            if (node == root) {
                root = node.getRight();
                node.getRight().setParent(null);
            }
            else {
                Node parent = node.getParent();
                if (parent.getRight() == node)
                    parent.setRight(node.getRight());
                else
                    parent.setLeft(node.getRight());
                node.getRight().setParent(parent);
                reduceS(parent);
            }
            nodeCount--;
            return true;
        }
        return false;
    }

    /**
     * Function creates level using shannon decomposition.
     * @param level index of layer
     * @param variable variable resolved in layer
     */
    void createLevel(int level, String variable) {
        Map<String, Node> layerBefore = nodeLevels.get(level - 1);
        Map<String, Node> newLayer = new HashMap<>();
        nodeLevels.add(newLayer);

        // Take every node from layer before and decompose its function
        for (String s : layerBefore.keySet()) {

            String[] split = s.split("\\+");
            // We need positive and negative part
            ExpressionBuilder positive = new ExpressionBuilder();
            ExpressionBuilder negative = new ExpressionBuilder();

            // Find variable in each part of expression and append to corresponding side.
            for (String s1 : split) {
                // Is negated
                if (s1.contains("!"+variable))
                    negative.appendFormat(s1, "!"+variable);
                // Contains value
                else if (s1.contains(variable))
                    positive.appendFormat(s1, variable);
                // If none of above apply, append to both sides
                else {
                    positive.appendWithCheck(s1);
                    negative.appendWithCheck(s1);
                }
            }
            // Get parent node and set its variable to current one (since decomposition is created from parent)
            Node parent = layerBefore.get(s);
            parent.setLabel(variable);

            // Check if expression is always true or false and set terminal
            if (positive.isAlwaysFalse())
                parent.setRight(terminalFalse);
            else if (positive.isAlwaysTrue())
                parent.setRight(terminalTrue);
            // If not check for reduction I
            else {
                if (newLayer.containsKey(positive.toString())){ //REDUCE I
                    Node noDuplicate = newLayer.get(positive.toString());
                    parent.setRight(noDuplicate);
                }
                // There is no duplicate node, we create one
                else {
                    Node newNode = new Node(parent);
                    parent.setRight(newNode);
                    newLayer.put(positive.toString(), newNode);
                    nodeCount++;
                }
            }
            // Same applies for negated part of expression
            if (negative.isAlwaysFalse())
                parent.setLeft(terminalFalse);
            else if (negative.isAlwaysTrue())
                parent.setLeft(terminalTrue);
            else {
                if (newLayer.containsKey(negative.toString())){ //REDUCE I
                    Node noDuplicate = newLayer.get(negative.toString());
                    parent.setLeft(noDuplicate);
                }
                else {
                    Node newNode = new Node(parent);
                    parent.setLeft(newNode);
                    newLayer.put(negative.toString(), newNode);
                    nodeCount++;
                }
            }
        }
    }

    public int getNodeCount() {
        return nodeCount;
    }

    public int getReducedNodes() {
        return reducedNodes;
    }

    /**
     * Function returns true or false depending on the input.
     * @param input set of '0' and '1'
     * @return function result
     */
    public boolean use(String input) {
        return recursiveUse(root, input);
    }

    /**
     * Recursive function which travels the tree searching for terminal node.
     * @param start node which is currently searched
     * @param inputs input to use
     * @return function result
     */
    private boolean recursiveUse(Node start, String inputs) {
        if (start.getValue() != null)
            return start.getValue();
        else {
            boolean value = inputs.charAt((start.getLabel().charAt(0) - 'A')) == '1';
            if (value)
                return recursiveUse(start.getRight(), inputs);
            else
                return recursiveUse(start.getLeft(), inputs);
        }
    }
}
