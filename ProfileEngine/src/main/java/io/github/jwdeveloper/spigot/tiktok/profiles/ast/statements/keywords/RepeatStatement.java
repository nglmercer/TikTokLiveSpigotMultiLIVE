package io.github.jwdeveloper.spigot.tiktok.profiles.ast.statements.keywords;

import io.github.jwdeveloper.ff.core.logger.plugin.FluentLogger;
import io.github.jwdeveloper.spigot.tiktok.profiles.ast.statements.Statement;
import io.github.jwdeveloper.spigot.tiktok.profiles.common.EvaluatorContext;
import io.github.jwdeveloper.spigot.tiktok.profiles.ast.expressions.Expression;
import io.github.jwdeveloper.spigot.tiktok.profiles.common.exceptions.SymlEngineException;
import lombok.Value;

@Value
public class RepeatStatement extends KeywordStatement {
    Expression repeatingCount;
    Expression repeatingItem;

    @Override
    public Object execute(EvaluatorContext context)
    {
        var count = repeatingCount.execute(context);

        if(!(count instanceof Integer))
        {
            throw new SymlEngineException("Repeat parameter must be an number but was -> "+count);
        }

        for(var i =1;i<=(int)count;i++)
        {
            context.setVariable("index",i);
            repeatingItem.execute(context);
        }
        context.removeVariable("index");

        return count;
    }
}
