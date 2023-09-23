package io.github.jwdeveloper.spigot.tiktok.profiles.ast.statements.keywords;

import io.github.jwdeveloper.spigot.tiktok.profiles.ast.expressions.Expression;
import io.github.jwdeveloper.spigot.tiktok.profiles.common.EvaluatorContext;
import lombok.Value;

@Value
public class ReturnStatement extends KeywordStatement
{
    Expression returningExpression;

    @Override
    public Object execute(EvaluatorContext context)
    {
        return returningExpression.execute(context);
    }
}
