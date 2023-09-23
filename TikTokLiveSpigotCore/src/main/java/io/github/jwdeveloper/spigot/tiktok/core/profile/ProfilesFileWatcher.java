package io.github.jwdeveloper.spigot.tiktok.core.profile;

import io.github.jwdeveloper.ff.core.logger.plugin.FluentLogger;
import io.github.jwdeveloper.ff.core.spigot.events.implementation.EventGroup;
import io.github.jwdeveloper.ff.extension.files.api.fluent_files.FileWatcher;
import io.github.jwdeveloper.ff.plugin.api.logger.PlayerLogger;
import io.github.jwdeveloper.ff.plugin.implementation.config.options.FluentConfigFile;
import io.github.jwdeveloper.spigot.tiktok.core.common.TikTokLiveSpigotConfig;
import io.github.jwdeveloper.spigot.tiktok.core.profiles.ProfileLoader;
import io.github.jwdeveloper.spigot.tiktok.core.profiles.ProfileLoaderResult;
import lombok.Getter;
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

public class ProfilesFileWatcher implements FileWatcher {
    private String content;
    private final PlayerLogger logger;
    private final FluentConfigFile<TikTokLiveSpigotConfig> config;
    @Getter
    private final EventGroup<String> onFileUpdatedEvent;

    public ProfilesFileWatcher(PlayerLogger logger,
                               FluentConfigFile<TikTokLiveSpigotConfig> config) {
        this.logger = logger;
        this.config = config;
        this.onFileUpdatedEvent = new EventGroup<>();
    }



    @Override
    public String getContent() {
        return content;
    }

    @Override
    public void onContentChanged(String content) {
        onFileUpdatedEvent.invoke(content);
    }


    @Override
    public String getFileName() {
        return "profiles.syml";
    }

    @Override
    public void setContent(String text) {
        this.content = text;
    }


}
