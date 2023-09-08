package io.github.jwdeveloper.spigot.tiktok.core;

import io.github.jwdeveloper.ff.extension.files.api.FluentFileType;
import io.github.jwdeveloper.ff.plugin.FluentPlugin;
import io.github.jwdeveloper.spigot.tiktok.core.common.TikTokLiveSpigotMeta;
import io.github.jwdeveloper.spigot.tiktok.core.commands.TikTokLiveSpigotCommands;
import io.github.jwdeveloper.spigot.tiktok.core.services.TikTokProfilesFileWatcher;
import org.bukkit.plugin.java.JavaPlugin;


public final class TikTokLiveSpigotMain extends JavaPlugin {

    @Override
    public void onEnable() {
        FluentPlugin.initialize(this)
                .withExtension(new TikTokLiveSpigotExtension())
                .withBstatsMetrics(19653)
                .withFiles(options ->
                {
                    options.addJsonFile(TikTokLiveSpigotMeta.class);
                    options.addFluentFile(model ->
                    {
                        model.setAllowAutomaticSaving(false);
                        model.setCustomPath("D:\\MC\\spigot_1.19.4\\plugins\\TikTokLiveSpigot\\");
                        model.setType(FluentFileType.FileWatcher);
                        model.setClassType(TikTokProfilesFileWatcher.class);
                    });
                })
                .withCommand(options ->
                {
                    options.setDefaultCommand(TikTokLiveSpigotCommands.class);
                })
                /*  .withUpdater(e ->
                   {
                       e.getGithubOptionsBuilder()
                               .setGithubUserName("jwdeveloper")
                               .setRepositoryName("TikTokLiveSpigot");
                   })*/
                .create();

    }
}
