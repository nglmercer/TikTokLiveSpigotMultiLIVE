package io.github.jwdeveloper.spigot.tiktok.profiles.parser.handlers.statements.definitiona;

import io.github.jwdeveloper.spigot.tiktok.profiles.ast.statements.ParametersStatement;
import io.github.jwdeveloper.spigot.tiktok.profiles.ast.statements.definitions.ArrayDefinitionStatement;
import io.github.jwdeveloper.spigot.tiktok.profiles.parser.NodeFactory;
import io.github.jwdeveloper.spigot.tiktok.profiles.parser.ParserHandler;
import io.github.jwdeveloper.spigot.tiktok.profiles.tokenizer.TokenType;
import io.github.jwdeveloper.spigot.tiktok.profiles.tokenizer.Tokenizer;

public class ArrayDefinitionStatementHandler  implements ParserHandler<ArrayDefinitionStatement>
{

    @Override
    public ArrayDefinitionStatement handle(Tokenizer tokenizer, NodeFactory parserFactory, Object... parameters)
    {
        var paramters = parserFactory.createNode(ParametersStatement.class, null, TokenType.OPEN_ARRAY,TokenType.CLOSE_ARRAY);

        return new ArrayDefinitionStatement(paramters);
    }
}
