package io.github.jwdeveloper.spigot.tiktok.profiles.parser;

import io.github.jwdeveloper.ff.core.async.cancelation.CancelationToken;
import io.github.jwdeveloper.spigot.tiktok.profiles.ast.ProgramSYML;
import io.github.jwdeveloper.spigot.tiktok.profiles.ast.expressions.Expression;
import io.github.jwdeveloper.spigot.tiktok.profiles.ast.expressions.literals.Literal;
import io.github.jwdeveloper.spigot.tiktok.profiles.ast.statements.ExpresionStatement;
import io.github.jwdeveloper.spigot.tiktok.profiles.ast.statements.ParametersStatement;
import io.github.jwdeveloper.spigot.tiktok.profiles.ast.statements.Statement;
import io.github.jwdeveloper.spigot.tiktok.profiles.ast.statements.blocks.IfBlockStatement;
import io.github.jwdeveloper.spigot.tiktok.profiles.ast.statements.definitions.ArrayDefinitionStatement;
import io.github.jwdeveloper.spigot.tiktok.profiles.ast.statements.keywords.KeywordStatement;
import io.github.jwdeveloper.spigot.tiktok.profiles.ast.statements.keywords.RepeatStatement;
import io.github.jwdeveloper.spigot.tiktok.profiles.ast.statements.blocks.SwitchBlockStatement;
import io.github.jwdeveloper.spigot.tiktok.profiles.ast.statements.definitions.MethodDefinitionStatement;
import io.github.jwdeveloper.spigot.tiktok.profiles.ast.statements.keywords.ReturnStatement;
import io.github.jwdeveloper.spigot.tiktok.profiles.parser.handlers.ProgramSYMLHandler;
import io.github.jwdeveloper.spigot.tiktok.profiles.parser.handlers.expressions.BinaryExpressionHandler;
import io.github.jwdeveloper.spigot.tiktok.profiles.parser.handlers.expressions.ExpressionHandler;
import io.github.jwdeveloper.spigot.tiktok.profiles.parser.handlers.expressions.PrimaryExpressionHandler;
import io.github.jwdeveloper.spigot.tiktok.profiles.parser.handlers.expressions.UnaryExpressionHandler;
import io.github.jwdeveloper.spigot.tiktok.profiles.parser.handlers.expressions.literals.IdentifierHandler;
import io.github.jwdeveloper.spigot.tiktok.profiles.parser.handlers.expressions.literals.LiteralHandler;
import io.github.jwdeveloper.spigot.tiktok.profiles.parser.handlers.expressions.members.MemberExpressionHandler;
import io.github.jwdeveloper.spigot.tiktok.profiles.parser.handlers.expressions.members.MethodCallExpressionHandler;
import io.github.jwdeveloper.spigot.tiktok.profiles.parser.handlers.statements.*;
import io.github.jwdeveloper.spigot.tiktok.profiles.parser.handlers.statements.blocks.IfBlockStatementHandler;
import io.github.jwdeveloper.spigot.tiktok.profiles.parser.handlers.statements.KeywordStatementHandler;
import io.github.jwdeveloper.spigot.tiktok.profiles.parser.handlers.statements.blocks.SwitchBlockStatementHandler;
import io.github.jwdeveloper.spigot.tiktok.profiles.parser.handlers.statements.definitiona.ArrayDefinitionStatementHandler;
import io.github.jwdeveloper.spigot.tiktok.profiles.parser.handlers.statements.definitiona.MethodDefinitionStatementHandler;
import io.github.jwdeveloper.spigot.tiktok.profiles.tokenizer.Tokenizer;
import io.github.jwdeveloper.spigot.tiktok.profiles.tokenizer.TokenizerFactory;

public class ParserFactory
{

    public static Parser createParser(String input)
    {
        return createParser(TokenizerFactory.create(input));
    }

    public static Parser createParser(Tokenizer tokenIterator)
    {
        var builder = new ParserBuilder(tokenIterator, new CancelationToken());





        //Statements blocks
        builder.withHandler(ProgramSYML.class, ProgramSYMLHandler.class);
        builder.withHandler(ExpresionStatement.class, ExpresionStatementHandler.class);

        builder.withHandler(Statement.class, StatementHandler.class);
        builder.withHandler(IfBlockStatement.class, IfBlockStatementHandler.class);
        builder.withHandler(SwitchBlockStatement.class, SwitchBlockStatementHandler.class);
        builder.withHandler(ParametersStatement.class, ParametersStatementHandler.class);
        builder.withHandler(MethodDefinitionStatement.class, MethodDefinitionStatementHandler.class);
        builder.withHandler(KeywordStatement.class, KeywordStatementHandler.class);
        builder.withHandler(RepeatStatement.class, KeywordStatementHandler.class);
        builder.withHandler(ReturnStatement.class, KeywordStatementHandler.class);
        builder.withHandler(ArrayDefinitionStatement.class, ArrayDefinitionStatementHandler.class);

        //Expressions
        builder.withHandler(Expression.class, ExpressionHandler.class);
        builder.withHandler(Expression.class, PrimaryExpressionHandler.class);
        builder.withHandler(Expression.class, MemberExpressionHandler.class);
        builder.withHandler(Expression.class, MethodCallExpressionHandler.class);

        builder.withHandler(Expression.class, BinaryExpressionHandler.class);
        builder.withHandler(Expression.class, UnaryExpressionHandler.class);

        //Expressions literals
        builder.withHandler(Expression.class, IdentifierHandler.class);
     //   builder.withHandler(FunctionCallExpression.class, FunctionCallHandler.class);
        builder.withHandler(Literal.class, LiteralHandler.class);

    
        return builder.build();
    }
}
