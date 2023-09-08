package io.github.jwdeveloper.spigot.tiktok.core.commands;

import io.github.jwdeveloper.ff.core.spigot.commands.api.enums.ArgumentDisplay;
import io.github.jwdeveloper.ff.extension.commands.api.annotations.Argument;
import io.github.jwdeveloper.ff.extension.commands.api.annotations.Command;
import io.github.jwdeveloper.spigot.tiktok.core.common.TikTokLiveSpigotPermissions;
import io.github.jwdeveloper.spigot.tiktok.core.TikTokLiveSpigotClient;
import io.github.jwdeveloper.spigot.tiktok.core.services.ProfileService;
import io.github.jwdeveloper.spigot.tiktok.api.profiles.models.Profile;
import org.bukkit.entity.Player;

import java.util.List;

@Command(name = "tiktoklive")
public class TikTokLiveSpigotCommands {
    private final ProfileService profileService;
    private final TikTokLiveSpigotClient tikTokSpigotClient;

    public TikTokLiveSpigotCommands(ProfileService profileService, TikTokLiveSpigotClient client) {
        this.profileService = profileService;
        this.tikTokSpigotClient = client;
    }

    @Command(name = "connect", permissions = {TikTokLiveSpigotPermissions.CONNECT})
    @Argument(name = "tiktok-user",
            displayMode = ArgumentDisplay.TAB_COMPLETE,
            onTabComplete = "onTikTokUserComplete")
    public void connect(Player player, String name)
    {
        tikTokSpigotClient.connect(name);
    }

    public List<String> onTikTokUserComplete()
    {
        return profileService.getProfiles().stream().map(Profile::getName).toList();
    }


    @Command(name = "profile", permissions = {TikTokLiveSpigotPermissions.SET_PROFILE})
    @Argument(name = "profile-name",
            displayMode = ArgumentDisplay.TAB_COMPLETE,
            onTabComplete = "onProfileTabComplete")
    private void onProfile(Player player, String name)
    {
        var result = profileService.setCurrentProfile(name);
        if (result.isSuccess()) {
            player.sendMessage(result.getMessage());
        } else {
            player.sendMessage(result.getMessage());
        }
    }

    public List<String> onProfileTabComplete()
    {
        return profileService.getProfiles().stream().map(Profile::getName).toList();
    }




    @Command(name = "profile-editor")
    public void editor() {

    }

    @Command(name = "disconnect", permissions = {TikTokLiveSpigotPermissions.DISCONNECT})
    public void disconnect() {
        tikTokSpigotClient.disconnect();
    }

}
