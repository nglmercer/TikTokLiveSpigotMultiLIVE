package io.github.jwdeveloper.spigot.tiktok.profiles.common;

import io.github.jwdeveloper.spigot.tiktok.profiles.code.blocks.literals.TextLineBlock;
import lombok.Value;

@Value
public class ProfileEventCommand
{
    private Class<?> eventClass;

    private TextLineBlock textLineBlock;
}
