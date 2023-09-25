package io.github.jwdeveloper.spigot.tiktok.profiles.parser.handlers.statements;

import io.github.jwdeveloper.spigot.tiktok.profiles.ast.expressions.Expression;
import io.github.jwdeveloper.spigot.tiktok.profiles.ast.expressions.literals.Literal;
import io.github.jwdeveloper.spigot.tiktok.profiles.ast.statements.keywords.DeleteStatement;
import io.github.jwdeveloper.spigot.tiktok.profiles.ast.statements.keywords.KeywordStatement;
import io.github.jwdeveloper.spigot.tiktok.profiles.ast.statements.keywords.RepeatStatement;
import io.github.jwdeveloper.spigot.tiktok.profiles.ast.statements.keywords.ReturnStatement;
import io.github.jwdeveloper.spigot.tiktok.profiles.common.exceptions.SymlEngineException;
import io.github.jwdeveloper.spigot.tiktok.profiles.parser.NodeFactory;
import io.github.jwdeveloper.spigot.tiktok.profiles.parser.ParserHandler;
import io.github.jwdeveloper.spigot.tiktok.profiles.parser.handlers.expressions.ExpressionHandler;
import io.github.jwdeveloper.spigot.tiktok.profiles.tokenizer.TokenType;
import io.github.jwdeveloper.spigot.tiktok.profiles.tokenizer.Tokenizer;

public class KeywordStatementHandler implements ParserHandler<KeywordStatement> {

    @Override
    public KeywordStatement handle(Tokenizer tokenizer, NodeFactory parserFactory, Object... parameters)
    {
        return switch (tokenizer.lookup().getValue()) {
            case "repeat" -> handleRepeat(tokenizer, parserFactory);
            case "return" -> handleReturn(tokenizer, parserFactory);
            case "delete" -> handleDelete(tokenizer, parserFactory);
            default -> throw new SymlEngineException("Unknown keyword value");
        };
    }

    private KeywordStatement handleRepeat(Tokenizer tokenizer, NodeFactory parserFactory) {
        tokenizer.nextOrThrow(TokenType.KEYWORLD, "repeat");
        var howManyTimesExpression = parserFactory.createNode(Expression.class, ExpressionHandler.class);
        var whatToInvoke = parserFactory.createNode(Expression.class, ExpressionHandler.class);
        return new RepeatStatement(howManyTimesExpression, whatToInvoke);
    }

    private KeywordStatement handleReturn(Tokenizer tokenizer, NodeFactory parserFactory) {
        tokenizer.nextOrThrow(TokenType.KEYWORLD, "return");
        var returnExpression = parserFactory.createNode(Expression.class, ExpressionHandler.class);
        return new ReturnStatement(returnExpression);
    }

    private KeywordStatement handleDelete(Tokenizer tokenizer, NodeFactory parserFactory) {
        tokenizer.nextOrThrow(TokenType.KEYWORLD, "delete");
        var returnExpression = parserFactory.createNode(Expression.class, ExpressionHandler.class);
        return new DeleteStatement(returnExpression);
    }
}
