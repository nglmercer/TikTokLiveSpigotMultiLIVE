package io.github.jwdeveloper.spigot.tiktok.profiles.deserializer.models;

import lombok.Data;

import java.util.List;

@Data
public class ProfileElementModel
{
    private String eventName;

    private List<String> commands;
}
