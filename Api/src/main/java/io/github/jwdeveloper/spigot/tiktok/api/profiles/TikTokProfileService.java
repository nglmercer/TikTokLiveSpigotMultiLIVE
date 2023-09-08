package io.github.jwdeveloper.spigot.tiktok.api.profiles;
import io.github.jwdeveloper.spigot.tiktok.api.profiles.models.Profile;

import java.util.List;

public interface TikTokProfileService
{
     Profile getCurrentProfile();

     List<Profile> getProfiles();
}
