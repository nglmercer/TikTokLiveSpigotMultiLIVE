package io.github.jwdeveloper.spigot.tiktok.core.commands;

import io.github.jwdeveloper.ff.core.spigot.commands.api.enums.ArgumentDisplay;
import io.github.jwdeveloper.ff.extension.commands.api.annotations.Argument;
import io.github.jwdeveloper.ff.extension.commands.api.annotations.Command;
import io.github.jwdeveloper.spigot.tiktok.api.TikTokLiveSpigotApi;
import io.github.jwdeveloper.spigot.tiktok.core.common.TikTokLiveSpigotPermissions;
import io.github.jwdeveloper.spigot.tiktok.api.profiles.models.Profile;
import org.bukkit.entity.Player;

import java.util.List;

@Command(name = "tiktoklive", permissions = {TikTokLiveSpigotPermissions.TIKTOKLIVESPIGOT})
public class TikTokLiveSpigotCommands
{
    private final TikTokLiveSpigotApi tiktokApi;

    public TikTokLiveSpigotCommands(TikTokLiveSpigotApi profileService)
    {
        this.tiktokApi = profileService;
    }


    @Argument(name = "tiktok-user",
            displayMode = ArgumentDisplay.TAB_COMPLETE,
            onTabComplete = "onTikTokUserComplete")
    @Command(name = "connect", permissions = {TikTokLiveSpigotPermissions.CONNECT})
    public void connect(Player player, String name)
    {
        tiktokApi.connect(player, name);
    }

    @Command(name = "disconnect", permissions = {TikTokLiveSpigotPermissions.DISCONNECT})
    public void disconnect(Player player) {
        tiktokApi.disconnect(player);
    }



    @Argument(name = "profile-name",
            displayMode = ArgumentDisplay.TAB_COMPLETE,
            onTabComplete = "onProfileTabComplete")
    @Command(name = "profile", permissions = {TikTokLiveSpigotPermissions.SET_PROFILE})
    private void setProfile(Player player, String name)
    {
        tiktokApi.setProfile(player,name);
    }

    @Command(name = "profile-editor", permissions = {TikTokLiveSpigotPermissions.EDITOR})
    public void openEditor(Player player)
    {
        tiktokApi.openProfileEditor(player);
    }


    private List<String> onTikTokUserComplete()
    {
        return tiktokApi.getRecentHostsNames();
    }


    private List<String> onProfileTabComplete()
    {
        return tiktokApi.getAvailableProfiles().stream().map(Profile::getName).toList();
    }
}
