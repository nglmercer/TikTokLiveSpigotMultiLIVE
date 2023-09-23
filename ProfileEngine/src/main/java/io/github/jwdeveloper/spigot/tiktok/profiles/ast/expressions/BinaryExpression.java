package io.github.jwdeveloper.spigot.tiktok.profiles.ast.expressions;

import io.github.jwdeveloper.spigot.tiktok.profiles.common.EvaluatorContext;
import io.github.jwdeveloper.spigot.tiktok.profiles.tokenizer.Token;

import java.util.List;

public class BinaryExpression extends Expression {
    protected final Expression left;
    protected final String operator;
    protected final Expression right;

    public BinaryExpression(Expression left, Token operation, Expression right) {
        this.left = left;
        this.operator = operation.getValue();
        this.right = right;
    }

    public Object execute(EvaluatorContext context) {

        var leftValue = left.execute(context);
        var rightValue = right.execute(context);

        if (operator.equals("!=") || operator.equals("not")) {
            return !leftValue.equals(rightValue);
        }

        if (operator.equals("==") || operator.equals("is")) {
            return leftValue.equals(rightValue);
        }

        if (leftValue instanceof Boolean a && rightValue instanceof Boolean b) {
            return handleBool(a, b, operator);
        }

        if (leftValue instanceof Integer a && rightValue instanceof Integer b) {
            return handleNumeric(a, b, operator);
        }

        if (leftValue instanceof String a && rightValue instanceof String b) {
            return handleStrings(a, b, operator);
        }

        if (operator.equals("contains")) {
            if (leftValue instanceof List a)
            {
                return a.contains(rightValue);
            }
        }
        return false;
    }

    public Object handleNumeric(Integer leftNumber, Integer rightNumber, String symbol) {
        return switch (symbol) {
            case ">" -> leftNumber > rightNumber;
            case "<" -> leftNumber < rightNumber;
            case ">=" -> leftNumber >= rightNumber;
            case "<=" -> leftNumber <= rightNumber;
            case "+" -> leftNumber + rightNumber;
            case "-" -> leftNumber - rightNumber;
            case "*" -> leftNumber * rightNumber;
            case "//" -> leftNumber / rightNumber;
            case "^" -> (int) Math.pow(leftNumber, rightNumber);
            default -> false;
        };
    }

    public Object handleStrings(String left, String right, String symbol) {
        return switch (symbol) {
            case "+" -> left + right;
            case "contains" -> left.contains(right);
            default -> left + right;
        };
    }
    
    public Object handleBool(boolean left, boolean right, String symbol)
    {
        return switch (symbol) {
            case "&&","and" -> left && right;
            case "||","or" -> left || right;
            default -> false;
        };
    }
}
