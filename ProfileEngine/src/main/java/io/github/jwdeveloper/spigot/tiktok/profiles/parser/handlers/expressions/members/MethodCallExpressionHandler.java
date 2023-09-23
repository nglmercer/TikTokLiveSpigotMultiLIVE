package io.github.jwdeveloper.spigot.tiktok.profiles.parser.handlers.expressions.members;

import io.github.jwdeveloper.spigot.tiktok.profiles.parser.NodeFactory;
import io.github.jwdeveloper.spigot.tiktok.profiles.parser.ParserHandler;
import io.github.jwdeveloper.spigot.tiktok.profiles.ast.expressions.literals.IdentifierLiteral;
import io.github.jwdeveloper.spigot.tiktok.profiles.ast.expressions.members.MethodCallExpression;
import io.github.jwdeveloper.spigot.tiktok.profiles.ast.statements.ParametersStatement;
import io.github.jwdeveloper.spigot.tiktok.profiles.parser.handlers.statements.ParametersStatementHandler;
import io.github.jwdeveloper.spigot.tiktok.profiles.tokenizer.Tokenizer;

public class MethodCallExpressionHandler implements ParserHandler<MethodCallExpression>
{

    @Override
    public MethodCallExpression handle(Tokenizer tokenizer, NodeFactory parserFactory, Object... parameters)
    {
        var methodName = (IdentifierLiteral)parameters[0];
        var methodParameters = parserFactory.createNode(ParametersStatement.class, ParametersStatementHandler.class);
        return new MethodCallExpression(methodName, methodParameters);
    }
}
