package input;

import java.util.Random;

public class InputGenerator {

    private String order;
    private String bestOrder;
    private int oldVariableCount;
    private final Random random = new Random();

    public String generateDNF(int variableCount) {
        oldVariableCount = variableCount;
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
            }
            builder.append(part);
            if (i != numOfParts-1)
                builder.append('+');
        }
        return builder.toString();
    }

    public String generateOrder() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < oldVariableCount; i++)
            builder.append(((char) (65 + i)));
        order = builder.toString();
        return order;
    }

    public String generateInput() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < oldVariableCount; i++) {
            builder.append(((char) (48 + random.nextInt(2))));
        }
        return builder.toString();
    }

    public void markBestOrder() {
        bestOrder = order;
    }

    public String generateDNF() {
        return generateDNF(oldVariableCount);
    }
}
