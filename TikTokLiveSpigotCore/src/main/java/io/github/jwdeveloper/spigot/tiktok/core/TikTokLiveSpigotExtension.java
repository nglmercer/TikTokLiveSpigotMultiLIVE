package io.github.jwdeveloper.spigot.tiktok.core;

import io.github.jwdeveloper.ff.core.logger.plugin.FluentLogger;
import io.github.jwdeveloper.ff.plugin.api.FluentApiSpigotBuilder;
import io.github.jwdeveloper.ff.plugin.api.extention.FluentApiExtension;
import io.github.jwdeveloper.ff.plugin.implementation.FluentApiSpigot;
import io.github.jwdeveloper.spigot.tiktok.api.TikTokLiveSpigotApi;
import io.github.jwdeveloper.spigot.tiktok.api.profiles.TikTokProfilesExecutor;
import io.github.jwdeveloper.spigot.tiktok.core.common.TikTokLiveSpigotConfig;
import io.github.jwdeveloper.spigot.tiktok.core.profile.ProfileService;
import io.github.jwdeveloper.spigot.tiktok.core.profile.TikTokProfileExecutorImpl;
import io.github.jwdeveloper.spigot.tiktok.core.profile_editor_old.ProfileEditorFunctionsRegistartion;
import org.bukkit.Bukkit;
import org.bukkit.GameRule;

public class TikTokLiveSpigotExtension implements FluentApiExtension {
    @Override
    public void onConfiguration(FluentApiSpigotBuilder builder) {
        builder.bindToConfig(TikTokLiveSpigotConfig.class, "tiktok-live");

        builder.loggerConfiguration();



        var container = builder.container();
        container.registerSigleton(TikTokLiveSpigotApi.class, TikTokLiveSpigotApiImpl.class);
        container.registerSigleton(TikTokProfilesExecutor.class, TikTokProfileExecutorImpl.class);
    }


    @Override
    public void onFluentApiEnable(FluentApiSpigot fluentAPI) throws Exception {

        var functionRegistration = fluentAPI.container().findInjection(ProfileEditorFunctionsRegistartion.class);
        functionRegistration.register();


        var client = fluentAPI.container().findInjection(TikTokLiveSpigotClient.class);
        var profileService = fluentAPI.container().findInjection(ProfileService.class);
        var config = fluentAPI.container().findInjection(TikTokLiveSpigotConfig.class);

        profileService.reloadProfiles();

        if (config.isAutoConnectOnStart()) {
            client.connect(config.getTiktokUser());
        }
        for (var world : Bukkit.getWorlds()) {
            world.setGameRule(GameRule.SEND_COMMAND_FEEDBACK, false);
        }
    }

    @Override
    public void onFluentApiDisabled(FluentApiSpigot fluentAPI) throws Exception {
        try {
            var client = fluentAPI.container().findInjection(TikTokLiveSpigotClient.class);
            client.disconnect();
        } catch (Exception e) {
            FluentLogger.LOGGER.warning("Error while disconnecting client");
        }
        for (var world : Bukkit.getWorlds()) {
            world.setGameRule(GameRule.SEND_COMMAND_FEEDBACK, true);
        }
    }
}
