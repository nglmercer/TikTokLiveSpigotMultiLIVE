package io.github.jwdeveloper.spigot.tiktok.profiles.parser.handlers.statements;

import io.github.jwdeveloper.spigot.tiktok.profiles.parser.NodeFactory;
import io.github.jwdeveloper.spigot.tiktok.profiles.parser.ParserHandler;
import io.github.jwdeveloper.spigot.tiktok.profiles.ast.expressions.Expression;
import io.github.jwdeveloper.spigot.tiktok.profiles.ast.statements.ExpresionStatement;
import io.github.jwdeveloper.spigot.tiktok.profiles.parser.handlers.expressions.ExpressionHandler;
import io.github.jwdeveloper.spigot.tiktok.profiles.tokenizer.Tokenizer;

public class ExpresionStatementHandler implements ParserHandler<ExpresionStatement>
{
    @Override
    public ExpresionStatement handle(Tokenizer tokenizer, NodeFactory parserFactory, Object... parameters) {

        var expression =  parserFactory.createNode(Expression.class, ExpressionHandler.class);


        return new ExpresionStatement(expression);
    }
}
