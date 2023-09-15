package io.github.jwdeveloper.spigot.tiktok.api.events;

import io.github.jwdeveloper.spigot.tiktok.profiles.common.Profile;
import io.github.jwdeveloper.tiktok.events.TikTokEvent;
import io.github.jwdeveloper.tiktok.live.LiveClient;
import lombok.Getter;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

@Getter
public class TikTokLiveSpigotEvent extends Event
{
    private static final HandlerList handlers = new HandlerList();
    private final TikTokEvent tikTokEvent;
    private final LiveClient liveClient;
    private final Profile profile;

    public TikTokLiveSpigotEvent(TikTokEvent tikTokEvent, LiveClient liveClient, Profile profile)
    {
        this.tikTokEvent = tikTokEvent;
        this.liveClient = liveClient;
        this.profile = profile;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }


    public static HandlerList getHandlerList() {
        return handlers;
    }
}
