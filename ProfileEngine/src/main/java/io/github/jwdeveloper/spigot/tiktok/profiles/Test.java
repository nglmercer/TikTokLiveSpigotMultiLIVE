package io.github.jwdeveloper.spigot.tiktok.profiles;

import io.github.jwdeveloper.spigot.tiktok.profiles.evaluator.EvaluatorFactory;
import io.github.jwdeveloper.spigot.tiktok.profiles.parser.ParserFactory;
import io.github.jwdeveloper.spigot.tiktok.profiles.tokenizer.TokenizerFactory;

public class Test
{
    public static void main(String[] run)
    {

        var parser = ParserFactory.createParser(TokenizerFactory.create("""
                 i = 10
                """));
        parser.parse().print();
    }
}
