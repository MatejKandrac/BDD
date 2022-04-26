import java.util.Arrays;
import java.util.Comparator;

public class ExpressionBuilder {
    private final StringBuilder builder = new StringBuilder();
    private boolean isAlwaysTrue = false;
    private boolean handled = false;
    public void appendFormat(String target, String expression) {
        handled = true;
        if (isAlwaysTrue || target.equals(expression)) {
            isAlwaysTrue = true;
            return;
        }
        if (target.startsWith(expression))
            appendWithCheck(target.substring(expression.length() + (target.length() > expression.length() ? 1 : 0)));
        else if (target.endsWith(expression))
            appendWithCheck(target.substring(0, target.length() - (expression.length() + 2)));
        else
            appendWithCheck(target.replace(expression+".", ""));
    }

    public void appendWithCheck(String expression) {
        handled = true;
        if (isDuplicatedExpression(expression))
            return;
        if (builder.length() != 0)
            builder.append("+");
        builder.append(expression);
    }

    private boolean isDuplicatedExpression(String expression) {
        String[] split = builder.toString().split("\\+");
        for (String s : split) {
            if (s.equals(expression))
                return true;
        }
        return false;
    }

    public boolean isNotHandled() {
        return !handled;
    }

    public boolean isAlwaysTrue() {
        return isAlwaysTrue || (builder.length() == 0 && handled);
    }

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
