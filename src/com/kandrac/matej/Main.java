package com.kandrac.matej;

import com.kandrac.matej.testing.Tester;

public class Main {

    private static final int MIN_VARIABLES_COUNT = 13;
    private static final int MAX_VARIABLES_COUNT = 20;
    private static final int NUM_OF_FUNCTIONS = 100;

    public static void main(String[] args) {
        Tester tester = new Tester();

        // We test variables from 13 to 19
        for (int i = MIN_VARIABLES_COUNT; i < MAX_VARIABLES_COUNT; i++) {

            // Save total reduction rate and total time in MS
            long reductionRate = 0;
            long totalMsCreate = 0;
            long totalMsUse = 0;

            // We create 100 different cases of DNF functions
            for (int j = 0; j < NUM_OF_FUNCTIONS; j++) {
                // generate input
                String dnf = tester.generateDNF(i);
                String order = tester.generateOrder(i);

//                System.out.println(dnf + "\nORDER: " + order);

                // Save timestamp of current time in MS
                long timeStamp = System.currentTimeMillis();
                BinaryDecisionDiagram tree = BDD_create(dnf, order);
                // Add ms to total time
                totalMsCreate += System.currentTimeMillis() - timeStamp;
                // Add to total reduce ratio
                reductionRate += tree.getReducedNodes() / tree.getNodeCount();
                // Run tests and add time to total time
                totalMsUse += tester.checkValid(tree, i, dnf);
            }

            // Print results
            System.out.println("Finished for " + i + " variables");
            System.out.println("Average time (create): " + (totalMsCreate / NUM_OF_FUNCTIONS) + "ms");
            System.out.println("Average time (use): " + (totalMsUse / NUM_OF_FUNCTIONS) + "ms");
            System.out.println("Average reduction rate: " + (reductionRate / NUM_OF_FUNCTIONS) + "\n");
        }
    }

    public static BinaryDecisionDiagram BDD_create(String bfunkcia, String poradie) {
        return new BinaryDecisionDiagram(bfunkcia, poradie);
    }

    public static char BDD_use(BinaryDecisionDiagram bdd, String vstupy){
        return bdd.use(vstupy) ? '1' : '0';
    }

}