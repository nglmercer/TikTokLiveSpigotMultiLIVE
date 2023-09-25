package io.github.jwdeveloper.spigot.tiktok.core.commands;

import io.github.jwdeveloper.ff.core.spigot.commands.api.enums.ArgumentDisplay;
import io.github.jwdeveloper.ff.extension.commands.api.annotations.Argument;
import io.github.jwdeveloper.ff.extension.commands.api.annotations.Command;
import io.github.jwdeveloper.spigot.tiktok.api.TikTokLiveSpigotApi;
import io.github.jwdeveloper.spigot.tiktok.api.profiles.models.Profile;
import io.github.jwdeveloper.spigot.tiktok.core.common.TikTokLiveSpigotPermissions;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

@Command(name = "tiktoklive")
public class TikTokLiveSpigotCommands {
    private final TikTokLiveSpigotApi tiktokApi;

    public TikTokLiveSpigotCommands(TikTokLiveSpigotApi profileService) {
        this.tiktokApi = profileService;
    }


    @Command
    public void onCommand(CommandSender commandSender)
    {
        tiktokApi.openHelp(commandSender);
    }

    @Argument(name = "tiktok-user",
            displayMode = ArgumentDisplay.TAB_COMPLETE,
            onTabComplete = "onTikTokUserComplete")
    @Command(name = "connect", permissions = {TikTokLiveSpigotPermissions.LIVE.CONNECT})
    public void connect(Player player, String name) {
        tiktokApi.connect(player, name);
    }


    @Command(name = "disconnect", permissions = {TikTokLiveSpigotPermissions.LIVE.DISCONNECT})
    public void disconnect(Player player) {
        tiktokApi.disconnect(player);
    }


    @Command(name = "admin", permissions = {TikTokLiveSpigotPermissions.GUI.ADMIN})
    public void adminPanel(Player player) {
        tiktokApi.openConfigGui(player);
    }


    @Argument(name = "profile-name",
            displayMode = ArgumentDisplay.TAB_COMPLETE,
            onTabComplete = "onProfileTabComplete")
    @Command(name = "profile", permissions = {TikTokLiveSpigotPermissions.PROFILES.CHANGE})
    private void setProfile(Player player, String name) {
        tiktokApi.setProfile(player, name);
    }


   // @Command(name = "profile-editor", permissions = {TikTokLiveSpigotPermissions.PROFILES.PROFILE_EDITOR})
    public void openEditor(Player player) {
        tiktokApi.openProfileEditor(player);
    }


    private List<String> onTikTokUserComplete() {
        return tiktokApi.getRecentHostsNames();
    }


    private List<String> onProfileTabComplete() {
        return tiktokApi.getAvailableProfiles().stream().map(Profile::getName).toList();
    }
}
