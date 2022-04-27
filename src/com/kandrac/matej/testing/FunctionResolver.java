package com.kandrac.matej.testing;

public class FunctionResolver {

    /**
     * Function resolves the expression with the given input
     * @param expression expression to be used
     * @param input input to be used
     * @return character 0 or 1
     */
    public char getResult(String expression, String input){
        for (String s : expression.split("\\+")) {
            if(checkValid(s, input))
                return '1';
        }
        return '0';
    }

    /**
     * Checks if the part of expression is valid
     * @param expressionPart part of expression
     * @param input input to use
     * @return true if expression is valid
     */
    private boolean checkValid(String expressionPart, String input) {
        for (String s : expressionPart.split("\\.")) {
            boolean inverted = s.charAt(0) == '!';
            char variable = s.charAt(inverted ? 1 : 0);
            char inputValue = input.charAt(variable-'A');
            if ((inverted ? '0' : '1') != inputValue)
                return false;
        }
        return true;
    }

}
