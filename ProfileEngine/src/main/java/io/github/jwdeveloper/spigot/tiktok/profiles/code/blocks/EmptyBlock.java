package io.github.jwdeveloper.spigot.tiktok.profiles.code.blocks;

import io.github.jwdeveloper.ff.core.common.java.StringUtils;
import io.github.jwdeveloper.spigot.tiktok.profiles.code.CodeBlock;
import io.github.jwdeveloper.spigot.tiktok.profiles.code.ExecutorContext;

public class EmptyBlock extends CodeBlock
{
    @Override
    public Object execute(ExecutorContext context)
    {
        return StringUtils.EMPTY;
    }
}
