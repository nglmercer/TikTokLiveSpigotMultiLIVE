package io.github.jwdeveloper.spigot.tiktok.profiles.evaluator;

import io.github.jwdeveloper.spigot.tiktok.profiles.common.EvaluatorContext;
import io.github.jwdeveloper.spigot.tiktok.profiles.common.definitions.MethodDefinition;
import io.github.jwdeveloper.spigot.tiktok.profiles.evaluator.utils.MethodRegistrator;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public class EvaluatorBuilder
{
    private final EvaluatorContext evaluatorContext;
    private final MethodRegistrator registrator;


    public EvaluatorBuilder() {
        this.evaluatorContext = new EvaluatorContext();
        this.registrator = new MethodRegistrator();
    }

    public EvaluatorBuilder addMethod(MethodDefinition functionDeclaration)
    {
        evaluatorContext.addMethod(functionDeclaration);
        return this;
    }

    public EvaluatorBuilder addMethod(String name, Function<List<Object>, Object> onInvoke)
    {
        evaluatorContext.addMethod(new MethodDefinition(name,onInvoke,true));
        return this;
    }

    public EvaluatorBuilder addMethod(Object object)
    {
        var methods = registrator.register(object);
        for(var method : methods)
        {
            addMethod(method);
        }
        return this;
    }

    public EvaluatorBuilder addVariable(String name, Object value)
    {
        evaluatorContext.setVariable(name,value);
        return this;
    }


    public EvaluatorBuilder addOnOutput(Consumer<String> output)
    {
        return this;
    }

    public Evaliator build()
    {
      return new Evaliator(evaluatorContext);
    }
}
