package io.github.jwdeveloper.spigot.tiktok.profiles.code.blocks;

import io.github.jwdeveloper.spigot.tiktok.profiles.code.CodeBlock;
import io.github.jwdeveloper.spigot.tiktok.profiles.code.ExecutorContext;

public class SingleExpressionBlock extends ExpressionBlock
{
    private final ExpressionBlock expression;

    public SingleExpressionBlock(ExpressionBlock expression) {
        this.expression = expression;
    }

    @Override
    public Object execute(ExecutorContext context) {

        var value = expression.execute(context);;
        if(value.getClass().equals(Integer.class))
        {
            return (int)value != 0;
        }
        if(value.getClass().equals(Boolean.class))
        {
            return (boolean)value == true;
        }

        return value != null;
    }
}
