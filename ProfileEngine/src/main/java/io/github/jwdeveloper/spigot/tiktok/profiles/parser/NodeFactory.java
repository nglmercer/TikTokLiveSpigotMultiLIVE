package io.github.jwdeveloper.spigot.tiktok.profiles.parser;

import io.github.jwdeveloper.ff.core.async.cancelation.CancelationToken;
import io.github.jwdeveloper.ff.core.logger.plugin.FluentLogger;
import io.github.jwdeveloper.spigot.tiktok.profiles.ast.Node;
import io.github.jwdeveloper.spigot.tiktok.profiles.common.exceptions.SymlEngineException;
import io.github.jwdeveloper.spigot.tiktok.profiles.tokenizer.Tokenizer;

import java.util.Map;

public class NodeFactory {
    private final CancelationToken _ctx;
    private final Map<Class, Map<Class, ParserHandler>> _handlers;
    private final Tokenizer _tokenIterator;

    public NodeFactory(Map<Class, Map<Class, ParserHandler>> handlers,
                       Tokenizer tokenIterator,
                       CancelationToken ctx) {
        _ctx = ctx;
        _handlers = handlers;
        _tokenIterator = tokenIterator;
    }

    public <T extends Node> T createNode(Class<T> nodeType, Object... parameters)
    {
        try {
            return (T) createNode(nodeType, null, parameters);
        } catch (SymlEngineException e)
        {
            throw e;
        }

    }

    public <T extends Node> T createNode(Class<T> nodeType, Class<?> parserType, Object... parameters) {

        if (_ctx.isCancel()) {
            throw new SymlEngineException("Cancellation requested");
        }

        if (!_handlers.containsKey(nodeType)) {
            throw new SymlEngineException("Node for type {nodeType} not registered");
        }

        var parsers = _handlers.get(nodeType);

        if (parserType == null) {
            parserType = parsers.keySet().stream().findFirst().get();
        }

        if (parserType == null || !parsers.containsKey(parserType)) {
            throw new SymlEngineException("Parser for type " + parserType.getSimpleName() + "not registered");
        }
        var handler = parsers.get(parserType);
        try {

            return (T) handler.handle(_tokenIterator, this, parameters);
        } catch (SymlEngineException e) {
            throw e;
        } catch (Exception e) {
            throw new SymlEngineException("Unable to parse for node " + nodeType + " and parser " + parserType, e);
        }
    }
}
