package io.github.jwdeveloper.spigot.tiktok.api.profiles;
import io.github.jwdeveloper.spigot.tiktok.api.profiles.models.Profile;
import io.github.jwdeveloper.spigot.tiktok.profiles.ast.ProgramSYML;
import io.github.jwdeveloper.tiktok.events.TikTokEvent;

import java.util.List;

public interface TikTokProfilesExecutor
{
     List<String> execute(TikTokEvent tikTokEvent, Profile profile);
     List<String> execute(ProgramSYML programSYML);
}
