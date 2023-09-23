package io.github.jwdeveloper.spigot.tiktok.profiles.parser;

import io.github.jwdeveloper.ff.core.logger.plugin.FluentLogger;
import io.github.jwdeveloper.spigot.tiktok.profiles.ast.ProgramSYML;
import io.github.jwdeveloper.spigot.tiktok.profiles.common.exceptions.SymlEngineException;

import java.util.ArrayList;

public class Parser {
    private final NodeFactory _nodeFactory;

    public Parser(NodeFactory nodeFactory) {
        _nodeFactory = nodeFactory;
    }

    public ProgramSYML parse()
    {
        try {
            return _nodeFactory.createNode(ProgramSYML.class);
        }
        catch (SymlEngineException e)
        {
            FluentLogger.LOGGER.info("SymlEngineExceptionSymlEngineExceptionSymlEngineExceptionSymlEngineExceptionSymlEngineException");
            throw e;
        }
        catch (Exception e)
        {
            FluentLogger.LOGGER.info("ExceptionExceptionExceptionExceptionException");
            throw e;
        }

    }
}
