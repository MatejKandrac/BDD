public class Main {

    public static void main(String[] args) {
        BinaryDecisionTree tree = BDD_create("B.A.C+A.C.!B+!A.B.!C", "ABC");
    }

    public static BinaryDecisionTree BDD_create(String bfunkcia, String poradie) {
        BinaryDecisionTree binaryDecisionTree = new BinaryDecisionTree(bfunkcia, poradie);
        return binaryDecisionTree;
    }

}
