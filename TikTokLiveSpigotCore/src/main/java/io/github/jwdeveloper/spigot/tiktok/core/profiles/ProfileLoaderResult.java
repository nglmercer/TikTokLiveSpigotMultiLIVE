package io.github.jwdeveloper.spigot.tiktok.core.profiles;

import io.github.jwdeveloper.spigot.tiktok.api.profiles.models.Profile;
import io.github.jwdeveloper.spigot.tiktok.profiles.ast.ProgramSYML;
import lombok.Value;

import java.util.List;

@Value
public class ProfileLoaderResult {

    List<Profile> profiles;
}
