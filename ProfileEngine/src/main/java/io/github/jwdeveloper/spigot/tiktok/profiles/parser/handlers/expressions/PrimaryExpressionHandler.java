package io.github.jwdeveloper.spigot.tiktok.profiles.parser.handlers.expressions;

import io.github.jwdeveloper.spigot.tiktok.profiles.common.exceptions.SymlEngineException;
import io.github.jwdeveloper.spigot.tiktok.profiles.parser.NodeFactory;
import io.github.jwdeveloper.spigot.tiktok.profiles.parser.ParserHandler;
import io.github.jwdeveloper.spigot.tiktok.profiles.ast.expressions.Expression;
import io.github.jwdeveloper.spigot.tiktok.profiles.ast.expressions.literals.Literal;
import io.github.jwdeveloper.spigot.tiktok.profiles.parser.handlers.expressions.literals.IdentifierHandler;
import io.github.jwdeveloper.spigot.tiktok.profiles.tokenizer.TokenType;
import io.github.jwdeveloper.spigot.tiktok.profiles.tokenizer.Tokenizer;

public class PrimaryExpressionHandler implements ParserHandler<Expression> {
    @Override
    public Expression handle(Tokenizer tokenizer, NodeFactory parserFactory, Object... parameters) {
        var token = tokenizer.lookup();
        if (IsLiteral(token.getTokenType()))
        {
            return parserFactory.createNode(Literal.class);
        }
        return switch (token.getTokenType()) {

            case IDENTIFIER -> parserFactory.createNode(Expression.class, IdentifierHandler.class);
            //default -> parserFactory.createNode(Expression.class, PrimaryExpressionHandler.class);
            default -> throw new SymlEngineException("Unknown token "+token.toString());
        };
    }

    private boolean IsLiteral(TokenType token) {
        return token == TokenType.BOOL ||
                token == TokenType.NUMBER ||
                token == TokenType.MINECRAFT_COMMENT ||
                token == TokenType.CODE_BLOCK ||
                token == TokenType.STRING;
    }
}
