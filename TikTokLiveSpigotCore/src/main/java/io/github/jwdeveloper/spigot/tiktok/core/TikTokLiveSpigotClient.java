package io.github.jwdeveloper.spigot.tiktok.core;

import io.github.jwdeveloper.ff.core.common.logger.FluentLogger;
import io.github.jwdeveloper.ff.core.injector.api.annotations.Injection;
import io.github.jwdeveloper.spigot.tiktok.core.listeners.TikTokSpigotEventListener;

import io.github.jwdeveloper.tiktok.TikTokLive;
import io.github.jwdeveloper.tiktok.live.LiveClient;


@Injection
public class TikTokLiveSpigotClient {


    private final TikTokSpigotEventListener eventListener;
    private LiveClient client;

    public TikTokLiveSpigotClient(TikTokSpigotEventListener eventListener) {
        this.eventListener = eventListener;
    }

    public void connect(String userName)
    {

        disconnect();
        client = createClient(userName);
        client.connect();

    }

    public void disconnect()
    {
        FluentLogger.LOGGER.info("Disconnecting");
        if (client != null) {
            client.disconnect();
        }
    }

    private LiveClient createClient(String userName) {
        return TikTokLive.newClient(userName)
                .configure(clientSettings ->
                {
                    clientSettings.setDownloadGiftInfo(false);
                    clientSettings.setHandleExistingMessagesOnConnect(false);
                })
                .addListener(eventListener)
                .build();
    }
}
