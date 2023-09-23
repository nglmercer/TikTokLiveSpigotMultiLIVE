package io.github.jwdeveloper.spigot.tiktok.profiles.tokenizer;

public class TokenizerFactory {
    public static Tokenizer create(String input) {
        var builder = new TokenizerBuilder();


        builder.addToken(TokenType.ASSIGMENT, ":","=");
        builder.addToken(TokenType.OPEN_BLOCK, "{");
        builder.addToken(TokenType.CLOSE_BLOCK, "}");

        builder.addToken(TokenType.OPEN_ARGUMETNS, "(");
        builder.addToken(TokenType.CLOSE_ARGUMENTS, ")");

        builder.addToken(TokenType.OPEN_ARRAY, "[");
        builder.addToken(TokenType.CLOSE_ARRAY, "]");

        builder.addToken(TokenType.COMMA, ",");
        builder.addToken(TokenType.DOT, ".");
        builder.addToken(TokenType.END_OF_LINE, ";");

        builder.addToken(TokenType.LOGICAL_OPERATOR, "&&", "and", "||", "or", "!");
        builder.addToken(TokenType.EQUALITY_OPREATOR,  "is", "not",  ">", ">=", "<", "<=", "contains");
        builder.addToken(TokenType.BINARY_ADDATIVE_OPERATOR, "+", "-");
        builder.addToken(TokenType.BINARY_MULTIPLICATION_OPERATOR, "*", "/", "^", "%", "mod");

        builder.addTokenRegex(TokenType.MINECRAFT_COMMENT, "^\\/");
        builder.addTokenRegex(TokenType.STRING, "([\"'])(.*?)\\1");
        builder.addTokenRegex(TokenType.NUMBER, "^\\d+$");
        builder.addTokenRegex(TokenType.BOOL, "\\b(true|false)\\b");
        builder.addTokenRegex(TokenType.CODE_BLOCK, "\\$\\{([^}]+)\\}");


        builder.addToken(TokenType.KEYWORLD, "if", "switch", "repeat","else","then","default","return","delete");

        return builder.build(input);
    }
}
