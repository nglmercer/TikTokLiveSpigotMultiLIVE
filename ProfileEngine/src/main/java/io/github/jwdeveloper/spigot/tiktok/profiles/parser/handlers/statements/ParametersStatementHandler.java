package io.github.jwdeveloper.spigot.tiktok.profiles.parser.handlers.statements;

import io.github.jwdeveloper.spigot.tiktok.profiles.parser.NodeFactory;
import io.github.jwdeveloper.spigot.tiktok.profiles.parser.ParserHandler;
import io.github.jwdeveloper.spigot.tiktok.profiles.ast.expressions.Expression;
import io.github.jwdeveloper.spigot.tiktok.profiles.ast.statements.ParametersStatement;
import io.github.jwdeveloper.spigot.tiktok.profiles.tokenizer.TokenType;
import io.github.jwdeveloper.spigot.tiktok.profiles.tokenizer.Tokenizer;

import java.util.ArrayList;

public class ParametersStatementHandler implements ParserHandler<ParametersStatement> {

    @Override
    public ParametersStatement handle(Tokenizer tokenizer, NodeFactory parserFactory, Object... parameters) {

        var openType = TokenType.OPEN_ARGUMETNS;
        var closeType = TokenType.CLOSE_ARGUMENTS;
        if(parameters.length == 2)
        {
            openType = (TokenType) parameters[0];
            closeType = (TokenType) parameters[1];
         }


        var expressions = new ArrayList<Expression>();
        tokenizer.nextOrThrow(openType);
        if (tokenizer.lookupType(closeType)) {
            tokenizer.nextOrThrow(closeType);
            return new ParametersStatement(expressions);
        }
        do {
            var expression = parserFactory.createNode(Expression.class);
            expressions.add(expression);

        } while (tokenizer.lookupType(TokenType.COMMA) &&  tokenizer.next() != null);

        tokenizer.nextOrThrow(closeType);
        return new ParametersStatement(expressions);
    }
}
