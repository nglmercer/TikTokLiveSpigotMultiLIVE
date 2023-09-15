package io.github.jwdeveloper.spigot.tiktok.core.listeners;

import io.github.jwdeveloper.ff.core.injector.api.annotations.Injection;
import io.github.jwdeveloper.ff.core.logger.plugin.FluentLogger;
import io.github.jwdeveloper.ff.core.spigot.tasks.api.FluentTaskFactory;
import io.github.jwdeveloper.ff.plugin.api.logger.PlayerLogger;
import io.github.jwdeveloper.spigot.tiktok.api.events.TikTokLiveSpigotEvent;
import io.github.jwdeveloper.spigot.tiktok.core.commands.MapTest;
import io.github.jwdeveloper.spigot.tiktok.core.profile.TikTokProfileExecutorImpl;
import io.github.jwdeveloper.spigot.tiktok.core.profile.ProfileService;
import io.github.jwdeveloper.tiktok.annotations.TikTokEventHandler;
import io.github.jwdeveloper.tiktok.events.TikTokEvent;
import io.github.jwdeveloper.tiktok.events.messages.*;
import io.github.jwdeveloper.tiktok.exceptions.TikTokLiveOfflineHostException;
import io.github.jwdeveloper.tiktok.listener.TikTokEventListener;
import io.github.jwdeveloper.tiktok.live.LiveClient;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.Plugin;

@Injection
public class TikTokSpigotEventListener implements TikTokEventListener {
    private final ProfileService profileService;
    private final TikTokProfileExecutorImpl profileExecutor;
    private final FluentTaskFactory fluentTaskFactory;
    private final Plugin plugin;
    private final PlayerLogger playerLogger;


    public TikTokSpigotEventListener(ProfileService profileService,
                                     TikTokProfileExecutorImpl profileProcessor,
                                     FluentTaskFactory fluentTaskFactory,
                                     Plugin plugin,
                                     PlayerLogger playerLogger) {
        this.profileService = profileService;
        this.profileExecutor = profileProcessor;
        this.fluentTaskFactory = fluentTaskFactory;
        this.plugin = plugin;
        this.playerLogger = playerLogger;
    }


    @TikTokEventHandler
    public void onEvent(LiveClient liveClient, TikTokEvent tikTokEvent) {
        var currentProfile = profileService.getCurrentProfile();
        var commands = profileExecutor.execute(tikTokEvent, currentProfile);
        if (commands.size() == 0) {
            return;
        }
        if (!plugin.isEnabled()) {
            return;
        }
        fluentTaskFactory.task(() ->
        {
            for (var command : commands) {
                FluentLogger.LOGGER.info(command);
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command);
            }
            var event = new TikTokLiveSpigotEvent(tikTokEvent, liveClient, currentProfile);
            Bukkit.getServer().getPluginManager().callEvent(event);
        });
    }

    @TikTokEventHandler
    public void onGift(LiveClient liveClient, TikTokCommentEvent tikTokEvent) {

        var imageUrl = tikTokEvent.getUser().getProfilePicture().getUrls().get(0);
        FluentLogger.LOGGER.info("Downloading URL: ",imageUrl);
        fluentTaskFactory.taskAsync(() ->
        {
            var image = MapTest.downloadImage(imageUrl);
            if(image == null)
            {
                FluentLogger.LOGGER.error("Image is null: "+imageUrl);
               return;
            }
            fluentTaskFactory.task(() ->
            {
                var player = Bukkit.getOnlinePlayers().stream().toList().get(0);
                MapTest.giveMapToPlayer(player, image);
            });
        });
    }


    @TikTokEventHandler
    public void onConnect(LiveClient liveClient, TikTokConnectedEvent tikTokEvent) {
        playerLogger
                .success("Connected to live", ChatColor.DARK_GREEN, liveClient.getRoomInfo().getUserName())
                .sendToAllPlayer();
    }

    @TikTokEventHandler
    public void onDisconnect(LiveClient liveClient, TikTokDisconnectedEvent tikTokEvent) {
        playerLogger
                .info("Disconnected from live", ChatColor.DARK_AQUA, liveClient.getRoomInfo().getUserName())
                .sendToAllPlayer();
    }


    @TikTokEventHandler
    public void onError(LiveClient liveClient, TikTokErrorEvent tikTokEvent) {
        if (tikTokEvent.getException() instanceof TikTokLiveOfflineHostException) {
            playerLogger
                    .error(tikTokEvent.getException(), liveClient.getRoomInfo().getUserName(), "could be offline or name is misspelled")
                    .sendToAllPlayer();
            return;
        }

        playerLogger.error(tikTokEvent.getException(), "TikTok live error, more details in console").sendToAllPlayer();
        FluentLogger.LOGGER.error("TikTok live unexpected error", tikTokEvent.getException());
    }

}
