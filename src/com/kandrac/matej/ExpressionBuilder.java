package com.kandrac.matej;

import java.util.Arrays;
import java.util.Comparator;

public class ExpressionBuilder {
    private final StringBuilder builder = new StringBuilder();
    private boolean isAlwaysTrue = false;
    private boolean isAlwaysFalse = true;

    /**
     * Method appends expression to the builder. If the target variable to be added is the same as expression,
     * expression is always true and so it marks the isAlwaysTrue property and never appends another target.
     * If it is not, it calls appendWithCheck method with expression without the target variable.
     * @param target part of expression to be decomposed
     * @param expression expression from which the target is decomposed
     */
    public void appendFormat(String target, String expression) {
        isAlwaysFalse = false;
        if (isAlwaysTrue || target.equals(expression)) {
            isAlwaysTrue = true;
            return;
        }
        if (target.startsWith(expression))
            // Removes target from start (checks for negation too)
            appendWithCheck(target.substring(expression.length() + (target.length() > expression.length() ? 1 : 0)));
        else if (target.endsWith(expression))
            // Removes target from end (checks for negation too)
            appendWithCheck(target.substring(0, target.length() - (expression.length() + 2)));
        else
            // Removes from center
            appendWithCheck(target.replace(expression+".", ""));
    }

    /**
     * Method checks if the entry is duplicated and if true, it ignores it else it appends the builder with expression.
     * Uses isDuplicatedExpression function.
     * @param expression Expression to be appended
     */
    public void appendWithCheck(String expression) {
        isAlwaysFalse = false;
        if (isDuplicatedExpression(expression))
            return;
        if (builder.length() != 0)
            builder.append("+");
        builder.append(expression);
    }

    /**
     * Checks for duplicates in builder.
     * @param expression expression to check
     * @return true if duplicated
     */
    private boolean isDuplicatedExpression(String expression) {
        String[] split = builder.toString().split("\\+");
        for (String s : split) {
            if (s.equals(expression))
                return true;
        }
        return false;
    }

    /**
     * Checks if the builder was appended at least once.
     * @return true if builder is always false
     */
    public boolean isAlwaysFalse() {
        return isAlwaysFalse;
    }

    /**
     * Checks if the expression to be built is always true.
     * @return true if builder is always true
     */
    public boolean isAlwaysTrue() {
        return isAlwaysTrue;
    }

    /**
     * Formats the input so all variables are alphabetically ordered
     * @param input Expression to format
     * @return formatted expression
     */
    static String formatInput(String input) {
        StringBuilder format = new StringBuilder();
        String[] expressions = input.split("\\+");
        for (int i = 0; i < expressions.length; i++) {
            String[] variables = expressions[i].split("\\.");

            Arrays.sort(variables, Comparator.comparingInt(o -> (o.length() == 2 ? o.charAt(1) : o.charAt(0))));

            for (int i1 = 0; i1 < variables.length; i1++) {
                format.append(variables[i1]);
                if (i1 < variables.length-1)
                    format.append(".");
            }

            if (i < expressions.length-1)
                format.append("+");

        }
        return format.toString();
    }

    @Override
    public String toString() {
        return builder.toString();
    }
}
