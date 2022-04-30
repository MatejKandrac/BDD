package com.kandrac.matej.testing;

import com.kandrac.matej.BinaryDecisionDiagram;

import java.util.Random;

import static com.kandrac.matej.Main.BDD_use;

public class Tester {

    private final Random random = new Random();

    /**
     * Generates DNF expression
     * @param variableCount total number of variables used
     * @return DNF expression
     */
    public String generateDNF(int variableCount) {
        StringBuilder builder = new StringBuilder();
        int numOfParts = random.nextInt(variableCount) + 1;
        int variablesInPart;
        for (int i = 0; i < numOfParts; i++) {
            variablesInPart = random.nextInt(variableCount/2) + 1;
            StringBuilder part = new StringBuilder();
            for (int i1 = 0; i1 < variablesInPart; i1++) {
                char character = ((char) (65 + random.nextInt(variableCount)));
                if (!part.toString().contains(Character.toString(character))){
                    boolean isNegated = random.nextBoolean();
                    part.append(isNegated ? "!" : "").append(character);
                    if (i1 != variablesInPart-1)
                        part.append('.');
                }
                else
                    i1--;
            }
            builder.append(part);
            if (i != numOfParts-1)
                builder.append('+');
        }
        return builder.toString();
    }

    /**
     * Order of variables. Override if you need other than classic order
     * @param variableCount number of variables
     * @return string of variables order
     */
    public String generateOrder(int variableCount) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < variableCount; i++)
            builder.append(((char) (65 + i)));
        return builder.toString();
    }

    /**
     * Check whether the diagram is valid. Compares result from FunctionResolver and BinaryDecisionTree.
     * This function tests all possible combinations of input.
     * @param tree binary decision diagram
     * @param variableCount number of variables
     * @param dnf expression
     * @return Total time in ms of BDD_use function
     */
    public long checkValid(BinaryDecisionDiagram tree, int variableCount, String dnf) {
        long totalTime = 0;
        FunctionResolver resolver = new FunctionResolver();
        int rows = (int) Math.pow(2, variableCount);

        StringBuilder builder = new StringBuilder();
        char treeResult;
        char functionResult;

        for (int i=0; i<rows; i++) {
            for (int j=variableCount-1; j>=0; j--) {
                builder.append((i/(int) Math.pow(2, j))%2);
            }

            long timeStamp = System.currentTimeMillis();
            treeResult = BDD_use(tree, builder.toString());
            totalTime += System.currentTimeMillis() - timeStamp;

            functionResult = resolver.getResult(dnf, builder.toString());
            if (treeResult != functionResult)
                System.out.println("Invalid result for input: " + builder + " Tree is: " + treeResult + " where function is: " + functionResult);
            builder = new StringBuilder();
        }
        return totalTime;
    }
}
