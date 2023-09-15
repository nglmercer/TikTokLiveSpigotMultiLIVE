package io.github.jwdeveloper.spigot.tiktok.profiles.interpreter;

import io.github.jwdeveloper.ff.core.common.java.StringUtils;
import io.github.jwdeveloper.spigot.tiktok.profiles.common.exceptions.ProfileEngineException;
import lombok.Data;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Data
public class Tokenizer implements Iterator<String> {
    //String input = "event name 12 \"my ${siema} name if jezz\", 'my name if jeff' ${asda${ }sdssd}";

    private List<String> tokens;
    private int currentIndex = -1;

    private String current = StringUtils.EMPTY;


    public Tokenizer(String input) {
        tokens = getTokens(input);
    }

    @Override
    public boolean hasNext() {
        return currentIndex < tokens.size()-1;
    }

    public boolean isLastToken() {
        return !hasNext();
    }

    public boolean isCurrent(String value)
    {
        return current().equals(value);
    }

    public String current() {
        return current;
    }

    public String lookup(int offset) {
        if (currentIndex + offset >= tokens.size()) {
            return tokens.get(tokens.size() - 1);
        }
        return tokens.get(currentIndex + offset);
    }


    public boolean isLookup(String value) {
        return lookup(1).equals(value);
    }


    public String lookup() {
        return lookup(1);
    }

    @Override
    public String next() {
        current = lookup(1);
        currentIndex++;
        return current();
    }

    public String nextOrThrow(String required) {
        var nextValue = next();
        if (!nextValue.equals(required)) {
            throw new ProfileEngineException("Next Token should be " + required + " but was " + nextValue);
        }
        return nextValue;
    }

    public String currentOrThrow(String required)
    {
        var nextValue = current();
        if (!nextValue.equals(required)) {
            throw new ProfileEngineException("Next Token should be " + required + " but was " + nextValue);
        }
        return nextValue;
    }

    private List<String> getTokens(String input) {
        int counter = 1;
        StringBuilder currentToken = new StringBuilder();
        int curlyBraceCount = 0;
        char stringDelimiter = 0; // Will hold the quote character when inside a string

        List<String> result = new ArrayList<>();
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);

            if(c == '\n')
            {
                continue;
            }


            if (c == '(' ) {
                var value = currentToken.toString();
                if(!value.equals(""))
                {
                    result.add(currentToken.toString());
                }
                result.add("(");
                counter++;
                currentToken.setLength(0);
                continue;
            }

            if (c == ',' ) {
                //System.out.println(counter + " " + currentToken);

                var value = currentToken.toString();
                if(!value.equals(""))
                {
                    result.add(currentToken.toString());
                }
                result.add(",");
                counter++;
                currentToken.setLength(0);
                continue;
            }

            if (c == ')' ) {
                var value = currentToken.toString();
                if(!value.equals(""))
                {
                    result.add(currentToken.toString());
                }
                result.add(")");
                counter++;
                currentToken.setLength(0);
                continue;
            }

            if (stringDelimiter != 0) {
                // We're inside a string
                currentToken.append(c);
                if (c == stringDelimiter) {
                    // End of string
                  //  System.out.println(counter + " " + currentToken);
                    result.add(currentToken.toString());
                    counter++;
                    currentToken.setLength(0);
                    stringDelimiter = 0;
                }
                continue;
            }

            if (c == '"' || c == '\'') {
                // Start of string
                stringDelimiter = c;
                currentToken.append(c);
                continue;
            }

            if (c == ' ' && curlyBraceCount == 0 && currentToken.length() == 0) {
                continue;
            }

            if (c == ' ' && curlyBraceCount == 0) {
                //System.out.println(counter + " " + currentToken);
                result.add(currentToken.toString());
                counter++;
                currentToken.setLength(0);
                continue;
            }


            if (c == '{') {
                curlyBraceCount++;
            }

            if (c == '}') {
                curlyBraceCount--;
            }

            currentToken.append(c);



            if (curlyBraceCount == 0 && c == '}') {
                //System.out.println(counter + " " + currentToken);
                result.add(currentToken.toString());
                counter++;
                currentToken.setLength(0);
            }
        }

        if (currentToken.length() > 0) {
            // System.out.println(counter + " " + currentToken);
            result.add(currentToken.toString());
        }
        return result;
    }
}
