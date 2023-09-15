package io.github.jwdeveloper.spigot.tiktok.profiles.interpreter;

import io.github.jwdeveloper.spigot.tiktok.profiles.code.CodeBlock;

public abstract class InterpreterBase {

    protected final Tokenizer tokenizer;

    public InterpreterBase(Tokenizer tokenizer) {
        this.tokenizer = tokenizer;
    }

    public abstract CodeBlock getCodeBlock();

}
