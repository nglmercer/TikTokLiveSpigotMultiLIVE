package io.github.jwdeveloper.spigot.tiktok.core.profile;

import io.github.jwdeveloper.spigot.tiktok.api.profiles.models.Profile;
import io.github.jwdeveloper.spigot.tiktok.profiles.ast.ProgramSYML;
import lombok.Value;

import java.util.List;

@Value
public class ProfileLoaderResult {

    List<Profile> profiles;
}
