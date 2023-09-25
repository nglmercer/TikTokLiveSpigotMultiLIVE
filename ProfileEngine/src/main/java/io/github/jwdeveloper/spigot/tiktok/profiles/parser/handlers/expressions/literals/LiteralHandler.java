package io.github.jwdeveloper.spigot.tiktok.profiles.parser.handlers.expressions.literals;

import io.github.jwdeveloper.spigot.tiktok.profiles.common.exceptions.SymlEngineException;
import io.github.jwdeveloper.spigot.tiktok.profiles.common.StringCodeBlockParser;
import io.github.jwdeveloper.spigot.tiktok.profiles.parser.NodeFactory;
import io.github.jwdeveloper.spigot.tiktok.profiles.parser.ParserFactory;
import io.github.jwdeveloper.spigot.tiktok.profiles.parser.ParserHandler;
import io.github.jwdeveloper.spigot.tiktok.profiles.ast.ProgramSYML;
import io.github.jwdeveloper.spigot.tiktok.profiles.ast.expressions.literals.CommandLiteral;
import io.github.jwdeveloper.spigot.tiktok.profiles.ast.expressions.literals.Literal;
import io.github.jwdeveloper.spigot.tiktok.profiles.ast.expressions.literals.StringLiteral;
import io.github.jwdeveloper.spigot.tiktok.profiles.tokenizer.Token;
import io.github.jwdeveloper.spigot.tiktok.profiles.tokenizer.TokenType;
import io.github.jwdeveloper.spigot.tiktok.profiles.tokenizer.Tokenizer;
import io.github.jwdeveloper.spigot.tiktok.profiles.tokenizer.TokenizerFactory;

import java.util.ArrayList;

public class LiteralHandler implements ParserHandler<Literal> {
    private final StringCodeBlockParser codeBlockParser;

    public LiteralHandler() {
        codeBlockParser = new StringCodeBlockParser();
    }

    @Override
    public Literal handle(Tokenizer tokenizer, NodeFactory parserFactory, Object... parameters) {
        var token = tokenizer.next();
        return switch (token.getTokenType()) {
            case BOOL -> new Literal(Boolean.parseBoolean(token.getValue()), token.getTokenType());
            case STRING, CODE_BLOCK -> handleStringLiteral(token);
            case MINECRAFT_COMMAND -> handleMinecraftCommand(token);
            case NUMBER -> new Literal(Integer.parseInt(token.getValue()), token.getTokenType());
            default -> throw new SymlEngineException("Unexpected literal " + token);
        };
    }

    private StringLiteral handleStringLiteral(Token token) {
        var codeContent = codeBlockParser.findCodeBlock(token.getValue());
        var codeBlocks = new ArrayList<ProgramSYML>();
        for (var occurance : codeContent.getOccurrences().entrySet()) {
            var tokenizer = TokenizerFactory.create(occurance.getValue());
            var parser = ParserFactory.createParser(tokenizer);
            var block = parser.parse();
            codeBlocks.add(block);
        }
        return new StringLiteral(token.getValue(), TokenType.STRING, codeContent.getMarkContent(), codeBlocks);
    }

    private Literal handleMinecraftCommand(Token token)
    {
        var stringLiteral = handleStringLiteral(token);
        return new CommandLiteral(token.getValue(), stringLiteral);
    }


}
