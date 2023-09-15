package io.github.jwdeveloper.spigot.tiktok.profiles.code.blocks.literals;

import io.github.jwdeveloper.spigot.tiktok.profiles.code.ExecutorContext;
import io.github.jwdeveloper.spigot.tiktok.profiles.common.exceptions.ProfileEngineException;

public class TextLineBlock extends LiteralBlock
{
    private final String orginalString;
    private final String parsedLine;
    public TextLineBlock(String originalLine, String parsedLine)
    {
        super(originalLine,String.class);
        this.orginalString = originalLine;
        this.parsedLine = parsedLine;
    }

    @Override
    public String execute(ExecutorContext context)
    {
        var index = 0;
        var blocksCode = new String[getCodeBlocks().size()];
        for(var block: getCodeBlocks())
        {
            var output = block.execute(context);
            if(output instanceof Boolean value && !value)
            {
               throw new ProfileEngineException("Command canceled due to false value");
            }
            blocksCode[index] = output.toString();
            index ++;
        }

        return  String.format(parsedLine, blocksCode);
    }
}
