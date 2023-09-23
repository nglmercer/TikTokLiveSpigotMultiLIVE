package io.github.jwdeveloper.spigot.tiktok.profiles.tokenizer.models;

import io.github.jwdeveloper.spigot.tiktok.profiles.common.definitions.VariableDefinition;
import io.github.jwdeveloper.spigot.tiktok.profiles.common.definitions.MethodDefinition;
import lombok.Value;

import java.util.List;

@Value
public class ProfilesDeserialization
{
     List<ProfileModel> profileModels;

     CodeBlockElement initializationBlock;

     List<MethodDefinition> methodDefinitions;

     List<VariableDefinition> constances;
}
