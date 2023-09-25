package io.github.jwdeveloper.spigot.tiktok.profiles.parser.handlers.expressions.members;

import io.github.jwdeveloper.spigot.tiktok.profiles.parser.NodeFactory;
import io.github.jwdeveloper.spigot.tiktok.profiles.parser.ParserHandler;
import io.github.jwdeveloper.spigot.tiktok.profiles.ast.expressions.Expression;
import io.github.jwdeveloper.spigot.tiktok.profiles.ast.expressions.members.MemberExpression;
import io.github.jwdeveloper.spigot.tiktok.profiles.parser.handlers.expressions.PrimaryExpressionHandler;
import io.github.jwdeveloper.spigot.tiktok.profiles.parser.handlers.expressions.literals.IdentifierHandler;
import io.github.jwdeveloper.spigot.tiktok.profiles.tokenizer.TokenType;
import io.github.jwdeveloper.spigot.tiktok.profiles.tokenizer.Tokenizer;

public class MemberExpressionHandler implements ParserHandler<Expression> {
    @Override
    public Expression handle(Tokenizer tokenizer, NodeFactory parserFactory, Object... parameters) {
        var objectTarget = parserFactory.createNode(Expression.class, PrimaryExpressionHandler.class);
        while (tokenizer.lookupType(TokenType.DOT) ||
                tokenizer.lookupType(TokenType.OPEN_ARRAY) ||
                tokenizer.lookupType(TokenType.OPEN_ARGUMENTS)) {


           if (tokenizer.lookupType(TokenType.DOT)) {
                tokenizer.next();
                var identifier = parserFactory.createNode(Expression.class, IdentifierHandler.class);
                objectTarget = new MemberExpression(false, objectTarget, identifier);
            }
        }


        return objectTarget;
    }
}
