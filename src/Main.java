public class Main {

    public static void main(String[] args) {
        BinaryDecisionTree tree = BDD_create("A.B.C+A.!B.C+C", "ABC");
    }

    public static BinaryDecisionTree BDD_create(String bfunkcia, String poradie) {
        BinaryDecisionTree binaryDecisionTree = new BinaryDecisionTree(bfunkcia, poradie);
        return binaryDecisionTree;
    }

}
