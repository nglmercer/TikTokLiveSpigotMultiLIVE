package io.github.jwdeveloper.spigot.tiktok.profiles.interpreter.code;

import io.github.jwdeveloper.spigot.tiktok.profiles.code.blocks.literals.LiteralBlock;
import io.github.jwdeveloper.spigot.tiktok.profiles.code.blocks.literals.MethodBlock;
import io.github.jwdeveloper.spigot.tiktok.profiles.interpreter.InterpreterBase;
import io.github.jwdeveloper.spigot.tiktok.profiles.interpreter.Tokenizer;
import io.github.jwdeveloper.spigot.tiktok.profiles.interpreter.code.literals.LiteralInterpreter;

import java.util.ArrayList;

public class MethodInterpeter extends InterpreterBase {
    private final LiteralInterpreter literalInterpreter;
    public MethodInterpeter(LiteralInterpreter literalInterpreter, Tokenizer tokenizer) {
        super(tokenizer);
        this.literalInterpreter = literalInterpreter;
    }

    public MethodBlock getCodeBlock()
    {
        var currentValue = tokenizer.current();
        tokenizer.nextOrThrow("(");
        var parameters = new ArrayList<LiteralBlock>();
        do {
            var parameter = literalInterpreter.getCodeBlock();
            parameters.add(parameter);
            if (tokenizer.isLookup(")")) {
                tokenizer.next();
                break;
            }
            tokenizer.next();
        }
        while (tokenizer.isCurrent(","));
        return new MethodBlock(currentValue, parameters);
    }


}
