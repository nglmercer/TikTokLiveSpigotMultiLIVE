package io.github.jwdeveloper.spigot.tiktok.profiles.parser.handlers.statements.blocks;

import io.github.jwdeveloper.spigot.tiktok.profiles.ast.statements.Statement;
import io.github.jwdeveloper.spigot.tiktok.profiles.parser.NodeFactory;
import io.github.jwdeveloper.spigot.tiktok.profiles.parser.ParserHandler;
import io.github.jwdeveloper.spigot.tiktok.profiles.ast.expressions.Expression;
import io.github.jwdeveloper.spigot.tiktok.profiles.ast.statements.ExpresionStatement;
import io.github.jwdeveloper.spigot.tiktok.profiles.ast.statements.blocks.IfBlockStatement;
import io.github.jwdeveloper.spigot.tiktok.profiles.parser.handlers.expressions.ExpressionHandler;
import io.github.jwdeveloper.spigot.tiktok.profiles.tokenizer.TokenType;
import io.github.jwdeveloper.spigot.tiktok.profiles.tokenizer.Tokenizer;

public class IfBlockStatementHandler implements ParserHandler<IfBlockStatement> {
    @Override
    public IfBlockStatement handle(Tokenizer tokenizer, NodeFactory parserFactory, Object... parameters) {
        tokenizer.nextOrThrow(TokenType.KEYWORLD, "if");
        var expression = parserFactory.createNode(Expression.class, ExpressionHandler.class);

        if (!tokenizer.lookupValue("then")) {
            return new IfBlockStatement(expression);
        }
        tokenizer.nextOrThrow(TokenType.KEYWORLD, "then");
        var thenStatement = parserFactory.createNode(Statement.class);

        if (!tokenizer.lookupValue("else")) {
            return new IfBlockStatement(expression, thenStatement);
        }
        tokenizer.nextOrThrow(TokenType.KEYWORLD, "else");
        var elseStatement = parserFactory.createNode(Statement.class);
        return new IfBlockStatement(expression, thenStatement, elseStatement);
    }
}
