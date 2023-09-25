package io.github.jwdeveloper.spigot.tiktok.core;

import io.github.jwdeveloper.ff.core.common.java.StringUtils;
import io.github.jwdeveloper.ff.core.logger.plugin.FluentLogger;
import io.github.jwdeveloper.ff.extension.files.api.fluent_files.FluentFile;
import io.github.jwdeveloper.ff.extension.gui.api.InventoryApi;
import io.github.jwdeveloper.ff.plugin.api.logger.PlayerLogger;
import io.github.jwdeveloper.spigot.tiktok.api.TikTokLiveSpigotApi;
import io.github.jwdeveloper.spigot.tiktok.api.profiles.TikTokProfilesExecutor;
import io.github.jwdeveloper.spigot.tiktok.api.profiles.models.Profile;
import io.github.jwdeveloper.spigot.tiktok.core.common.TikTokLiveSpigotConst;
import io.github.jwdeveloper.spigot.tiktok.core.common.TikTokLiveSpigotMeta;
import io.github.jwdeveloper.spigot.tiktok.core.gui.TikTokSpigotLiveAdminGUI;
import io.github.jwdeveloper.spigot.tiktok.core.profile.ProfileService;
import io.github.jwdeveloper.tiktok.live.ConnectionState;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;


public class TikTokLiveSpigotApiImpl implements TikTokLiveSpigotApi {
    private final ProfileService profileService;
    private final TikTokLiveSpigotClient tikTokSpigotClient;
    private final TikTokProfilesExecutor tikTokProfileExecutor;
    private final FluentFile<TikTokLiveSpigotMeta> metaFluentFile;
    private final PlayerLogger playerLogger;
    private final InventoryApi inventoryApi;


    public TikTokLiveSpigotApiImpl(ProfileService profileService,
                                   TikTokLiveSpigotClient tikTokSpigotClient,
                                   FluentFile<TikTokLiveSpigotMeta> metaFluentFile,
                                   PlayerLogger playerLogger,
                                   InventoryApi inventoryApi,
                                   TikTokProfilesExecutor tikTokProfileExecutor) {
        this.profileService = profileService;
        this.tikTokSpigotClient = tikTokSpigotClient;
        this.metaFluentFile = metaFluentFile;
        this.playerLogger = playerLogger;
        this.inventoryApi = inventoryApi;
        this.tikTokProfileExecutor = tikTokProfileExecutor;
    }


    @Override
    public void connect(Player player) {
        playerLogger
                .info("Connecting to live", ChatColor.DARK_AQUA, tikTokSpigotClient.getRecentHost())
                .sendToAllPlayer();
        tikTokSpigotClient.connect(tikTokSpigotClient.getRecentHost());
    }

    public void connect(Player player, String tiktokUserName)
    {
        if(tiktokUserName.startsWith("@"))
        {
            tiktokUserName = tiktokUserName.replace("@", StringUtils.EMPTY);
        }

        playerLogger
                .info("Connecting to live", ChatColor.DARK_AQUA, tiktokUserName)
                .sendToAllPlayer();
        tikTokSpigotClient.connect(tiktokUserName);
    }

    public void disconnect(Player player) {
        tikTokSpigotClient.disconnect();
    }

    public void setProfile(Player player, String profileName) {
        var result = profileService.setCurrentProfile(profileName);
        if (result.isSuccess()) {
            playerLogger.success(result.getMessage()).send(player);
        } else {
            playerLogger.warning(result.getMessage()).send(player);
        }
    }

    public void openProfileEditor(Player player) {
        var url = profileService.getWebEditorUrl(player);
        playerLogger.link("to open web editor", url).send(player);
    }

    public void openHelp(CommandSender player) {
        playerLogger.link("More information here", TikTokLiveSpigotConst.PROFILE_EDITOR_HELP).send(player);
    }

    @Override
    public void openConfigGui(Player player)
    {
       var instance =  inventoryApi.inventory().create(TikTokSpigotLiveAdminGUI.class);
       instance.open(player);
    }

    @Override
    public List<Profile> getAvailableProfiles() {
        return profileService.getProfiles();
    }

    public List<String> getRecentHostsNames() {
        if (metaFluentFile.getTarget().getHosts() == null) {
            return List.of();
        }
        return metaFluentFile.getTarget().getHosts();
    }

    @Override
    public String getRecentHost() {
        return tikTokSpigotClient.getRecentHost();
    }

    @Override
    public ConnectionState getClientState() {
        return tikTokSpigotClient.getConnectionState();
    }

    @Override
    public TikTokProfilesExecutor getProfileExecutor() {
        return tikTokProfileExecutor;
    }

    @Override
    public void clearHostsNames() {
        metaFluentFile.getTarget().getHosts().clear();
        metaFluentFile.save();
    }


}
