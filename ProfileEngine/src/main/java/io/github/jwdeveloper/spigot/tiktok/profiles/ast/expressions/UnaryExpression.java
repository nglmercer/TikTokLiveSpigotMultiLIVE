package io.github.jwdeveloper.spigot.tiktok.profiles.ast.expressions;

import io.github.jwdeveloper.spigot.tiktok.profiles.common.EvaluatorContext;
import io.github.jwdeveloper.spigot.tiktok.profiles.common.exceptions.SymlEngineException;
import io.github.jwdeveloper.spigot.tiktok.profiles.tokenizer.Token;
import lombok.Value;

@Value
public class UnaryExpression extends Expression {
    Token operator;
    Expression argument;

    @Override
    public Object execute(EvaluatorContext context) {

        var operatorValue = operator.getValue();
        var value = argument.execute(context);


        switch (operatorValue) {
            case "-":
                if (value instanceof Number numberValue) {
                    var intValue = (int) numberValue;
                    return -intValue;
                }
                break;
            case "!":
                if (value instanceof Boolean boolValue) {
                    return !boolValue;
                }
                break;
            default:
                throw new SymlEngineException("Invalid unary expression: " + operatorValue);
        }

        return value;
    }
}
