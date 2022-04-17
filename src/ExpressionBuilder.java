public class ExpressionBuilder {

    private StringBuilder builder = new StringBuilder();
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

    public int length(){
        return builder.length();
    }

    public boolean isNotHandled() {
        return !handled;
    }

    public boolean isAlwaysTrue() {
        return isAlwaysTrue || (builder.length() == 0 && handled);
    }

    @Override
    public String toString() {
        return builder.toString();
    }
}
