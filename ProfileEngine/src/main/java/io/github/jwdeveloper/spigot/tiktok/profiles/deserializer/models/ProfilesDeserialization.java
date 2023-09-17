package io.github.jwdeveloper.spigot.tiktok.profiles.deserializer.models;

import io.github.jwdeveloper.spigot.tiktok.profiles.common.definitions.ConstDefinition;
import io.github.jwdeveloper.spigot.tiktok.profiles.common.definitions.MethodDefinition;
import lombok.Value;

import java.util.List;

@Value
public class ProfilesDeserialization
{
     List<ProfileModel> profileModels;

     List<MethodDefinition> methodDefinitions;

     List<ConstDefinition> constances;
}
