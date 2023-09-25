package io.github.jwdeveloper.spigot.tiktok.profiles.tokenizer;

import lombok.Value;

@Value
public class Token
{
    TokenType tokenType;

    String value;

    int index;

    int line;

    int character;
}
