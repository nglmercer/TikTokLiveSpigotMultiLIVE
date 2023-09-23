package io.github.jwdeveloper.spigot.tiktok.profiles.tokenizer;

import io.github.jwdeveloper.ff.core.common.java.StringUtils;
import io.github.jwdeveloper.spigot.tiktok.profiles.common.exceptions.SymlEngineException;
import lombok.Data;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Data
public class TokenIterator implements Iterator<String> {
    //String input = "event name 12 \"my ${siema} name if jezz\", 'my name if jeff' ${asda${ }sdssd}";

    private List<String> tokens;
    private int currentIndex = -1;

    private String current = StringUtils.EMPTY;


    public TokenIterator(String input) {
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
            throw new SymlEngineException("Next Token should be " + required + " but was " + nextValue);
        }
        return nextValue;
    }

    public String currentOrThrow(String required)
    {
        var nextValue = current();
        if (!nextValue.equals(required)) {
            throw new SymlEngineException("Next Token should be " + required + " but was " + nextValue);
        }
        return nextValue;
    }

    private List<String> getTokens(String input) {
        int counter = 1;
        StringBuilder currentToken = new StringBuilder();
        int curlyBraceCount = 0;
        char stringDelimiter = 0; // Will hold the quote character when inside a string

        boolean commandMode = false;


        var specialSymbols = new ArrayList<Character>();

        specialSymbols.add('(');
        specialSymbols.add(')');
        specialSymbols.add(',');
        specialSymbols.add('+');
        specialSymbols.add('[');
        specialSymbols.add(']');
        List<String> result = new ArrayList<>();
        for (int i = 0; i < input.length(); i++) {
            char current = input.charAt(i);

            if(current == '\n')
            {
                if(commandMode)
                {
                    result.add(currentToken.toString());
                    counter++;
                    currentToken.setLength(0);
                    commandMode = false;
                }
                continue;
            }

            if(commandMode)
            {
                currentToken.append(current);
                continue;
            }

            if(curlyBraceCount == 0 && stringDelimiter == 0 && current == '/')
            {
                commandMode = true;
            }

            if(specialSymbols.contains(current))
            {
                var value = currentToken.toString();
                if(!value.equals(""))
                {
                    result.add(currentToken.toString());
                }
                result.add(current+"");
                counter++;
                currentToken.setLength(0);
                continue;
            }

           /*if (current == '(' ) {
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


            if (current == '+' ) {
                var value = currentToken.toString();
                if(!value.equals(""))
                {
                    result.add(currentToken.toString());
                }
                result.add("+");
                counter++;
                currentToken.setLength(0);
                continue;
            }

            if (current == ',' ) {
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

            if (current == ')' ) {
                var value = currentToken.toString();
                if(!value.equals(""))
                {
                    result.add(currentToken.toString());
                }
                result.add(")");
                counter++;
                currentToken.setLength(0);
                continue;
            }*/

            if (stringDelimiter != 0) {
                // We're inside a string
                currentToken.append(current);
                if (current == stringDelimiter) {
                    // End of string
                  //  System.out.println(counter + " " + currentToken);
                    result.add(currentToken.toString());
                    counter++;
                    currentToken.setLength(0);
                    stringDelimiter = 0;
                }
                continue;
            }

            if (current == '"' || current == '\'') {
                // Start of string
                stringDelimiter = current;
                currentToken.append(current);
                continue;
            }

            if (current == ' ' && curlyBraceCount == 0 && currentToken.length() == 0) {
                continue;
            }

            if (current == ' ' && curlyBraceCount == 0) {
                //System.out.println(counter + " " + currentToken);
                result.add(currentToken.toString());
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
