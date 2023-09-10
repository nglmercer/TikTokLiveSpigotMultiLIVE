package io.github.jwdeveloper.spigot.tiktok.api.profiles.models;

import lombok.Data;

import java.util.List;

@Data
public class ProfileEventCommand
{
    private Class<?> eventClass;

    private List<CodeBlock> codeBlocks;

    private String commandString;
}
