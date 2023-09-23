package io.github.jwdeveloper.spigot.tiktok.profiles.common;

import lombok.Value;

import java.util.HashMap;
import java.util.Map;

public class StringCodeBlockParser {

    public CodeBlockParserOutput findCodeBlock(String input) {
        var occurences = findCodeBlocks(input);
        var template = replaceWithPlaceHolder(input);
        return new CodeBlockParserOutput(occurences, input, template);
    }


    private  Map<Integer, String> findCodeBlocks(String input) {
        Map<Integer, String> occurrences = new HashMap<>();
        StringBuilder currentToken = new StringBuilder();
        int curlyBraceCount = 0;

        var counter = 0;
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);

            if (c == '$' && i + 1 < input.length() && input.charAt(i + 1) == '{') {
                curlyBraceCount++;
                i++; // Skip the '{' as we already counted it
                 currentToken.append("${");
            } else if (c == '}') {
                curlyBraceCount--;
                currentToken.append("}");
            } else if (curlyBraceCount > 0) {
                currentToken.append(c);
            }

            if (curlyBraceCount == 0 && currentToken.length() > 0)
            {
                var current = currentToken.toString();

                //removig ${ from begining and } from end
                current = current.substring(2, current.length() - 1);

                occurrences.put(counter, current);
                counter++;
                currentToken.setLength(0);
            }
        }
        return occurrences;
    }

    private String replaceWithPlaceHolder(String input) {
        var output = new StringBuilder();
        var currentToken = new StringBuilder();
        int curlyBraceCount = 0;

        var index = 0;
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);

            if (c == '$' && i + 1 < input.length() && input.charAt(i + 1) == '{') {
                curlyBraceCount++;
                i++; // Skip the '{' as we already counted it
                currentToken.append("${");
            } else if (c == '}') {
                curlyBraceCount--;
                currentToken.append("}");
            } else if (curlyBraceCount > 0) {
                currentToken.append(c);
            } else {
                output.append(c);
            }

            if (curlyBraceCount == 0 && currentToken.length() > 0) {
                output.append("%s");
                index++;
                currentToken.setLength(0);
            }
        }

        if(output.charAt(0) == '\'' ||output.charAt(0) == '\"')
        {
            output.deleteCharAt(0);
            output.deleteCharAt(output.length()-1);
        }

        return output.toString();
    }

    @Value
    public static class CodeBlockParserOutput {
        Map<Integer, String> occurrences;
        String orginalContent;
        String markContent;
    }
}
