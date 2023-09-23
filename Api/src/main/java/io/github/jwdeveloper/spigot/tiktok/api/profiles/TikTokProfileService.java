package io.github.jwdeveloper.spigot.tiktok.api.profiles;
import io.github.jwdeveloper.spigot.tiktok.api.profiles.models.Profile;
import org.bukkit.entity.Player;

import java.util.List;

public interface TikTokProfileService
{
     Profile getCurrentProfile();

     List<Profile> getProfiles();

     String getWebEditorUrl(Player player);
}
