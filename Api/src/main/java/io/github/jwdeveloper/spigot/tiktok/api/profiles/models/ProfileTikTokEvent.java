package io.github.jwdeveloper.spigot.tiktok.api.profiles.models;

import io.github.jwdeveloper.spigot.tiktok.profiles.ast.ProgramSYML;
import lombok.Value;

@Value
public class ProfileTikTokEvent
{
     String sectionName;

     Class<?> className;

     ProgramSYML codeBlock;
}
