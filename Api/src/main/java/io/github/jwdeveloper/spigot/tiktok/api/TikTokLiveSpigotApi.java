package io.github.jwdeveloper.spigot.tiktok.api;


import io.github.jwdeveloper.spigot.tiktok.api.profiles.TikTokProfileEditor;
import io.github.jwdeveloper.spigot.tiktok.profiles.common.Profile;
import io.github.jwdeveloper.tiktok.live.ConnectionState;
import org.bukkit.entity.Player;

import java.util.List;

public interface TikTokLiveSpigotApi
{
     void connect(Player player);

     void connect(Player player, String tiktokUserName);

     void disconnect(Player player);

     void setProfile(Player player, String profileName);

     void openProfileEditor(Player player);

     void openConfigGui(Player player);
     List<Profile> getAvailableProfiles();
     List<String> getRecentHostsNames();
     String getRecentHost();
     ConnectionState getClientState();

     TikTokProfileEditor getProfileExecutor();
     void clearHostsNames();
}
