package io.github.jwdeveloper.spigot.tiktok.profiles.ast.statements.blocks;

import io.github.jwdeveloper.ff.core.logger.plugin.FluentLogger;
import io.github.jwdeveloper.spigot.tiktok.profiles.common.EvaluatorContext;
import io.github.jwdeveloper.spigot.tiktok.profiles.ast.expressions.Expression;
import io.github.jwdeveloper.spigot.tiktok.profiles.ast.statements.Statement;

public class IfBlockStatement extends Statement
{
    private final Expression expression;
    private Statement consequent;
    private Statement alternate;

    public IfBlockStatement(Expression expression) {
        this.expression = expression;
    }


    public IfBlockStatement(Expression expression, Statement consequent) {
        this.expression = expression;
        this.consequent = consequent;
    }

    public IfBlockStatement(Expression expression, Statement consequent, Statement alternate) {
        this.expression = expression;
        this.consequent = consequent;
        this.alternate = alternate;
    }

    @Override
    public Object execute(EvaluatorContext context)
    {
        var expressionsValue = (boolean)expression.execute(context);


        if(this.consequent == null)
        {
            return expressionsValue;
        }

        if(expressionsValue)
        {
            return consequent.execute(context);
        }

        if (alternate != null)
        {
            return alternate.execute(context);
        }
        return false;
    }
}
