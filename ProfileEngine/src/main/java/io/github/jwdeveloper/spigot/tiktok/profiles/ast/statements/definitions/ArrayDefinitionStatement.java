package io.github.jwdeveloper.spigot.tiktok.profiles.ast.statements.definitions;

import io.github.jwdeveloper.spigot.tiktok.profiles.ast.expressions.Expression;
import io.github.jwdeveloper.spigot.tiktok.profiles.ast.statements.ParametersStatement;
import io.github.jwdeveloper.spigot.tiktok.profiles.common.EvaluatorContext;
import io.github.jwdeveloper.spigot.tiktok.profiles.common.definitions.ArrayDefinition;
import lombok.Value;

import java.util.List;

@Value
public class ArrayDefinitionStatement extends Expression
{
    ParametersStatement parametersStatement;

    @Override
    public ArrayDefinition execute(EvaluatorContext context)
    {
        return new ArrayDefinition(parametersStatement.execute(context));
    }
}
