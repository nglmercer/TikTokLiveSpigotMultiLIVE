package io.github.jwdeveloper.spigot.tiktok.profiles.tokenizer.models;

import lombok.Data;

import java.util.List;

@Data
public class ProfileModel
{
    private String profileName;

    private List<CodeBlockElement> elements;
}
