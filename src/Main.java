import input.FunctionResolver;
import input.InputGenerator;

public class Main {

    private static final int VARIABLE_COUNT = 15;

    public static void main(String[] args) {
        FunctionResolver resolver = new FunctionResolver();
        InputGenerator generator = new InputGenerator();
        String dnf = generator.generateDNF(VARIABLE_COUNT);
        String order = generator.generateOrder();
        System.out.println(dnf + " ORDER: " + order);

        BinaryDecisionDiagram tree = BDD_create(dnf, order);
        System.out.println("Tree created. Nodes: " + tree.getNodeCount());

        int rows = (int) Math.pow(2,VARIABLE_COUNT);

        StringBuilder builder = new StringBuilder();
        char treeResult;
        char functionResult;

        for (int i=0; i<rows; i++) {
            for (int j=VARIABLE_COUNT-1; j>=0; j--) {
                builder.append((i/(int) Math.pow(2, j))%2);
            }
            treeResult = BDD_use(tree, builder.toString());
            functionResult = resolver.getResult(dnf, builder.toString());
            if (treeResult != functionResult)
                System.out.println("Invalid result for input: " + builder + " Tree is: " + treeResult + " where function is: " + functionResult);
            builder = new StringBuilder();
        }
        System.out.println("Finished");
    }

    public static BinaryDecisionDiagram BDD_create(String bfunkcia, String poradie) {
        long stamp = System.currentTimeMillis();
        BinaryDecisionDiagram binaryDecisionTree = new BinaryDecisionDiagram(bfunkcia, poradie);
        System.out.println("Created tree in: " + (System.currentTimeMillis() - stamp) + "ms");
        return binaryDecisionTree;
    }

    public static char BDD_use(BinaryDecisionDiagram tree, String vstupy){
        return tree.use(vstupy) ? '1' : '0';
    }

}