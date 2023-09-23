package io.github.jwdeveloper.spigot.tiktok.profiles.parser;

import io.github.jwdeveloper.spigot.tiktok.profiles.tokenizer.Tokenizer;

public interface ParserHandler<T>
{
     T handle(Tokenizer tokenizer, NodeFactory parserFactory, Object ... parameters);
}
