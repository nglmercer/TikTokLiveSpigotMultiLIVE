package io.github.jwdeveloper.spigot.tiktok.api.profiles.models;

import lombok.Data;

@Data
public class ProfileCommandParameter
{
    private ParameterType type;

    private int index;

    private String path;

}
