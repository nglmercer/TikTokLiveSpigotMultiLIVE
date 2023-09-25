package io.github.jwdeveloper.spigot.tiktok.profiles.parser.handlers.expressions;

import io.github.jwdeveloper.spigot.tiktok.profiles.ast.statements.definitions.ArrayDefinitionStatement;
import io.github.jwdeveloper.spigot.tiktok.profiles.parser.NodeFactory;
import io.github.jwdeveloper.spigot.tiktok.profiles.parser.ParserHandler;
import io.github.jwdeveloper.spigot.tiktok.profiles.ast.expressions.AssignmentExpression;
import io.github.jwdeveloper.spigot.tiktok.profiles.ast.expressions.Expression;
import io.github.jwdeveloper.spigot.tiktok.profiles.ast.statements.definitions.MethodDefinitionStatement;
import io.github.jwdeveloper.spigot.tiktok.profiles.tokenizer.Token;
import io.github.jwdeveloper.spigot.tiktok.profiles.tokenizer.TokenType;
import io.github.jwdeveloper.spigot.tiktok.profiles.tokenizer.Tokenizer;

public class ExpressionHandler implements ParserHandler<Expression> {

    @Override
    public Expression handle(Tokenizer tokenizer, NodeFactory parserFactory, Object... parameters) {
        var left = parserFactory.createNode(Expression.class, BinaryExpressionHandler.class);
        if (!isAssignmentType(tokenizer.lookup())) {
            return left;
        }

        /*if (left is not IdentifierLiteral && left is not MemberExpression)
        {
            throw new SyntaxException($"to assigned type must be of type {TokenType.IDENTIFIER}",
                    tokenIterator.CurrentToken());
        }*/

        var assignmentToken = tokenizer.nextOrThrow(TokenType.ASSIGNMENT);
        if(tokenizer.lookupType(TokenType.OPEN_ARGUMENTS))
        {
            var methodDefinition = parserFactory.createNode(MethodDefinitionStatement.class,null,left);
            return new AssignmentExpression(left, assignmentToken, methodDefinition);
        }
        if(tokenizer.lookupType(TokenType.OPEN_ARRAY))
        {
            var methodDefinition = parserFactory.createNode(ArrayDefinitionStatement.class,null,left);
            return new AssignmentExpression(left, assignmentToken, methodDefinition);
        }

        var right = parserFactory.createNode(Expression.class);
        return new AssignmentExpression(left, assignmentToken, right);
    }

    private boolean isAssignmentType(Token token) {
        return token.getTokenType().equals(TokenType.ASSIGNMENT) || token.getTokenType().equals(TokenType.COMPLEX_ASSIGNMENT);
    }
}
