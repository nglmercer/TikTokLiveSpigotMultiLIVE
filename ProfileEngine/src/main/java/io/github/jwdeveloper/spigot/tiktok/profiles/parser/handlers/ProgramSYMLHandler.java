package io.github.jwdeveloper.spigot.tiktok.profiles.parser.handlers;

import io.github.jwdeveloper.spigot.tiktok.profiles.ast.statements.ExpresionStatement;
import io.github.jwdeveloper.spigot.tiktok.profiles.parser.NodeFactory;
import io.github.jwdeveloper.spigot.tiktok.profiles.parser.ParserHandler;
import io.github.jwdeveloper.spigot.tiktok.profiles.ast.Node;
import io.github.jwdeveloper.spigot.tiktok.profiles.ast.ProgramSYML;
import io.github.jwdeveloper.spigot.tiktok.profiles.ast.statements.Statement;
import io.github.jwdeveloper.spigot.tiktok.profiles.tokenizer.TokenType;
import io.github.jwdeveloper.spigot.tiktok.profiles.tokenizer.Tokenizer;
import java.util.ArrayList;

public class ProgramSYMLHandler implements ParserHandler<ProgramSYML>
{

    @Override
    public ProgramSYML handle(Tokenizer tokenizer, NodeFactory parserFactory, Object... parameters)
    {

        Class loopUntil = System.class;
        if(parameters.length == 1)
        {
            loopUntil = (Class)parameters[0];
        }


        var statements = new ArrayList<Node>();
        try
        {
            while (tokenizer.hasNext())
            {
                if(tokenizer.lookupType(TokenType.END_OF_LINE))
                {
                    tokenizer.next();
                    return new ProgramSYML(statements);
                }


                var statement =  parserFactory.createNode(Statement.class);
                statements.add(statement);

                if(statement.getClass().isAssignableFrom(loopUntil))
                {
                    break;
                }

            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return new ProgramSYML(statements);
        }
        return new ProgramSYML(statements);
    }
}
