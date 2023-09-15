package io.github.jwdeveloper.spigot.tiktok.profiles.code.blocks.if_blocks;

import io.github.jwdeveloper.ff.core.common.java.StringUtils;
import io.github.jwdeveloper.spigot.tiktok.profiles.code.CodeBlock;
import io.github.jwdeveloper.spigot.tiktok.profiles.code.ExecutorContext;
import io.github.jwdeveloper.spigot.tiktok.profiles.code.blocks.ExpressionBlock;

public class IfBodyBlock extends ExpressionBlock
{
    private final ExpressionBlock expression;
    private final CodeBlock thenBlock;
    private final CodeBlock elseBlock;


    public IfBodyBlock(ExpressionBlock expression1, CodeBlock thenBlock1, CodeBlock elseBlock1)
    {
        this.expression = expression1;
        this.thenBlock = thenBlock1;
        this.elseBlock = elseBlock1;
    }

    public IfBodyBlock(ExpressionBlock expression1, CodeBlock thenBlock1)
    {
        this.expression = expression1;
        this.thenBlock = thenBlock1;
        this.elseBlock = null;
    }

    @Override
    public Object execute(ExecutorContext context)
    {
        var expressionsValue = (boolean)expression.execute(context);

        if(expressionsValue)
        {
            return thenBlock.execute(context);
        }

        if (elseBlock != null)
        {
            return elseBlock.execute(context);
        }
        return StringUtils.EMPTY;
    }
}
