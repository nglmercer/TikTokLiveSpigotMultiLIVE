package io.github.jwdeveloper.spigot.tiktok.api.profiles;
import io.github.jwdeveloper.spigot.tiktok.profiles.common.Profile;
import io.github.jwdeveloper.tiktok.events.TikTokEvent;

import java.util.List;

public interface TikTokProfileExecutor
{
     void addConstance();

     void addMethod();
     List<String> execute(TikTokEvent tikTokEvent, Profile profile);
}
