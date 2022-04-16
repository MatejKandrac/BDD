import java.util.*;

public class BinaryDecisionTree {

    private final Node root;
    private final Node terminalTrue;
    private final Node terminalFalse;
    private int nodeCount;
    private final int variableCount;
    private final List<Map<String, Node>> nodeLevels;

    public BinaryDecisionTree(String binaryFunction, String order) {
        nodeLevels = new ArrayList<>();
        variableCount = order.length();


        String expression = formatInput(binaryFunction);

        root = new Node(expression, order.charAt(0), null);

        Map<String, Node> map = new HashMap<>();
        map.put(expression, root);
        nodeLevels.add(map);

        terminalTrue = new Node(true);
        terminalFalse = new Node(false);

        for (int i = 1; i < order.length()+1; i++)
            createLevel(i, Character.toString(order.charAt(i-1)));

        nodeCount = getNodeCount();
    }

    private String formatInput(String input) {
        StringBuilder format = new StringBuilder();
        String[] expressions = input.split("\\+");
        for (int i = 0; i < expressions.length; i++) {
            String[] variables = expressions[i].split("\\.");

            Arrays.sort(variables, Comparator.comparingInt(o -> (o.length() == 2 ? o.charAt(1) : o.charAt(0))));

            for (int i1 = 0; i1 < variables.length; i1++) {
                format.append(variables[i1]);
                if (i1 < variables.length-1)
                    format.append(".");
            }

            if (i < expressions.length-1)
                format.append("+");

        }
        return format.toString();
    }

    private int getNodeCount() {
        int counter = 0;
        for (Map<String, Node> nodeLevel : nodeLevels)
            counter += nodeLevel.size();
        return counter;
    }

    void simplifyLevel(int index) {

    }

    private void appendFormat(StringBuilder builder, String target, String expression) {
        if (builder.length() != 0)
            builder.append("+");
        if (target.startsWith(expression))
            builder.append(target.substring(expression.length() + (target.length() > expression.length() ? 1 : 0)));
        else if (target.endsWith(expression))
            builder.append(target, 0, target.length() - (expression.length() + 2));
        else
            builder.append(target.replace(expression+".", ""));
    }

    void createLevel(int level, String variable) {
        Map<String, Node> layerBefore = nodeLevels.get(level - 1);
        Map<String, Node> newLayer = new HashMap<>();
        nodeLevels.add(newLayer);

        for (String s : layerBefore.keySet()) {

            String[] split = s.split("\\+");
            StringBuilder positive = null;
            StringBuilder negative = null;

            for (String s1 : split) {
                if (s1.contains("!"+variable)){
                    if (negative == null)
                        negative = new StringBuilder();
                    appendFormat(negative, s1, "!"+variable);
                }
                else if (s1.contains(variable)){
                    if (positive == null)
                        positive = new StringBuilder();
                    appendFormat(positive, s1, variable);
                }
                else {
                    if (positive == null)
                        positive = new StringBuilder();
                    if (negative == null)
                        negative = new StringBuilder();
                    if (positive.length() != 0)
                        positive.append("+");
                    if (negative.length() != 0)
                        negative.append("+");
                    positive.append(s);
                    negative.append(s);
                }
            }
            Node parent = layerBefore.get(s);
            if (positive == null)
                parent.setRight(terminalFalse);
            else if (positive.length() > 0) {
                if (newLayer.containsKey(positive.toString())){
                    Node noDuplicate = newLayer.get(positive.toString());
                    parent.setRight(noDuplicate);
                }
                else {
                    Node newNode = new Node(positive.toString(), variable, parent);
                    parent.setRight(newNode);
                    newLayer.put(positive.toString(), newNode);
                }
            } else
                parent.setRight(terminalTrue);

            if (negative == null)
                parent.setLeft(terminalFalse);
            else if (negative.length() > 0) {
                if (newLayer.containsKey(negative.toString())){
                    Node noDuplicate = newLayer.get(negative.toString());
                    parent.setLeft(noDuplicate);
                }
                else {
                    Node newNode = new Node(negative.toString(), variable, parent);
                    parent.setLeft(newNode);
                    newLayer.put(negative.toString(), newNode);
                }
            } else
                parent.setLeft(terminalTrue);
        }

    }

    private boolean isDuplicate(List<Node> layerNodes, Node node) {
        for (Node layerNode : layerNodes) {
            if (layerNode.getParent() == node.getParent())
                return true;
        }
        return false;
    }

}
