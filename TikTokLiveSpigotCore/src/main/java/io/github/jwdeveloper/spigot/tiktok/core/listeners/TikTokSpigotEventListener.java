package io.github.jwdeveloper.spigot.tiktok.core.listeners;

import io.github.jwdeveloper.ff.core.common.logger.FluentLogger;
import io.github.jwdeveloper.ff.core.injector.api.annotations.Injection;
import io.github.jwdeveloper.ff.core.spigot.tasks.api.FluentTaskFactory;
import io.github.jwdeveloper.spigot.tiktok.core.profiles.processor.ProfileProcessor;
import io.github.jwdeveloper.spigot.tiktok.core.services.ProfileService;
import io.github.jwdeveloper.spigot.tiktok.api.events.TikTokLiveSpigotEvent;
import io.github.jwdeveloper.tiktok.annotations.TikTokEventHandler;
import io.github.jwdeveloper.tiktok.events.TikTokEvent;
import io.github.jwdeveloper.tiktok.events.messages.TikTokConnectedEvent;
import io.github.jwdeveloper.tiktok.events.messages.TikTokDisconnectedEvent;
import io.github.jwdeveloper.tiktok.events.messages.TikTokErrorEvent;
import io.github.jwdeveloper.tiktok.listener.TikTokEventListener;
import io.github.jwdeveloper.tiktok.live.LiveClient;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

@Injection
public class TikTokSpigotEventListener implements TikTokEventListener {
    private final ProfileService profileService;
    private final ProfileProcessor profileProcessor;
    private final FluentTaskFactory fluentTaskFactory;
    private final Plugin plugin;


    public TikTokSpigotEventListener(ProfileService profileService,
                                     ProfileProcessor profileProcessor,
                                     FluentTaskFactory fluentTaskFactory,
                                     Plugin plugin) {
        this.profileService = profileService;
        this.profileProcessor = profileProcessor;
        this.fluentTaskFactory = fluentTaskFactory;
        this.plugin = plugin;
    }


    @TikTokEventHandler
    public void onEvent(LiveClient liveClient, TikTokEvent tikTokEvent)
    {
        var currentProfile = profileService.getCurrentProfile();
        var result = profileProcessor.processProfile(tikTokEvent, currentProfile);
        var commands = result.getProcessedCommands();

        if(!plugin.isEnabled())
        {
            return;
        }

        if(commands.size() == 0)
        {
            return;
        }
        FluentLogger.LOGGER.info("EVENT "+tikTokEvent.getClass().getSimpleName());

        fluentTaskFactory.task(() ->
        {

            for (var command : commands) {
                Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(), command);
            }
            var event = new TikTokLiveSpigotEvent(tikTokEvent,liveClient,currentProfile);
            Bukkit.getServer().getPluginManager().callEvent(event);
        });
    }

    @TikTokEventHandler
    public void onConnect(LiveClient liveClient, TikTokConnectedEvent tikTokEvent)
    {
        FluentLogger.LOGGER.info("Connected");
    }

    @TikTokEventHandler
    public void onDisconnect(LiveClient liveClient, TikTokDisconnectedEvent tikTokEvent)
    {
        FluentLogger.LOGGER.info("Disconnected");
    }


    @TikTokEventHandler
    public void onError(LiveClient liveClient, TikTokErrorEvent tikTokEvent)
    {
        FluentLogger.LOGGER.error("TikTokLiveError",tikTokEvent.getException());
    }

}
