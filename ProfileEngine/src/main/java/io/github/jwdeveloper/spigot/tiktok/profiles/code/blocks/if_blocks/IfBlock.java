package io.github.jwdeveloper.spigot.tiktok.profiles.code.blocks.if_blocks;

import io.github.jwdeveloper.spigot.tiktok.profiles.code.ExecutorContext;
import io.github.jwdeveloper.spigot.tiktok.profiles.code.blocks.ExpressionBlock;
import lombok.Value;

@Value
public class IfBlock extends ExpressionBlock {
    ExpressionBlock left;
    String operator;
    ExpressionBlock right;


    public Object execute(ExecutorContext context) {
        var leftValue = left.execute(context);
        var rightValue = right.execute(context);


        if (leftValue instanceof Number && rightValue instanceof Float) {
            return HandleNumeric(leftValue, rightValue, operator);
        }

        if (operator.equals("!=") || operator.equals("not")) {
            return !leftValue.equals(rightValue);
        }

        if (operator.equals("==") || operator.equals("is")) {
            return leftValue.equals(rightValue);
        }

        var leftBool = (boolean) leftValue;
        var rightBool = (boolean) rightValue;

        if (operator.equals("&&") || operator.equals("and")) {
            return leftBool && rightBool;
        }

        if (operator.equals("||") || operator.equals("or")) {
            return leftBool || rightBool;
        }
        return false;
    }

    public boolean HandleNumeric(Object left, Object right, String symbol) {
        var leftNumber = Float.parseFloat(left.toString());
        var rightNumber = (float) right;

        return switch (symbol) {
            case "==", "is" -> leftNumber == rightNumber;
            case "!=", "not" -> leftNumber != rightNumber;
            case ">" -> leftNumber > rightNumber;
            case "<" -> leftNumber < rightNumber;
            case ">=" -> leftNumber >= rightNumber;
            case "<=" -> leftNumber <= rightNumber;
            default -> false;
        };
    }


}
