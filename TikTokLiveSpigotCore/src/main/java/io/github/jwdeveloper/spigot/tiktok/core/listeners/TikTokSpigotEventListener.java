package io.github.jwdeveloper.spigot.tiktok.core.listeners;

import io.github.jwdeveloper.ff.core.injector.api.annotations.Injection;
import io.github.jwdeveloper.ff.core.logger.plugin.FluentLogger;
import io.github.jwdeveloper.ff.core.spigot.tasks.api.FluentTaskFactory;
import io.github.jwdeveloper.ff.plugin.api.logger.PlayerLogger;
import io.github.jwdeveloper.spigot.tiktok.api.events.TikTokLiveSpigotEvent;
import io.github.jwdeveloper.spigot.tiktok.api.profiles.TikTokProfilesExecutor;
import io.github.jwdeveloper.spigot.tiktok.core.commands.MapTest;
import io.github.jwdeveloper.spigot.tiktok.core.profile.ProfileService;
import io.github.jwdeveloper.tiktok.annotations.TikTokEventHandler;
import io.github.jwdeveloper.tiktok.events.TikTokEvent;
import io.github.jwdeveloper.tiktok.events.messages.*;
import io.github.jwdeveloper.tiktok.exceptions.TikTokLiveOfflineHostException;
import io.github.jwdeveloper.tiktok.exceptions.TikTokLiveRequestException;
import io.github.jwdeveloper.tiktok.listener.TikTokEventListener;
import io.github.jwdeveloper.tiktok.live.LiveClient;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;

@Injection
public class TikTokSpigotEventListener implements TikTokEventListener {
    private final ProfileService profileService;
    private final TikTokProfilesExecutor profileExecutor;
    private final FluentTaskFactory fluentTaskFactory;
    private final Plugin plugin;
    private final PlayerLogger playerLogger;
    private final LoggerListener loggerListener;


    public TikTokSpigotEventListener(ProfileService profileService,
                                     TikTokProfilesExecutor profileProcessor,
                                     FluentTaskFactory fluentTaskFactory,
                                     Plugin plugin,
                                     PlayerLogger playerLogger,
                                     LoggerListener loggerListener) {
        this.profileService = profileService;
        this.profileExecutor = profileProcessor;
        this.fluentTaskFactory = fluentTaskFactory;
        this.plugin = plugin;
        this.playerLogger = playerLogger;
        this.loggerListener = loggerListener;
    }


    @TikTokEventHandler
    public void onEvent(LiveClient liveClient, TikTokEvent tikTokEvent)
    {
        try
        {
            var currentProfile = profileService.getCurrentProfile();
            var commands = profileExecutor.execute(tikTokEvent, currentProfile);
            if (commands.size() == 0) {
                return;
            }
            if (!plugin.isEnabled()) {
                return;
            }
            var commandsCopy = new ArrayList<>(commands);

            fluentTaskFactory.task(() ->
            {
                for(var i =0;i<commandsCopy.size();i++)
                {
                    var command = commandsCopy.get(i);
                    if(command.startsWith("say"))
                    {
                        var cmd = command.substring(4);
                        Bukkit.getOnlinePlayers().forEach(e -> e.sendMessage(cmd));
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(),"execute at @p run particle minecraft:heart ~1 ~1 ~");
                        continue;
                    }
                    loggerListener.addMessageToIgnore();
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command);
                }
                var event = new TikTokLiveSpigotEvent(tikTokEvent, liveClient, currentProfile);
                Bukkit.getServer().getPluginManager().callEvent(event);
            });
        }
        catch (Exception e)
        {
            FluentLogger.LOGGER.error("Error while executing profile",e);
            profileService.clearProfiles();
        }


    }

    //@TikTokEventHandler
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
    public void onError(LiveClient liveClient, TikTokErrorEvent tikTokEvent)
    {

        if(tikTokEvent.getException() instanceof TikTokLiveRequestException)
        {
            try
            {
              Thread.sleep(500);
                playerLogger
                        .warning(tikTokEvent.getException(), liveClient.getRoomInfo().getUserName(), "connection failed, reconnecting again")
                        .sendToAllPlayer();
              liveClient.connect();
            }
            catch (Exception e)
            {

            }
            return;
        }

        if (tikTokEvent.getException() instanceof TikTokLiveOfflineHostException)
        {
            playerLogger
                    .error(tikTokEvent.getException(), liveClient.getRoomInfo().getUserName(), "could be offline or name is misspelled")
                    .sendToAllPlayer();
            return;
        }

        playerLogger.error(tikTokEvent.getException(), "TikTok live error, more details in console").sendToAllPlayer();
        FluentLogger.LOGGER.error("TikTok live unexpected error", tikTokEvent.getException());
    }

}
