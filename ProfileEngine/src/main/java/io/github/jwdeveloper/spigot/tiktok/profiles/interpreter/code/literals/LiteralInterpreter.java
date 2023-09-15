package io.github.jwdeveloper.spigot.tiktok.profiles.interpreter.code.literals;

import io.github.jwdeveloper.spigot.tiktok.profiles.code.blocks.literals.LiteralBlock;
import io.github.jwdeveloper.spigot.tiktok.profiles.interpreter.InterpreterBase;
import io.github.jwdeveloper.spigot.tiktok.profiles.interpreter.Tokenizer;
import io.github.jwdeveloper.spigot.tiktok.profiles.interpreter.code.MethodInterpeter;
import io.github.jwdeveloper.tiktok.events.TikTokEvent;

import java.util.regex.Pattern;

public class LiteralInterpreter extends InterpreterBase {
    private final Pattern numbersPattern;
    private final Pattern boolPattern;
    private final Pattern stringPattern;
    private final Pattern codeBlockPattern;
    private final TextLineInterpeter textLineInterpeter;
    private final JavaLiteralInterpeter javaLiteralInterpeter;
    private final MethodInterpeter methodInterpeter;

    public LiteralInterpreter(Tokenizer tokenizer, TextLineInterpeter textLineInterpeter) {
        super(tokenizer);
        this.textLineInterpeter = textLineInterpeter;
        this.javaLiteralInterpeter = new JavaLiteralInterpeter(tokenizer, this);
        this.methodInterpeter = new MethodInterpeter(this, tokenizer);
        numbersPattern = Pattern.compile("-?\\d+(\\.\\d+)?");
        stringPattern = Pattern.compile("([\"'])(.*?)\\1");
        boolPattern = Pattern.compile("\\b(true|false)\\b");
        codeBlockPattern = Pattern.compile("\\$\\{([^}]+)\\}");
    }


    public LiteralBlock getCodeBlock() {
        var content = tokenizer.next();

        if (stringPattern.matcher(content).find()) {
            if (codeBlockPattern.matcher(content).find()) {
                return textLineInterpeter.getCodeBlock(content);
            }
            return new LiteralBlock(content, String.class);
        }

        if (content.startsWith("event")) {
            return new LiteralBlock(content, TikTokEvent.class);
        }

        if (content.startsWith("java")) {
            return javaLiteralInterpeter.getCodeBlock();
        }

        if (numbersPattern.matcher(content).find()) {
            return new LiteralBlock(content, Number.class);
        }

        if (boolPattern.matcher(content).find()) {
            return new LiteralBlock(content, Boolean.class);
        }

        if (tokenizer.isLookup("("))
        {
            return methodInterpeter.getCodeBlock();
        }

        return new LiteralBlock(content, Object.class);
    }


}
