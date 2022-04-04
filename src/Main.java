public class Main {

    public static void main(String[] args) {
        BinaryDecisionTree tree = BDD_create("A.B.C+A.b.C+a.B.c", "ABC");
    }

    public static BinaryDecisionTree BDD_create(String bfunkcia, String poradie) {
        BinaryDecisionTree binaryDecisionTree = new BinaryDecisionTree(bfunkcia, poradie);
        return binaryDecisionTree;
    }

}
