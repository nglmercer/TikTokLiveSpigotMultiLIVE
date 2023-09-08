package io.github.jwdeveloper.spigot.tiktok.core;

import io.github.jwdeveloper.ff.core.common.logger.FluentLogger;
import io.github.jwdeveloper.ff.plugin.api.FluentApiSpigotBuilder;
import io.github.jwdeveloper.ff.plugin.api.extention.FluentApiExtension;
import io.github.jwdeveloper.ff.plugin.implementation.FluentApiSpigot;
import io.github.jwdeveloper.spigot.tiktok.core.common.TikTokLiveSpigotConfig;
import io.github.jwdeveloper.spigot.tiktok.core.services.ProfileService;

public class TikTokLiveSpigotExtension implements FluentApiExtension
{
    @Override
    public void onConfiguration(FluentApiSpigotBuilder builder)
    {
        builder.bindToConfig(TikTokLiveSpigotConfig.class,"plugin.tiktok-live");
    }


    @Override
    public void onFluentApiEnable(FluentApiSpigot fluentAPI) throws Exception
    {
        var client = fluentAPI.container().findInjection(TikTokLiveSpigotClient.class);
        var profileService =fluentAPI.container().findInjection(ProfileService.class);
        var config = fluentAPI.container().findInjection(TikTokLiveSpigotConfig.class);
        profileService.reloadProfiles();
        if(config.isAutoConnectOnStart())
        {
            client.connect(config.getTiktokUser());
        }
    }

    @Override
    public void onFluentApiDisabled(FluentApiSpigot fluentAPI) throws Exception {
        try
        {
            var client = fluentAPI.container().findInjection(TikTokLiveSpigotClient.class);
            client.disconnect();
        }
        catch (Exception e)
        {
            FluentLogger.LOGGER.warning("Error while disconnecting client");
        }
    }
}
