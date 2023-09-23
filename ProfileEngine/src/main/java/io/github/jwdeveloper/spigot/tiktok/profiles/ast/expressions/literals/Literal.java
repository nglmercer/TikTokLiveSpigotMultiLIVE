package io.github.jwdeveloper.spigot.tiktok.profiles.ast.expressions.literals;

import io.github.jwdeveloper.spigot.tiktok.profiles.common.EvaluatorContext;
import io.github.jwdeveloper.spigot.tiktok.profiles.ast.expressions.Expression;
import io.github.jwdeveloper.spigot.tiktok.profiles.tokenizer.TokenType;
import lombok.Data;

@Data
public class Literal extends Expression
{
    protected TokenType literalType;

    protected Object value;

    public Literal(Object value, TokenType literalType)
    {
        this.value = value;
        this.literalType = literalType;
    }

    @Override
    public Object execute(EvaluatorContext context)
    {
        return value;
    }


}
