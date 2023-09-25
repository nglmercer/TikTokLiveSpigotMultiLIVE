package io.github.jwdeveloper.spigot.tiktok.profiles.ast.expressions.literals;

import io.github.jwdeveloper.spigot.tiktok.profiles.common.EvaluatorContext;
import io.github.jwdeveloper.spigot.tiktok.profiles.tokenizer.TokenType;

public class CommandLiteral extends Literal{
    private final StringLiteral stringLiteral;
    public CommandLiteral(Object value, StringLiteral stringLiteral)
    {
        super(value, TokenType.MINECRAFT_COMMAND);
        this.stringLiteral = stringLiteral;
    }

    @Override
    public Object execute(EvaluatorContext context) {

        var command = (String) stringLiteral.execute(context);
        if(command.startsWith("/"))
        {
            command = command.substring(1);
        }
        context.output(command);
        return command;
    }
}
