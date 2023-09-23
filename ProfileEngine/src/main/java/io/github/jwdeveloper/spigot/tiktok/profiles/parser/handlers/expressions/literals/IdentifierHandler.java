package io.github.jwdeveloper.spigot.tiktok.profiles.parser.handlers.expressions.literals;

import io.github.jwdeveloper.spigot.tiktok.profiles.common.exceptions.SymlEngineException;
import io.github.jwdeveloper.spigot.tiktok.profiles.parser.NodeFactory;
import io.github.jwdeveloper.spigot.tiktok.profiles.parser.ParserHandler;
import io.github.jwdeveloper.spigot.tiktok.profiles.ast.expressions.Expression;
import io.github.jwdeveloper.spigot.tiktok.profiles.ast.expressions.literals.IdentifierLiteral;
import io.github.jwdeveloper.spigot.tiktok.profiles.parser.handlers.expressions.members.MethodCallExpressionHandler;
import io.github.jwdeveloper.spigot.tiktok.profiles.tokenizer.TokenType;
import io.github.jwdeveloper.spigot.tiktok.profiles.tokenizer.Tokenizer;

public class IdentifierHandler implements ParserHandler<Expression> {


    @Override
    public Expression handle(Tokenizer tokenizer, NodeFactory parserFactory, Object... parameters) {
        var nextToken = tokenizer.lookup();

        if (nextToken.getTokenType() != TokenType.IDENTIFIER && nextToken.getTokenType() != TokenType.KEYWORLD) {
            throw new SymlEngineException("Expected identifier" + tokenizer.lookup());
        }

        var token = tokenizer.next();
        var identifier = new IdentifierLiteral(token.getValue());



        var type= tokenizer.lookup().getTokenType();
        if (type == TokenType.OPEN_ARGUMETNS)
        {
            return parserFactory.createNode(Expression.class,
                    MethodCallExpressionHandler.class,
                    identifier);
        }
        return identifier;
    }
}
