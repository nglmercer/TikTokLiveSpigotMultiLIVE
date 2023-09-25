package io.github.jwdeveloper.spigot.tiktok.profiles.tokenizer;

public enum TokenType
{
    BEGIN,
    END_OF_FILE,
    END_OF_LINE,
    BAD_TOKEN,
    IGNORED,
    SPACE,

    OPEN_BLOCK,
    CLOSE_BLOCK,

    OPEN_ARGUMENTS,
    CLOSE_ARGUMENTS,

    OPEN_ARRAY,
    CLOSE_ARRAY,

    COMMA,


    //Variable
    STRING,
    NUMBER,
    BOOL,
    CODE_BLOCK,

    MINECRAFT_COMMAND,
    IDENTIFIER,


    ASSIGNMENT,
    COMPLEX_ASSIGNMENT,
    BINARY_ADDATIVE_OPERATOR,
    BINARY_MULTIPLICATION_OPERATOR,
    EQUALITY_OPERATOR,
    LOGICAL_OPERATOR,

    KEYWORLD,
    OBJECT_TYPE,
    DOT
}
