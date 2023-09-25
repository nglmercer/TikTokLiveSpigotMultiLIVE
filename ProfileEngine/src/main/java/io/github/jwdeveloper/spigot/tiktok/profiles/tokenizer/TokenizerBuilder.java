package io.github.jwdeveloper.spigot.tiktok.profiles.tokenizer;

import io.github.jwdeveloper.ff.core.common.java.StringUtils;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class TokenizerBuilder {

    private Map<String, TokenType> mappings = new LinkedHashMap<>();
    private Map<Pattern, TokenType> patterns = new LinkedHashMap<>();

    public TokenizerBuilder addToken(TokenType tokenType, String... values) {
        for (var value : values) {
            mappings.put(value, tokenType);
        }
        return this;
    }

    public TokenizerBuilder addTokenRegex(TokenType tokenType, String pattern) {
        patterns.put(Pattern.compile(pattern), tokenType);
        return this;
    }

    public Tokenizer build(String input) {
        return new Tokenizer(getTokens(input));
    }

    private Token stringToToken(String value, int index) {
        var type = TokenType.IDENTIFIER;

        if (mappings.containsKey(value)) {
            type = mappings.get(value);
        }

        if (type == TokenType.IDENTIFIER) {

            for (var pattern : patterns.entrySet()) {
                if (pattern.getKey().matcher(value).find()) {
                    type = pattern.getValue();
                    break;
                }
            }
        }

        return new Token(type, value, index, 0, 0);
    }


    private List<Token> getTokens(String input) {
        StringBuilder currentToken = new StringBuilder();
        int curlyBraceCount = 0;
        char stringDelimiter = 0; // Will hold the quote character when inside a string
        boolean commandMode = false;
        int counter =0;

        var specialSymbols = new ArrayList<Character>();

        specialSymbols.add('(');
        specialSymbols.add(')');
        specialSymbols.add(',');
        specialSymbols.add('[');
        specialSymbols.add(']');
        specialSymbols.add(':');
        specialSymbols.add('.');
        specialSymbols.add('=');

        specialSymbols.add('+');
        specialSymbols.add('-');
        specialSymbols.add('*');
        specialSymbols.add('^');
        specialSymbols.add(';');
        List<Token> result = new ArrayList<>();
        for (int i = 0; i < input.length(); i++) {
            char current = input.charAt(i);


            if (current == '&') {
                current = '§';
            }

            if (current == '\n') {
                if (commandMode) {
                    commandMode = false;
                }

                var res = currentToken.toString();
                if (StringUtils.isNotNullOrEmpty(res)) {
                    result.add(stringToToken(res,i));
                }
                currentToken.setLength(0);
                counter++;
                continue;
            }

            if (commandMode) {
                currentToken.append(current);
                continue;
            }

            if (curlyBraceCount == 0 && stringDelimiter == 0 && current == '/') {
                commandMode = true;
            }

            if (specialSymbols.contains(current)) {
                var value = currentToken.toString();
                if (!value.equals("")) {
                    result.add(stringToToken(value,i));
                }
                result.add(stringToToken(current + "",i));
                counter++;
                currentToken.setLength(0);
                continue;
            }

            if (stringDelimiter != 0) {
                currentToken.append(current);
                if (current == stringDelimiter) {
                    result.add(stringToToken(currentToken.toString(),i));
                    counter++;
                    currentToken.setLength(0);
                    stringDelimiter = 0;
                }
                continue;
            }

            if ((current == '"' || current == '\'') && curlyBraceCount == 0) {
                // Start of string
                stringDelimiter = current;
                currentToken.append(current);
                continue;
            }

            if (current == ' ' && curlyBraceCount == 0 && currentToken.length() == 0) {
                continue;
            }

            if (current == ' ' && curlyBraceCount == 0) {
                result.add(stringToToken(currentToken.toString(),i));
                counter++;
                currentToken.setLength(0);
                continue;
            }

            if (current == '{') {
                curlyBraceCount++;
            }

            if (current == '}') {
                curlyBraceCount--;
            }

            currentToken.append(current);


            if (curlyBraceCount == 0 && current == '}') {
                result.add(stringToToken(currentToken.toString(),i));
                counter++;
                currentToken.setLength(0);
            }
        }

        if (currentToken.length() > 0) {
            result.add(stringToToken(currentToken.toString(),currentToken.length()));
        }
        return result;
    }


}
