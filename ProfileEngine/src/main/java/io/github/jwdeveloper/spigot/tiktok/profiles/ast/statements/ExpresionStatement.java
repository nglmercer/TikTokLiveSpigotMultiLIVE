package io.github.jwdeveloper.spigot.tiktok.profiles.ast.statements;

import io.github.jwdeveloper.spigot.tiktok.profiles.common.EvaluatorContext;
import io.github.jwdeveloper.spigot.tiktok.profiles.ast.expressions.Expression;
import lombok.Getter;

public class ExpresionStatement extends Statement
{
    @Getter
    private final Expression expression;

    public ExpresionStatement(Expression expression)
    {
       this.expression = expression;
    }

    @Override
    public Object execute(EvaluatorContext context)
    {
        return expression.execute(context);
    }
}
