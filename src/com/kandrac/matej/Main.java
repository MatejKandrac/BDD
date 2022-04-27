package com.kandrac.matej;

import com.kandrac.matej.testing.Tester;

public class Main {

    private static final int VARIABLE_COUNT = 25;

    public static void main(String[] args) {
        Tester tester = new Tester();

        String dnf = tester.generateDNF(VARIABLE_COUNT);
        String order = tester.generateOrder(VARIABLE_COUNT);

        BinaryDecisionDiagram tree = BDD_create(dnf, order);

        System.out.println(dnf + "\nORDER: " + order + "\nNUM OF NODES: " + tree.getNodeCount() + " TOTAL REDUCED: " + tree.getReducedNodes());

        tester.checkValid(tree, VARIABLE_COUNT, dnf);

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