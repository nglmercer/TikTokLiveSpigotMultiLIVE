package io.github.jwdeveloper.spigot.tiktok.profiles.tokenizer;

import io.github.jwdeveloper.spigot.tiktok.profiles.common.exceptions.SymlEngineException;
import lombok.Getter;

import java.util.Iterator;
import java.util.List;

public class Tokenizer implements Iterator<Token> {
    //String input = "event name 12 \"my ${siema} name if jezz\", 'my name if jeff' ${asda${ }sdssd}";

    @Getter
    private List<Token> tokens;

    @Getter
    private int currentIndex = -1;

    private Token current = new Token(TokenType.BEGIN, "",0,0,0);


    public Tokenizer(List<Token> tokens) {
        this.tokens = tokens;
    }

    @Override
    public boolean hasNext() {
        return currentIndex < tokens.size() - 1;
    }

    public boolean isLastToken() {
        return !hasNext();
    }

    public boolean isCurrent(String value) {
        return current().equals(value);
    }

    public Token current() {
        return current;
    }

    public Token lookup(int offset) {
        if (currentIndex + offset >= tokens.size()) {
            return tokens.get(tokens.size() - 1);
        }
        return tokens.get(currentIndex + offset);
    }

    public boolean lookupType(TokenType value) {
        return lookup(1).getTokenType().equals(value);
    }

    public boolean lookupValue(String value) {
        return lookup(1).getValue().equals(value);
    }

    public Token lookup() {
        return lookup(1);
    }

    @Override
    public Token next() {
        current = lookup(1);
        currentIndex++;
        return current();
    }

    public Token nextOrThrow(TokenType required) {
        var nextValue = next();
        if (!nextValue.getTokenType().equals(required)) {
            throw new SymlEngineException("Next Token should be " + required + " but was " + nextValue);
        }
        return nextValue;
    }

    public Token nextValueOrThrow(String required) {
        var nextValue = next();
        if (!nextValue.getValue().equals(required)) {
            throw new SymlEngineException("Next Token should be " + required + " but was " + nextValue);
        }
        return nextValue;
    }


    public Token nextOrThrow(TokenType required, String value) {
        var nextValue = next();
        if (!nextValue.getTokenType().equals(required) && !nextValue.getValue().equals(value)) {
            throw new SymlEngineException("Next Token should be " + required + " but was " + nextValue);
        }
        return nextValue;
    }

    public Token currentOrThrow(TokenType required) {
        var nextValue = current();
        if (!nextValue.getTokenType().equals(required)) {
            throw new SymlEngineException("Next Token should be " + required + " but was " + nextValue);
        }
        return nextValue;
    }


    public void print() {
        var index = 0;
        for (var token : tokens) {
            var sb = new StringBuilder();
            sb.append(index)
                    .append(" ").append(token.getTokenType())
                    .append(" ").append(token.getValue());
            index++;

            System.out.println(sb.toString());
        }
    }

}
