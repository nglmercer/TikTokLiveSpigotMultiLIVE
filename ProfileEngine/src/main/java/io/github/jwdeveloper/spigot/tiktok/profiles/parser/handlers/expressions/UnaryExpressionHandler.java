package io.github.jwdeveloper.spigot.tiktok.profiles.parser.handlers.expressions;

import io.github.jwdeveloper.spigot.tiktok.profiles.parser.NodeFactory;
import io.github.jwdeveloper.spigot.tiktok.profiles.parser.ParserHandler;
import io.github.jwdeveloper.spigot.tiktok.profiles.ast.expressions.Expression;
import io.github.jwdeveloper.spigot.tiktok.profiles.ast.expressions.UnaryExpression;
import io.github.jwdeveloper.spigot.tiktok.profiles.parser.handlers.expressions.members.MemberExpressionHandler;
import io.github.jwdeveloper.spigot.tiktok.profiles.tokenizer.Tokenizer;

public class UnaryExpressionHandler implements ParserHandler<Expression> {
    @Override
    public Expression handle(Tokenizer tokenizer, NodeFactory parserFactory, Object... parameters) {
        if (tokenizer.lookupValue("!") || tokenizer.lookupValue("-")) {
            var token = tokenizer.next();
            var nextExpresion = parserFactory.createNode(Expression.class, UnaryExpressionHandler.class);
            return new UnaryExpression(token, nextExpresion);
        }
        return parserFactory.createNode(Expression.class, MemberExpressionHandler.class);
    }
}
