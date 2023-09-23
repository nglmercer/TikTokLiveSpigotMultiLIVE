package io.github.jwdeveloper.spigot.tiktok.profiles.ast.expressions.literals;

import io.github.jwdeveloper.spigot.tiktok.profiles.ast.ProgramSYML;
import io.github.jwdeveloper.spigot.tiktok.profiles.common.EvaluatorContext;
import io.github.jwdeveloper.spigot.tiktok.profiles.tokenizer.TokenType;
import lombok.Getter;

import java.util.List;


public class StringLiteral extends Literal {

    @Getter
    List<ProgramSYML> blocks;

    @Getter
    String formattedValue;

    public StringLiteral(Object value, TokenType literalType, String formattedString, List<ProgramSYML> blocks) {
        super(value, literalType);
        this.blocks = blocks;
        this.formattedValue = formattedString;
    }

    @Override
    public Object execute(EvaluatorContext context) {
        var result = new Object[blocks.size()];
        var index =0 ;
        for(var block : blocks)
        {
            result[index] = block.execute(context);
            index ++;
        }

        var output  = String.format(formattedValue, result);

        return output;
    }
}
