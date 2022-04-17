public class Main {

    public static void main(String[] args) {
        FunctionResolver resolver = new FunctionResolver();
        InputGenerator generator = new InputGenerator();
        String dnf = generator.generateDNF(10);
        String order = generator.generateOrder();
        String input = generator.generateInput();
        System.out.println(dnf + " ORDER: " + order + " INPUT: " + input);
        BinaryDecisionTree tree = BDD_create(dnf, order);
        long stamp = System.currentTimeMillis();
        char result;
        try {
            result = BDD_use(tree, input);
        } catch (IllegalStateException e) {
            result = 'e';
        }
        System.out.println("BDD input resolved in: " + (stamp - System.currentTimeMillis()) + "ms");
        System.out.println(result + " correct is: " + resolver.getResult(dnf, input));
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