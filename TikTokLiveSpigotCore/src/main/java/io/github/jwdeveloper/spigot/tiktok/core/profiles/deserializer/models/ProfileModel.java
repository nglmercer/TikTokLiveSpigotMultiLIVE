package io.github.jwdeveloper.spigot.tiktok.core.profiles.deserializer.models;

import lombok.Data;

import java.util.List;

@Data
public class ProfileModel
{
    private String name;

    private List<ProfileElementModel> elements;
}
