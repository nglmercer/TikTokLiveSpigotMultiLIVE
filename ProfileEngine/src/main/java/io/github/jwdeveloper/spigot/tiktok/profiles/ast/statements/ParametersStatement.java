package io.github.jwdeveloper.spigot.tiktok.profiles.ast.statements;

import io.github.jwdeveloper.spigot.tiktok.profiles.ast.expressions.literals.IdentifierLiteral;
import io.github.jwdeveloper.spigot.tiktok.profiles.common.EvaluatorContext;
import io.github.jwdeveloper.spigot.tiktok.profiles.ast.expressions.Expression;
import lombok.Value;

import java.util.ArrayList;
import java.util.List;

@Value
public class ParametersStatement extends Statement
{
    List<Expression> parameters;

    @Override
    public List<Object> execute(EvaluatorContext context)
    {
        var result = new ArrayList<>();
        for(var parameter : parameters)
        {
            var parameterValue = parameter.execute(context);
            result.add(parameterValue);
        }
        return result;
    }

    public List<String> getParametersNames(EvaluatorContext context)
    {
        var result = new ArrayList<String>();
        for(var parameter : parameters)
        {
            var parameterName = (IdentifierLiteral)parameter;
            var name = parameterName.getName();
            result.add(name);
        }
        return result;
    }
}
