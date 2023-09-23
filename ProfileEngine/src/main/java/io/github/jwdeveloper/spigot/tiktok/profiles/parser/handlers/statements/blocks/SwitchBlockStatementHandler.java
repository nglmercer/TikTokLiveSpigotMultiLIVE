package io.github.jwdeveloper.spigot.tiktok.profiles.parser.handlers.statements.blocks;


import io.github.jwdeveloper.spigot.tiktok.profiles.parser.NodeFactory;
import io.github.jwdeveloper.spigot.tiktok.profiles.parser.ParserHandler;
import io.github.jwdeveloper.spigot.tiktok.profiles.ast.expressions.Expression;
import io.github.jwdeveloper.spigot.tiktok.profiles.ast.expressions.literals.Literal;
import io.github.jwdeveloper.spigot.tiktok.profiles.ast.statements.blocks.SwitchBlockStatement;
import io.github.jwdeveloper.spigot.tiktok.profiles.parser.handlers.expressions.BinaryExpressionHandler;
import io.github.jwdeveloper.spigot.tiktok.profiles.parser.handlers.expressions.ExpressionHandler;
import io.github.jwdeveloper.spigot.tiktok.profiles.parser.handlers.expressions.literals.LiteralHandler;
import io.github.jwdeveloper.spigot.tiktok.profiles.tokenizer.TokenType;
import io.github.jwdeveloper.spigot.tiktok.profiles.tokenizer.Tokenizer;

import java.util.ArrayList;

public class SwitchBlockStatementHandler implements ParserHandler<SwitchBlockStatement> {
    @Override
    public SwitchBlockStatement handle(Tokenizer tokenizer, NodeFactory parserFactory, Object... parameters) {
        tokenizer.nextOrThrow(TokenType.KEYWORLD, "switch");
        var switchCondition = parserFactory.createNode(Expression.class, ExpressionHandler.class);


        var caseses = new ArrayList<SwitchBlockStatement.SwitchCase>();
        while (tokenizer.lookupValue("case") &&
                tokenizer.nextOrThrow(TokenType.KEYWORLD, "case") != null) {
            var caseCondition = parserFactory.createNode(Expression.class, BinaryExpressionHandler.class);
            tokenizer.nextOrThrow(TokenType.KEYWORLD, "then");
            var caseAction = parserFactory.createNode(Expression.class, ExpressionHandler.class);

            caseses.add(new SwitchBlockStatement.SwitchCase(caseCondition, caseAction));
        }

        if (!tokenizer.lookupValue("default")) {
            return new SwitchBlockStatement(switchCondition, caseses);
        }
        tokenizer.nextOrThrow(TokenType.KEYWORLD, "default");
        tokenizer.nextOrThrow(TokenType.KEYWORLD, "then");
        var caseAction = parserFactory.createNode(Expression.class, ExpressionHandler.class);
        var defaultCase = new SwitchBlockStatement.SwitchCase(null, caseAction);
        return new SwitchBlockStatement(switchCondition, caseses, defaultCase);
    }
}
