package io.github.jwdeveloper.spigot.tiktok.core;

import io.github.jwdeveloper.ff.core.logger.plugin.FluentLogger;
import io.github.jwdeveloper.ff.extension.files.api.FluentFileType;
import io.github.jwdeveloper.ff.plugin.FluentPlugin;
import io.github.jwdeveloper.spigot.tiktok.core.commands.TikTokLiveSpigotCommands;
import io.github.jwdeveloper.spigot.tiktok.core.common.TikTokLiveSpigotConst;
import io.github.jwdeveloper.spigot.tiktok.core.common.TikTokLiveSpigotMeta;
import io.github.jwdeveloper.spigot.tiktok.core.services.ProfilesFileWatcher;
import org.bukkit.plugin.java.JavaPlugin;

///gamerule sendCommandFeedback false

public final class TikTokLiveSpigotMain extends JavaPlugin {

    @Override
    public void onEnable() {


        FluentPlugin.initialize(this)
                .withExtension(new TikTokLiveSpigotExtension())
                .withBstatsMetrics(TikTokLiveSpigotConst.STATISTIC_ID)
                .withFiles(options ->
                {
                    options.addJsonFile(TikTokLiveSpigotMeta.class);
                    options.addFluentFile(model ->
                    {
                        model.setAllowAutomaticSaving(false);
                        model.setCustomPath(this.getDataFolder().getAbsolutePath());
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
                })
                .create();
    }
}
