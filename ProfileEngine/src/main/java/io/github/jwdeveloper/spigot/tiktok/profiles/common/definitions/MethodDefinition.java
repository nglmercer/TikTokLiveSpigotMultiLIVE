package io.github.jwdeveloper.spigot.tiktok.profiles.common.definitions;

import lombok.Data;
import lombok.Value;

import java.util.List;
import java.util.function.Function;

@Value
public class MethodDefinition
{
    private String name;

    private Function<List<Object>,Object> onInvoke;
}
