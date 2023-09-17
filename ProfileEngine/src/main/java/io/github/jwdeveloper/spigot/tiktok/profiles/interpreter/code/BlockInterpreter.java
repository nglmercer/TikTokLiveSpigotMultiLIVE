package io.github.jwdeveloper.spigot.tiktok.profiles.interpreter.code;

import io.github.jwdeveloper.ff.core.common.java.StringUtils;
import io.github.jwdeveloper.spigot.tiktok.profiles.code.CodeBlock;
import io.github.jwdeveloper.spigot.tiktok.profiles.code.blocks.EmptyBlock;
import io.github.jwdeveloper.spigot.tiktok.profiles.interpreter.InterpreterBase;
import io.github.jwdeveloper.spigot.tiktok.profiles.interpreter.Tokenizer;
import io.github.jwdeveloper.spigot.tiktok.profiles.interpreter.code.literals.LiteralInterpreter;
import io.github.jwdeveloper.spigot.tiktok.profiles.interpreter.code.statements.DeclarationInterpreter;
import io.github.jwdeveloper.spigot.tiktok.profiles.interpreter.code.statements.IfInterpreter;
import io.github.jwdeveloper.spigot.tiktok.profiles.interpreter.code.statements.RepeatInterpreter;
import io.github.jwdeveloper.spigot.tiktok.profiles.interpreter.code.statements.SwitchInterpeter;

public class BlockInterpreter extends InterpreterBase {
    private final LiteralInterpreter literalInterpreter;
    private final IfInterpreter ifInterpreter;
    private final SwitchInterpeter switchInterpeter;
    private final RepeatInterpreter repeatInterpreter;
    private final DeclarationInterpreter declarationInterpreter;

    public BlockInterpreter(Tokenizer tokenizer) {
        super(tokenizer);
        this.literalInterpreter = new LiteralInterpreter(tokenizer, new CodeLineInterpeter());

        this.ifInterpreter = new IfInterpreter(tokenizer, this, literalInterpreter);
        this.switchInterpeter = new SwitchInterpeter(tokenizer, literalInterpreter);
        this.repeatInterpreter = new RepeatInterpreter(tokenizer, literalInterpreter);
        this.declarationInterpreter = new DeclarationInterpreter(tokenizer, literalInterpreter );
    }

    public CodeBlock getCodeBlock() {
        return getCodeBlock(null);
    }

    public CodeBlock getCodeBlock(String stopValue) {
        var root = new CodeBlock();


        do {
            if (stopValue != null && tokenizer.isLookup(stopValue)) {
                break;
            }
            if (StringUtils.isNullOrEmpty(tokenizer.lookup(1))) {
                tokenizer.next();
                root.addChild(new EmptyBlock());
                continue;
            }

            if (tokenizer.lookup().startsWith("if")) {
                root.addChild(ifInterpreter.getCodeBlock());
                continue;
            }

            if (tokenizer.lookup().startsWith("switch")) {
                root.addChild(switchInterpeter.getCodeBlock());
                continue;
            }

            if (tokenizer.lookup().startsWith("repeat")) {
                root.addChild(repeatInterpreter.getCodeBlock());
                continue;
            }

            if (tokenizer.lookup().startsWith(":")) {
                root.addChild(declarationInterpreter.getCodeBlock());
                continue;
            }

            root.addChild(literalInterpreter.getCodeBlock());
        }
        while (tokenizer.hasNext());
        return root;
    }

}
