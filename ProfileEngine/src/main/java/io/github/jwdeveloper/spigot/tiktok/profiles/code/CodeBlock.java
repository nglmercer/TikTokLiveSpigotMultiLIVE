package io.github.jwdeveloper.spigot.tiktok.profiles.code;

import io.github.jwdeveloper.ff.core.common.java.StringUtils;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CodeBlock implements CodeExecutor
{
    private List<CodeBlock> codeBlocks = new ArrayList<>();

    private Integer blockIndex = -1;

    @Override
    public Object execute(ExecutorContext context)
    {
        Object returnValue = StringUtils.EMPTY;
        for(var block : codeBlocks)
        {
           returnValue = block.execute(context);
        }
        return returnValue;
    }

    public void addChild(CodeBlock codeBlock)
    {
        codeBlocks.add(codeBlock);
    }
}
