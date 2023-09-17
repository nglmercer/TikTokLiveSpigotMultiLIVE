package io.github.jwdeveloper.spigot.tiktok.profiles.code.blocks.literals;

import io.github.jwdeveloper.ff.core.common.java.StringUtils;
import io.github.jwdeveloper.spigot.tiktok.profiles.code.ExecutorContext;
import io.github.jwdeveloper.spigot.tiktok.profiles.common.exceptions.ProfileEngineException;

import java.util.ArrayList;
import java.util.List;

public class TextLineBlock extends LiteralBlock {
    private final String orginalString;
    private final String parsedLine;

    public TextLineBlock(String originalLine, String parsedLine) {
        super(originalLine, String.class);
        this.orginalString = originalLine;
        this.parsedLine = parsedLine;
    }

    @Override
    public List<String> execute(ExecutorContext context) {
        var index = 0;
        var blocksCode = new String[getCodeBlocks().size()];
        var repeat = 1;
        for (var block : getCodeBlocks()) {

            var output = block.execute(context);
            if(output instanceof RepeatBlock.RepeatInfo repeatInfo)
            {
                repeat = repeatInfo.getValue();
                output = StringUtils.EMPTY;
            }
            if (output instanceof Boolean value && !value) {
                throw new ProfileEngineException("Command canceled due to false value");
            }
            blocksCode[index] = output.toString();
            index++;
        }

        var formattedCommand =  String.format(parsedLine, blocksCode);;

        if(StringUtils.isNullOrEmpty(formattedCommand))
        {
            return List.of();
        }

        if(formattedCommand.charAt(0) == ' ')
        {
           formattedCommand = formattedCommand.substring(1);
        }
        if(formattedCommand.charAt(0) == '/')
        {
            formattedCommand = formattedCommand.substring(1);
        }

        var result = new ArrayList<String>();
        for(var i =0;i<repeat;i++)
        {
            result.add(formattedCommand);
        }
        return result;
    }
}
