package io.github.jwdeveloper.spigot.tiktok.core;

import io.github.jwdeveloper.ff.core.common.java.StringUtils;
import io.github.jwdeveloper.ff.core.logger.plugin.FluentLogger;
import io.github.jwdeveloper.ff.extension.files.api.fluent_files.FluentFile;
import io.github.jwdeveloper.ff.extension.gui.api.InventoryApi;
import io.github.jwdeveloper.ff.plugin.api.logger.PlayerLogger;
import io.github.jwdeveloper.spigot.tiktok.api.TikTokLiveSpigotApi;
import io.github.jwdeveloper.spigot.tiktok.api.profiles.TikTokProfileEditor;
import io.github.jwdeveloper.spigot.tiktok.profiles.common.Profile;
import io.github.jwdeveloper.spigot.tiktok.core.common.TikTokLiveSpigotMeta;
import io.github.jwdeveloper.spigot.tiktok.core.gui.TikTokSpigotLiveAdminGUI;
import io.github.jwdeveloper.spigot.tiktok.core.profile.ProfileService;
import io.github.jwdeveloper.tiktok.live.ConnectionState;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.List;

public class TikTokLiveSpigotApiImpl implements TikTokLiveSpigotApi {
    private final ProfileService profileService;
    private final TikTokLiveSpigotClient tikTokSpigotClient;
    private final TikTokProfileEditor tikTokProfileExecutor;
    private final FluentFile<TikTokLiveSpigotMeta> metaFluentFile;
    private final PlayerLogger playerLogger;
    private final InventoryApi inventoryApi;

    public TikTokLiveSpigotApiImpl(ProfileService profileService,
                                   TikTokLiveSpigotClient tikTokSpigotClient,
                                   FluentFile<TikTokLiveSpigotMeta> metaFluentFile,
                                   PlayerLogger playerLogger,
                                   InventoryApi inventoryApi,
                                   TikTokProfileEditor tikTokProfileExecutor) {
        this.profileService = profileService;
        this.tikTokSpigotClient = tikTokSpigotClient;
        this.metaFluentFile = metaFluentFile;
        this.playerLogger = playerLogger;
        this.inventoryApi = inventoryApi;
        this.tikTokProfileExecutor = tikTokProfileExecutor;
        this.profileService.onProfilesUpdated(this::updateProfilesInfo);
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
    public TikTokProfileEditor getProfileExecutor() {
        return tikTokProfileExecutor;
    }

    @Override
    public void clearHostsNames() {
        metaFluentFile.getTarget().getHosts().clear();
        metaFluentFile.save();
    }

    private void updateProfilesInfo(List<Profile> profiles)
    {
        playerLogger.success("Profiles reloaded").sendToAllPlayer();
        FluentLogger.LOGGER.success("==============================================");
        FluentLogger.LOGGER.success("Profiles updated: ");
        for (var profile : profiles) {
            FluentLogger.LOGGER.success("-", profile.getName());
            FluentLogger.LOGGER.success("    events:");
            for (var event : profile.getEventsCommands().entrySet()) {
                FluentLogger.LOGGER.success("      -", event.getKey().getSimpleName(), "-> commands:", event.getValue().size());
            }
        }
        FluentLogger.LOGGER.success("==============================================");
    }
}
