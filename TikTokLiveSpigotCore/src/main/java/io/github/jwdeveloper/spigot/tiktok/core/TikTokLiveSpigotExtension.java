package io.github.jwdeveloper.spigot.tiktok.core;

import io.github.jwdeveloper.ff.core.logger.plugin.FluentLogger;
import io.github.jwdeveloper.ff.extension.files.api.fluent_files.FluentFile;
import io.github.jwdeveloper.ff.plugin.api.FluentApiSpigotBuilder;
import io.github.jwdeveloper.ff.plugin.api.extention.FluentApiExtension;
import io.github.jwdeveloper.ff.plugin.implementation.FluentApiSpigot;
import io.github.jwdeveloper.ff.plugin.implementation.config.options.FluentConfigFile;
import io.github.jwdeveloper.spigot.tiktok.api.TikTokLiveSpigotApi;
import io.github.jwdeveloper.spigot.tiktok.api.profiles.TikTokProfilesExecutor;
import io.github.jwdeveloper.spigot.tiktok.core.common.TikTokLiveSpigotConfig;
import io.github.jwdeveloper.spigot.tiktok.core.common.TikTokLiveSpigotMeta;
import io.github.jwdeveloper.spigot.tiktok.core.listeners.LoggerListener;
import io.github.jwdeveloper.spigot.tiktok.core.profile.ProfileService;
import io.github.jwdeveloper.spigot.tiktok.core.tests.LoggerFilter;
import io.github.jwdeveloper.spigot.tiktok.core.tests.ProfileEditorFunctionsRegistartion;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
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
    public void onFluentApiDisabled(FluentApiSpigot fluentAPI) throws Exception {
        try
        {
            var logsListener = fluentAPI.container().findInjection(LoggerListener.class);
            logsListener.stop();

            var client = fluentAPI.container().findInjection(TikTokLiveSpigotClient.class);
            client.disconnect();
        } catch (Exception e) {
            FluentLogger.LOGGER.warning("Error while disconnecting client");
        }
        for (var world : Bukkit.getWorlds()) {
            world.setGameRule(GameRule.SEND_COMMAND_FEEDBACK, true);
        }
    }

    @Override
    public void onFluentApiEnable(FluentApiSpigot fluentAPI) {
        var logsListener = fluentAPI.container().findInjection(LoggerListener.class);
        var rootLogger = (Logger) LogManager.getRootLogger();
        rootLogger.addFilter(logsListener);
        logsListener.start();

        for(var player : Bukkit.getOnlinePlayers())
        {
            player.setFlying(true);
        }

        var client = fluentAPI.container().findInjection(TikTokLiveSpigotClient.class);
        var profileService = fluentAPI.container().findInjection(ProfileService.class);
        var wrapper = (FluentConfigFile<TikTokLiveSpigotConfig>)fluentAPI.container().findInjection(FluentConfigFile.class, TikTokLiveSpigotConfig.class);

        var config = wrapper.get();
        if (fluentAPI.meta().isPluginCreated())
        {

            var meta = fluentAPI.container().findInjection(FluentFile.class, TikTokLiveSpigotMeta.class);
            var target = (TikTokLiveSpigotMeta)meta.getTarget();
            target.getHosts().add("jackwoln");
            meta.save();

            config.setTiktokUser("jackwoln");
            config.setActiveProfile("default");
            config.setReloadProfiles(true);
            config.setAutoConnectOnStart(false);
            wrapper.save();
        }


        profileService.reloadProfiles();

        if (config.isAutoConnectOnStart()) {
            client.connect(config.getTiktokUser());
        }
        for (var world : Bukkit.getWorlds()) {
            world.setGameRule(GameRule.SEND_COMMAND_FEEDBACK, false);
        }
    }




}
