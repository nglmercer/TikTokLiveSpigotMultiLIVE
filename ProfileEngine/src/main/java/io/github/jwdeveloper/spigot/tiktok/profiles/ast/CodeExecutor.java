package io.github.jwdeveloper.spigot.tiktok.profiles.ast;

import io.github.jwdeveloper.spigot.tiktok.profiles.common.EvaluatorContext;

import java.util.List;

public interface CodeExecutor
{
    Object execute(EvaluatorContext context);
}
