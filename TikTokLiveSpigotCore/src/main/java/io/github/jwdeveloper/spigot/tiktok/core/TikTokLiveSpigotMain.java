package io.github.jwdeveloper.spigot.tiktok.core;

import io.github.jwdeveloper.ff.core.files.FileUtility;
import io.github.jwdeveloper.ff.core.injector.implementation.containers.builder.ContainerBuilderImpl;
import io.github.jwdeveloper.ff.core.logger.plugin.FluentLogger;
import io.github.jwdeveloper.ff.extension.files.api.FluentFileType;
import io.github.jwdeveloper.ff.extension.gui.FluentInventoryApi;
import io.github.jwdeveloper.ff.extension.translator.FluentTranslatorAPI;
import io.github.jwdeveloper.ff.extension.updater.FluentUpdaterApi;
import io.github.jwdeveloper.ff.plugin.FluentPlugin;
import io.github.jwdeveloper.ff.plugin.FluentPluginBuilder;
import io.github.jwdeveloper.spigot.tiktok.core.commands.TikTokLiveSpigotCommands;
import io.github.jwdeveloper.spigot.tiktok.core.common.TikTokLiveSpigotConst;
import io.github.jwdeveloper.spigot.tiktok.core.common.TikTokLiveSpigotMeta;
import io.github.jwdeveloper.spigot.tiktok.core.profile.ProfilesFileWatcher;
import io.github.jwdeveloper.spigot.tiktok.core.tests.LoggerFilter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import java.nio.file.Paths;


public final class TikTokLiveSpigotMain extends JavaPlugin {

    @Override
    public void onEnable()
    {
        useFluentFramework(this).create();

    }

    public static FluentPluginBuilder useFluentFramework(Plugin plugin)
    {
        return useFluentFramework(plugin, FluentPlugin.initialize(plugin));
    }

    public static FluentPluginBuilder useFluentFramework(Plugin plugin, FluentPluginBuilder builder) {
        return builder
                .withTranslator()
                .withExtension(new TikTokLiveSpigotExtension())
                .withExtension(FluentInventoryApi.use())
                .withBstatsMetrics(TikTokLiveSpigotConst.STATISTIC_ID)
                .withFiles(options ->
                {
                    options.addJsonFile(TikTokLiveSpigotMeta.class);
                    options.addFluentFile(model ->
                    {
                        model.setAllowAutomaticSaving(false);
                        model.setCustomPath(Paths.get(plugin.getDataFolder().getAbsolutePath(),"profiles").toString());
                        model.setType(FluentFileType.FileWatcher);
                        model.setClassType(ProfilesFileWatcher.class);
                        model.setOnIfFileNotFound(path ->
                        {
                            FluentLogger.LOGGER.info("Creating default profile");
                            try {
                                var inputStream = plugin.getResource("default.sy");
                                var content = FileUtility.loadInputStream(inputStream);
                                FileUtility.save(content, path);
                            } catch (Exception e)
                            {
                                FluentLogger.LOGGER.error("Unable to load default profile", e);
                            }
                        });
                    });
                })
                .withCommand(options ->
                {
                    options.setDefaultCommand(TikTokLiveSpigotCommands.class);
                })
                .withUpdater(e ->
                {
                    e.getGithubOptionsBuilder()
                            .setGithubUserName("jwdeveloper")
                            .setRepositoryName("TikTokLiveSpigot");
                });

    }
}
