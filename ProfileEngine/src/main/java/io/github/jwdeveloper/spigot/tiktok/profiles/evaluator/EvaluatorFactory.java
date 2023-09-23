package io.github.jwdeveloper.spigot.tiktok.profiles.evaluator;

import io.github.jwdeveloper.spigot.tiktok.profiles.common.SymlDefaultMethod;

public class EvaluatorFactory
{

    public static Evaliator create()
    {
        return builder().build();
    }

    public static EvaluatorBuilder builder()
    {
        var builder = new EvaluatorBuilder();
        builder.addMethod(new SymlDefaultMethod());
        return builder;
    }

}
