package io.github.jwdeveloper.spigot.tiktok.profiles.common.definitions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Setter;

@Data
@AllArgsConstructor
public class VariableDefinition
{
    String name;

    @Setter
    Object value;
}
