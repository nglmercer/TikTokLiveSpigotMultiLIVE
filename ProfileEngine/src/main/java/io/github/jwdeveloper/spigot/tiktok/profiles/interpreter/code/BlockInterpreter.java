package io.github.jwdeveloper.spigot.tiktok.profiles.interpreter.code;

import io.github.jwdeveloper.ff.core.common.java.StringUtils;
import io.github.jwdeveloper.spigot.tiktok.profiles.code.CodeBlock;
import io.github.jwdeveloper.spigot.tiktok.profiles.code.blocks.EmptyBlock;
import io.github.jwdeveloper.spigot.tiktok.profiles.interpreter.InterpreterBase;
import io.github.jwdeveloper.spigot.tiktok.profiles.interpreter.Tokenizer;
import io.github.jwdeveloper.spigot.tiktok.profiles.interpreter.code.literals.LiteralInterpreter;
import io.github.jwdeveloper.spigot.tiktok.profiles.interpreter.code.literals.TextLineInterpeter;

public class BlockInterpreter extends InterpreterBase {
    private final LiteralInterpreter literalInterpreter;
    private final IfInterpreter ifInterpreter;
    private final SwitchInterpeter switchInterpeter;

    public BlockInterpreter(Tokenizer tokenizer) {
        super(tokenizer);
        this.literalInterpreter = new LiteralInterpreter(tokenizer, new TextLineInterpeter());

        this.ifInterpreter = new IfInterpreter(tokenizer, this, literalInterpreter);
        this.switchInterpeter = new SwitchInterpeter(tokenizer, literalInterpreter);
    }

    public CodeBlock getCodeBlock()
    {
        return getCodeBlock(null);
    }

    public CodeBlock getCodeBlock(String stopValue)
    {
        var root = new CodeBlock();
        CodeBlock temp;

        do {
            if(stopValue != null && tokenizer.isLookup(stopValue))
            {
                break;
            }
            if (StringUtils.isNullOrEmpty(tokenizer.lookup(1)))
            {
                tokenizer.next();
                temp = new EmptyBlock();
                root.addChild(temp);
                continue;
            }

            if (tokenizer.lookup().startsWith("if"))
            {
                temp = ifInterpreter.getCodeBlock();
                root.addChild(temp);
                continue;
            }

            if (tokenizer.lookup().startsWith("switch"))
            {
                temp = switchInterpeter.getCodeBlock();
                root.addChild(temp);
                continue;
            }

            if(tokenizer.isLastToken())
            {
                temp = literalInterpreter.getCodeBlock();
                root.addChild(temp);
                continue;
            }

            temp = literalInterpreter.getCodeBlock();
            root.addChild(temp);
        }
        while (tokenizer.hasNext());
        return root;
    }

}
