package io.github.jwdeveloper.spigot.tiktok.profiles.ast.expressions.literals;

import io.github.jwdeveloper.spigot.tiktok.profiles.common.EvaluatorContext;
import io.github.jwdeveloper.spigot.tiktok.profiles.tokenizer.TokenType;

public class IdentifierLiteral extends Literal
{
    private IdentifierLiteral nextCall;

    public IdentifierLiteral(String value, IdentifierLiteral nextCall)
    {
        super(value, TokenType.IDENTIFIER);
        this.nextCall = nextCall;
    }

    public IdentifierLiteral(String value)
    {
        super(value, TokenType.IDENTIFIER);
    }

    @Override
    public Object execute(EvaluatorContext context)
    {
        return getValue(context);
    }

    public Object getValue(EvaluatorContext context)
    {
        return context.getVariable((String)value);
    }

    public String getName()
    {
        return (String) value;
    }
}
