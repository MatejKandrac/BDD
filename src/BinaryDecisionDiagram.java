import java.util.*;

public class BinaryDecisionDiagram {
    private Node root;
    private final Node terminalTrue;
    private final Node terminalFalse;
    private int nodeCount = 3;
    private final List<Map<String, Node>> nodeLevels;
    public BinaryDecisionDiagram(String binaryFunction, String order) {
        nodeLevels = new ArrayList<>();

        String expression = ExpressionBuilder.formatInput(binaryFunction);

        root = new Node(expression, order.charAt(0), null);

        Map<String, Node> map = new HashMap<>();
        map.put(expression, root);
        nodeLevels.add(map);

        terminalTrue = new Node(true);
        terminalFalse = new Node(false);

        for (int i = 1; i < order.length()+1; i++) {
            createLevel(i, Character.toString(order.charAt(i-1)));
            simplifyLevel(i - 1);
        }

//        Map<String, Node> lastLevel = nodeLevels.get(nodeLevels.size()-1);
//        if (lastLevel.size() > 0)
//            for (String s : lastLevel.keySet())
//                lastLevel.get(s).setLabel(Character.toString(order.charAt(order.length()-1)));

        if (root == terminalFalse || root == terminalTrue)
            nodeCount--;
    }

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

    void createLevel(int level, String variable) {
        Map<String, Node> layerBefore = nodeLevels.get(level - 1);
        Map<String, Node> newLayer = new HashMap<>();
        nodeLevels.add(newLayer);

        for (String s : layerBefore.keySet()) {

            String[] split = s.split("\\+");
            ExpressionBuilder positive = new ExpressionBuilder();
            ExpressionBuilder negative = new ExpressionBuilder();

            for (String s1 : split) {
                if (s1.contains("!"+variable))
                    negative.appendFormat(s1, "!"+variable);
                else if (s1.contains(variable))
                    positive.appendFormat(s1, variable);
                else {
                    positive.appendWithCheck(s1);
                    negative.appendWithCheck(s1);
                }
            }
            Node parent = layerBefore.get(s);
            parent.setLabel(variable);
            if (positive.isNotHandled())
                parent.setRight(terminalFalse);
            else if (positive.isAlwaysTrue())
                parent.setRight(terminalTrue);
            else {
                if (newLayer.containsKey(positive.toString())){ //REDUCE I TODO vypocitat podstrom
                    Node noDuplicate = newLayer.get(positive.toString());
                    parent.setRight(noDuplicate);
                }
                else {
                    Node newNode = new Node(positive.toString(), parent);
                    parent.setRight(newNode);
                    newLayer.put(positive.toString(), newNode);
                    nodeCount++;
                }
            }

            if (negative.isNotHandled())
                parent.setLeft(terminalFalse);
            else if (negative.isAlwaysTrue())
                parent.setLeft(terminalTrue);
            else {
                if (newLayer.containsKey(negative.toString())){ //REDUCE I
                    Node noDuplicate = newLayer.get(negative.toString());
                    parent.setLeft(noDuplicate);
                }
                else {
                    Node newNode = new Node(negative.toString(), parent);
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

    public boolean use(String input) {
        return recursiveUse(root, input);
    }

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
