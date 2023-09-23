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

    OPEN_ARGUMETNS,
    CLOSE_ARGUMENTS,

    OPEN_ARRAY,
    CLOSE_ARRAY,

    COMMA,


    //Variable
    STRING,
    NUMBER,
    BOOL,
    CODE_BLOCK,

    MINECRAFT_COMMENT,
    IDENTIFIER,


    ASSIGMENT,
    COMPLEX_ASSIGMENT,
    BINARY_ADDATIVE_OPERATOR,
    BINARY_MULTIPLICATION_OPERATOR,
    EQUALITY_OPREATOR,
    LOGICAL_OPERATOR,

    KEYWORLD,
    OBJECT_TYPE,
    DOT
}
