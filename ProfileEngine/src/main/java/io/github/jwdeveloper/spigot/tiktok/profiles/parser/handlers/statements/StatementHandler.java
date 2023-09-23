package io.github.jwdeveloper.spigot.tiktok.profiles.parser.handlers.statements;

import io.github.jwdeveloper.spigot.tiktok.profiles.ast.statements.keywords.KeywordStatement;
import io.github.jwdeveloper.spigot.tiktok.profiles.ast.statements.keywords.ReturnStatement;
import io.github.jwdeveloper.spigot.tiktok.profiles.common.exceptions.SymlEngineException;
import io.github.jwdeveloper.spigot.tiktok.profiles.parser.NodeFactory;
import io.github.jwdeveloper.spigot.tiktok.profiles.parser.ParserHandler;
import io.github.jwdeveloper.spigot.tiktok.profiles.ast.statements.ExpresionStatement;
import io.github.jwdeveloper.spigot.tiktok.profiles.ast.statements.blocks.IfBlockStatement;
import io.github.jwdeveloper.spigot.tiktok.profiles.ast.statements.Statement;
import io.github.jwdeveloper.spigot.tiktok.profiles.ast.statements.keywords.RepeatStatement;
import io.github.jwdeveloper.spigot.tiktok.profiles.ast.statements.blocks.SwitchBlockStatement;
import io.github.jwdeveloper.spigot.tiktok.profiles.tokenizer.Token;
import io.github.jwdeveloper.spigot.tiktok.profiles.tokenizer.TokenType;
import io.github.jwdeveloper.spigot.tiktok.profiles.tokenizer.Tokenizer;

public class StatementHandler implements ParserHandler<Statement> {

    @Override
    public Statement handle(Tokenizer tokenizer, NodeFactory parserFactory, Object... parameters) {
        var token = tokenizer.lookup();


        if(token.getTokenType() == TokenType.KEYWORLD)
        {
            return handleKeyWord(token,parserFactory);
        }


        return parserFactory.createNode(ExpresionStatement.class);
    }


    private Statement handleKeyWord(Token tokenType,  NodeFactory parserFactory)
    {
        return switch (tokenType.getValue()) {
            case "if" -> parserFactory.createNode(IfBlockStatement.class);
            case "switch" -> parserFactory.createNode(SwitchBlockStatement.class);
            default ->  parserFactory.createNode(KeywordStatement.class);
        };
    }
}
