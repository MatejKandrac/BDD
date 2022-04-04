
public class BinaryDecisionTree {

    private final Node root;
    private final Node terminalTrue;
    private final Node terminalFalse;

    public BinaryDecisionTree(String binaryFunction, String order) {
        root = new Node(binaryFunction);
        terminalTrue = new Node(true);
        terminalFalse = new Node(false);
        createTree(root);
    }

    void createTree(Node node) {

        String label = Character.toString(node.getExpression().charAt(0)).toUpperCase();

        String[] split = node.getExpression().split("\\+");

        StringBuilder positive = new StringBuilder();
        StringBuilder negative = new StringBuilder();

        for (String s : split) {
            if (s.contains(label)) {
                if (positive.length() != 0)
                    positive.append("+");
                positive.append(s.replace(label + ".", ""));
            } else if (s.contains(label.toLowerCase())) {
                if (negative.length() != 0)
                    negative.append("+");
                negative.append(s.replace(label.toLowerCase() + ".", ""));
            }
        }

        if (positive.length() != 0 ) {
            if (node.getExpression().length() == 1) {
                boolean reverse = Character.isLowerCase(node.getExpression().charAt(0));
                node.setRight(reverse ? terminalFalse : terminalTrue);
                node.setLeft(reverse ? terminalTrue : terminalFalse);
            }
            else {
                node.setRight(new Node(positive.toString()));
                createTree(node.getRight());
            }
        }
        else
            node.setRight(terminalFalse);

        if (negative.length() != 0 ) {
            if (node.getExpression().length() == 1) {
                boolean reverse = Character.isLowerCase(node.getExpression().charAt(0));
                node.setRight(reverse ? terminalFalse : terminalTrue);
                node.setLeft(reverse ? terminalTrue : terminalFalse);
            }
            else {
                node.setLeft(new Node(positive.toString()));
                createTree(node.getLeft());
            }
        }
        else
            node.setLeft(terminalFalse);
    }

    public Node getRoot() {
        return root;
    }
}
