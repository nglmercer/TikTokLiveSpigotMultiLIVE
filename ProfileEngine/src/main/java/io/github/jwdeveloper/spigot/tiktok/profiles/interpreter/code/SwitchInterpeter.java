package io.github.jwdeveloper.spigot.tiktok.profiles.interpreter.code;

import io.github.jwdeveloper.spigot.tiktok.profiles.code.blocks.switch_blocks.SwitchBlock;
import io.github.jwdeveloper.spigot.tiktok.profiles.code.blocks.switch_blocks.SwitchCase;
import io.github.jwdeveloper.spigot.tiktok.profiles.common.exceptions.ProfileEngineException;
import io.github.jwdeveloper.spigot.tiktok.profiles.interpreter.InterpreterBase;
import io.github.jwdeveloper.spigot.tiktok.profiles.interpreter.Tokenizer;
import io.github.jwdeveloper.spigot.tiktok.profiles.interpreter.code.literals.LiteralInterpreter;

import java.util.ArrayList;

public class SwitchInterpeter extends InterpreterBase {


    private final LiteralInterpreter literalInterpreter;

    public SwitchInterpeter(Tokenizer tokenizer, LiteralInterpreter literalInterpreter) {
        super(tokenizer);
        this.literalInterpreter = literalInterpreter;
    }

    @Override
    public SwitchBlock getCodeBlock()
    {
        tokenizer.nextOrThrow("switch");

        var switchInput = literalInterpreter.getCodeBlock();
        var cases = new ArrayList<SwitchCase>();
        while (tokenizer.hasNext())
        {
            var casee = getSwitchCase();
            cases.add(casee);
        }
        return new SwitchBlock(switchInput,cases);
    }

    public SwitchCase getSwitchCase()
    {
        if(tokenizer.isLookup("case"))
        {
            tokenizer.next();
            var caseValue  = literalInterpreter.getCodeBlock();
            tokenizer.nextOrThrow("then");
            var caseAction  = literalInterpreter.getCodeBlock();

            return new SwitchCase(false,caseValue,caseAction);
        }
        if(tokenizer.isLookup("default"))
        {
            tokenizer.next();
            tokenizer.nextOrThrow("then");
            var caseAction  = literalInterpreter.getCodeBlock();

            return new SwitchCase(true,null,caseAction);
        }

        throw new ProfileEngineException("Switch bad implementation, should be case or default");

    }
}
