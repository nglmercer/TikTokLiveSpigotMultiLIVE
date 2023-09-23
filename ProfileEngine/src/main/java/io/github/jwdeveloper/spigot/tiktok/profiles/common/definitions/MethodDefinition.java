package io.github.jwdeveloper.spigot.tiktok.profiles.common.definitions;

import lombok.Value;

import java.util.List;
import java.util.function.Function;

@Value
public class MethodDefinition
{
     String name;

     Function<List<Object>,Object> onInvoke;

     boolean isDefault;



}
