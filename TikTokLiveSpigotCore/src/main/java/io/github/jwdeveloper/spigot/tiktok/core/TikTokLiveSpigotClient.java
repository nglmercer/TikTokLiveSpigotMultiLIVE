package io.github.jwdeveloper.spigot.tiktok.core;

import io.github.jwdeveloper.ff.core.injector.api.annotations.Injection;
import io.github.jwdeveloper.ff.core.logger.plugin.FluentLogger;
import io.github.jwdeveloper.ff.core.logger.plugin.PluginLogger;
import io.github.jwdeveloper.ff.core.spigot.tasks.api.FluentTaskFactory;
import io.github.jwdeveloper.ff.extension.files.api.fluent_files.FluentFile;
import io.github.jwdeveloper.ff.plugin.implementation.config.options.FluentConfigFile;
import io.github.jwdeveloper.spigot.tiktok.core.common.TikTokLiveSpigotConfig;
import io.github.jwdeveloper.spigot.tiktok.core.common.TikTokLiveSpigotMeta;
import io.github.jwdeveloper.spigot.tiktok.core.listeners.TikTokSpigotEventListener;
import io.github.jwdeveloper.tiktok.TikTokLive;
import io.github.jwdeveloper.tiktok.exceptions.TikTokLiveOfflineHostException;
import io.github.jwdeveloper.tiktok.live.ConnectionState;
import io.github.jwdeveloper.tiktok.live.LiveClient;

import java.util.logging.Level;


@Injection
public class TikTokLiveSpigotClient {
    private final TikTokSpigotEventListener eventListener;
    private final FluentFile<TikTokLiveSpigotMeta> metaFluentFile;
    private final FluentConfigFile<TikTokLiveSpigotConfig> configFile;
    private final FluentTaskFactory taskFactory;
    private final PluginLogger pluginLogger;
    private LiveClient client;

    public TikTokLiveSpigotClient(TikTokSpigotEventListener eventListener,
                                  FluentFile<TikTokLiveSpigotMeta> metaFluentFile,
                                  FluentConfigFile<TikTokLiveSpigotConfig> configFile,
                                  FluentTaskFactory taskFactory, PluginLogger pluginLogger) {
        this.eventListener = eventListener;
        this.metaFluentFile = metaFluentFile;
        this.taskFactory = taskFactory;
        this.configFile = configFile;
        this.pluginLogger = pluginLogger;
    }


    public String getRecentHost()
    {
        return configFile.get().getTiktokUser();
    }

    public ConnectionState getConnectionState()
    {
        if(client == null)
        {
            return ConnectionState.DISCONNECTED;
        }
        return client.getRoomInfo().getConnectionState();
    }


    public void connect(String userName) {

        taskFactory.taskAsync(() ->
        {
            try
            {
                disconnect();
                client = createClient(userName);
                client.connect();

                var hosts = metaFluentFile.getTarget().getHosts();
                if(!hosts.contains(userName))
                {
                    hosts.add(userName);
                }
                metaFluentFile.save();
                configFile.get().setTiktokUser(userName);
                configFile.save();
            }catch (TikTokLiveOfflineHostException e)
            {
                pluginLogger.warning(e.getMessage());
            }

        });
    }

    public void disconnect() {
        if (client != null) {
            client.disconnect();
        }
    }

    private LiveClient createClient(String userName) {
        return TikTokLive.newClient(userName)
                .configure(clientSettings ->
                {
                    clientSettings.setLogLevel(Level.OFF);
                    clientSettings.setPrintToConsole(false);
                    clientSettings.setDownloadGiftInfo(false);
                    clientSettings.setHandleExistingMessagesOnConnect(false);
                })
                .addListener(eventListener)
                .build();
    }
}
