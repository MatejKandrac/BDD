public class FunctionResolver {

    public char getResult(String expression, String input){
        for (String s : expression.split("\\+")) {
            if(checkValid(s, input))
                return '1';
        }
        return '0';
    }

    private boolean checkValid(String expressionPart, String input) {
        for (String s : expressionPart.split("\\.")) {
            boolean inverted = s.charAt(0) == '!';
            char variable = s.charAt(inverted ? 1 : 0);
            char inputValue = input.charAt(input.indexOf(variable)+1);
            if ((inverted ? '0' : '1') != inputValue)
                return false;
        }
        return true;
    }

}
