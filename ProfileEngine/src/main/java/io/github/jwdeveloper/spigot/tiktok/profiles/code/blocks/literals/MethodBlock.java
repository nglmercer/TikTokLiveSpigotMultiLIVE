package io.github.jwdeveloper.spigot.tiktok.profiles.code.blocks.literals;

import io.github.jwdeveloper.ff.core.common.java.StringUtils;
import io.github.jwdeveloper.spigot.tiktok.profiles.code.CodeBlock;
import io.github.jwdeveloper.spigot.tiktok.profiles.code.ExecutorContext;
import io.github.jwdeveloper.spigot.tiktok.profiles.code.blocks.ExpressionBlock;
import io.github.jwdeveloper.spigot.tiktok.profiles.code.blocks.literals.LiteralBlock;
import io.github.jwdeveloper.spigot.tiktok.profiles.common.exceptions.ProfileEngineException;
import lombok.Data;
import lombok.Getter;

import java.util.List;


@Getter
public class MethodBlock extends LiteralBlock
{
    private final List<LiteralBlock> parameters;
    public MethodBlock(String name, List<LiteralBlock> parameters)
    {
        super(name,Void.class);
        this.parameters = parameters;
    }

    @Override
    public Object execute(ExecutorContext context)
    {
        if(!context.getMethods().containsKey(this.getContent()))
        {
            throw new ProfileEngineException("Method of not found "+this.getContent());
        }

        var metodDefinition = context.getMethods().get(this.getContent());
        var params = parameters.stream().map(e -> e.execute(context)).toList();

        try
        {
           var result= metodDefinition.getOnInvoke().apply(params);
           if(result == null)
           {
               return StringUtils.EMPTY;
           }
           return result;
        }
        catch (Exception e)
        {
            throw new ProfileEngineException("Method exception: "+getContent(),e);
        }

    }
}
