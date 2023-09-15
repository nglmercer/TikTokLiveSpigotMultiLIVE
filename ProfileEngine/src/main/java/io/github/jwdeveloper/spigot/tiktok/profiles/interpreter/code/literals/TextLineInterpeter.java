package io.github.jwdeveloper.spigot.tiktok.profiles.interpreter.code.literals;

import io.github.jwdeveloper.spigot.tiktok.profiles.code.blocks.literals.TextLineBlock;
import io.github.jwdeveloper.spigot.tiktok.profiles.interpreter.CodeBlockParser;
import io.github.jwdeveloper.spigot.tiktok.profiles.interpreter.Tokenizer;
import io.github.jwdeveloper.spigot.tiktok.profiles.interpreter.code.BlockInterpreter;

public class TextLineInterpeter {
    private final CodeBlockParser codeBlockParser;

    public TextLineInterpeter() {
        this.codeBlockParser = new CodeBlockParser();
    }

    public TextLineBlock getCodeBlock(String line) {
        var codeContent = codeBlockParser.findCodeBlock(line);
        var root = new TextLineBlock(codeContent.getOrginalContent(), codeContent.getMarkContent());
        for (var content : codeContent.getOccurrences().entrySet()) {
            var tokenizer = new Tokenizer(content.getValue());
            var interpreter = new BlockInterpreter(tokenizer);
            var block = interpreter.getCodeBlock();
            block.setBlockIndex(content.getKey());
            root.addChild(block);
        }
        return root;
    }
}
