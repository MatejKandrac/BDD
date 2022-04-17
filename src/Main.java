public class Main {

    public static void main(String[] args) {
        FunctionResolver resolver = new FunctionResolver();
        String input = "A1B0C1D0E1F0G1H0", expression = "A.B+C.D+E.F+G.H", order = "ABCDEFGH";
        BinaryDecisionTree tree = BDD_create(expression, order);
        long stamp = System.currentTimeMillis();
        char result;
        try {
            result = BDD_use(tree, input);
        } catch (IllegalStateException e) {
            result = 'e';
        }
        System.out.println("BDD input resolved in: " + (stamp - System.currentTimeMillis()) + "ms");
        System.out.println(result + " correct is: " + resolver.getResult(expression, input));
    }

    public static BinaryDecisionTree BDD_create(String bfunkcia, String poradie) {
        long stamp = System.currentTimeMillis();
        BinaryDecisionTree binaryDecisionTree = new BinaryDecisionTree(bfunkcia, poradie);
        System.out.println("Created tree in: " + (System.currentTimeMillis() - stamp) + "ms");
        return binaryDecisionTree;
    }

    public static char BDD_use(BinaryDecisionTree tree, String vstupy){
        return tree.use(vstupy) ? '1' : '0';
    }

}