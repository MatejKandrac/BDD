public class Main {

    public static void main(String[] args) {
        BinaryDecisionTree tree = BDD_create("A.B+C.D+E.F+G.H", "ABCDEFGH");
        System.out.println(BDD_use(tree, "A1B0C1D0E1F0G1H0"));
    }

    public static BinaryDecisionTree BDD_create(String bfunkcia, String poradie) {
        BinaryDecisionTree binaryDecisionTree = new BinaryDecisionTree(bfunkcia, poradie);
        return binaryDecisionTree;
    }

    public static char BDD_use(BinaryDecisionTree tree, String vstupy){
        return tree.use(vstupy) ? '1' : '0';
    }

}