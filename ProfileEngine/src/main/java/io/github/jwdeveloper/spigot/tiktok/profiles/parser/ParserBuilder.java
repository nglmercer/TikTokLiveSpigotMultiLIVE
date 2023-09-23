package io.github.jwdeveloper.spigot.tiktok.profiles.parser;

import io.github.jwdeveloper.ff.core.async.cancelation.CancelationToken;
import io.github.jwdeveloper.spigot.tiktok.profiles.common.exceptions.SymlEngineException;
import io.github.jwdeveloper.spigot.tiktok.profiles.tokenizer.Tokenizer;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class ParserBuilder
{
    private final Map<Class, Map<Class, ParserHandler>> _parserHandlers;
    private final CancelationToken _ctx;
    private final Tokenizer _tokenIterator;

    public ParserBuilder(Tokenizer tokenIterator, CancelationToken ctx)
    {
        _parserHandlers = new LinkedHashMap<>();
        _tokenIterator = tokenIterator;
        _ctx = ctx;
    }

    public  ParserBuilder withHandler(Class<?> nodeType, Class<? extends ParserHandler>  handlerType)
    {
        try {
            var handler = handlerType.newInstance();
            if (_parserHandlers.containsKey(nodeType))
            {
                var handlers = _parserHandlers.get(nodeType);
                handlers.put(handlerType, handler);
                return this;
            }

            var handlersDic = new LinkedHashMap<Class, ParserHandler>();
            handlersDic.put(handlerType, handler);
            _parserHandlers.put(nodeType, handlersDic);
        }
        catch (Exception e)
        {
            throw new SymlEngineException("Unable to create instance of "+ handlerType,e);
        }

        return this;
    }

    public Parser build()
    {
        var nodeFactory = new NodeFactory(_parserHandlers, _tokenIterator, _ctx);
        return new Parser(nodeFactory);
    }
}
