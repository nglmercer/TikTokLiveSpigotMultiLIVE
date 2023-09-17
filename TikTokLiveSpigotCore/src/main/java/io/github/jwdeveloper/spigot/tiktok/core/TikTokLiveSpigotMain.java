package io.github.jwdeveloper.spigot.tiktok.core;

import io.github.jwdeveloper.ff.extension.files.api.FluentFileType;
import io.github.jwdeveloper.ff.extension.gui.FluentInventoryApi;
import io.github.jwdeveloper.ff.plugin.FluentPlugin;
import io.github.jwdeveloper.ff.plugin.FluentPluginBuilder;
import io.github.jwdeveloper.spigot.tiktok.core.commands.TikTokLiveSpigotCommands;
import io.github.jwdeveloper.spigot.tiktok.core.common.TikTokLiveSpigotConst;
import io.github.jwdeveloper.spigot.tiktok.core.common.TikTokLiveSpigotMeta;
import io.github.jwdeveloper.spigot.tiktok.core.profile.ProfilesFileWatcher;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public final class TikTokLiveSpigotMain extends JavaPlugin {

    @Override
    public void onEnable() {
        useFluentFramework(this).create();
    }

    public static FluentPluginBuilder useFluentFramework(Plugin plugin) {
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
                        model.setCustomPath(plugin.getDataFolder().getAbsolutePath());
                        model.setType(FluentFileType.FileWatcher);
                        model.setClassType(ProfilesFileWatcher.class);
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
