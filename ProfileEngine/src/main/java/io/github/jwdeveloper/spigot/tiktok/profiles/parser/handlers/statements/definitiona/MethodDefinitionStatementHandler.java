package io.github.jwdeveloper.spigot.tiktok.profiles.parser.handlers.statements.definitiona;

import io.github.jwdeveloper.spigot.tiktok.profiles.ast.expressions.literals.CommandLiteral;
import io.github.jwdeveloper.spigot.tiktok.profiles.ast.statements.keywords.ReturnStatement;
import io.github.jwdeveloper.spigot.tiktok.profiles.parser.NodeFactory;
import io.github.jwdeveloper.spigot.tiktok.profiles.parser.ParserHandler;
import io.github.jwdeveloper.spigot.tiktok.profiles.ast.ProgramSYML;
import io.github.jwdeveloper.spigot.tiktok.profiles.ast.expressions.Expression;
import io.github.jwdeveloper.spigot.tiktok.profiles.ast.statements.ParametersStatement;
import io.github.jwdeveloper.spigot.tiktok.profiles.ast.statements.definitions.MethodDefinitionStatement;
import io.github.jwdeveloper.spigot.tiktok.profiles.tokenizer.Tokenizer;

public class MethodDefinitionStatementHandler  implements ParserHandler<MethodDefinitionStatement>
{
    @Override
    public MethodDefinitionStatement handle(Tokenizer tokenizer, NodeFactory parserFactory, Object... parameters)
    {
        var methodName= (Expression) parameters[0];

        var paramters = parserFactory.createNode(ParametersStatement.class);

        tokenizer.nextValueOrThrow("=");
        tokenizer.nextValueOrThrow(">");




        var body =  parserFactory.createNode(ProgramSYML.class,null, ReturnStatement.class);

        return new MethodDefinitionStatement(methodName, paramters, body);
    }
}
