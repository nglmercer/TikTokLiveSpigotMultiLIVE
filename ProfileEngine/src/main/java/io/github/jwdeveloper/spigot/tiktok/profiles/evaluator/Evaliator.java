package io.github.jwdeveloper.spigot.tiktok.profiles.evaluator;

import io.github.jwdeveloper.spigot.tiktok.profiles.ast.ProgramSYML;
import io.github.jwdeveloper.spigot.tiktok.profiles.common.EvaluatorContext;
import io.github.jwdeveloper.ff.core.common.TextBuilder;
import io.github.jwdeveloper.spigot.tiktok.profiles.parser.ParserFactory;
import io.github.jwdeveloper.spigot.tiktok.profiles.tokenizer.TokenizerFactory;
import lombok.Getter;

public class Evaliator {
    @Getter
    private final EvaluatorContext context;

    public Evaliator(EvaluatorContext context) {
        this.context = context;
    }

    public Evaliator evaluate(String input) {
        var tokenizer = TokenizerFactory.create(input);
        var parser = ParserFactory.createParser(tokenizer);
        parser.parse().execute(context);
        return this;
    }

    public Evaliator evaluate(ProgramSYML program) {
        program.execute(context);
        return this;
    }

    public Evaliator print()
    {
        var i = 123;
        var builder = new TextBuilder();
        builder.textNewLine("Variables: ");
        for(var variable : context.getVariables().entrySet())
        {
            builder.text(" - ").text(variable.getKey()).space().text(variable.getValue().getValue()).newLine();
        }

        builder.textNewLine("Methods: ");
        for(var variable : context.getMethods().entrySet())
        {
            builder.text(" - ").textNewLine(variable.getKey());
        }

        builder.textNewLine("Output: ");
        for(var output : context.getCollectedOutput())
        {
            builder.textNewLine(output);
        }

        System.out.println(builder.toString());
        return this;
    }
}
