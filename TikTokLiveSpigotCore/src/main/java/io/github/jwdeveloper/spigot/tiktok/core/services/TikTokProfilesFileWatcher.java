package io.github.jwdeveloper.spigot.tiktok.core.services;

import io.github.jwdeveloper.ff.core.common.logger.PluginLogger;
import io.github.jwdeveloper.ff.core.spigot.events.implementation.EventGroup;
import io.github.jwdeveloper.ff.extension.files.api.fluent_files.FileWatcher;
import io.github.jwdeveloper.spigot.tiktok.core.profiles.deserializer.ProfileDeserializer;
import io.github.jwdeveloper.spigot.tiktok.core.profiles.interpreter.ProfileInterpreter;
import io.github.jwdeveloper.spigot.tiktok.api.profiles.models.Profile;
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.List;
import java.util.function.Consumer;

public class TikTokProfilesFileWatcher implements FileWatcher {
    private String content;
    private final ProfileDeserializer deserializer;
    private final ProfileInterpreter interpreter;
    private final PluginLogger logger;
    private final EventGroup<List<Profile>> profilesUpdateEvents;

    public TikTokProfilesFileWatcher(ProfileDeserializer deserializer,
                                     ProfileInterpreter interpreter,
                                     PluginLogger logger) {
        this.deserializer = deserializer;
        this.interpreter = interpreter;
        this.logger = logger;
        this.profilesUpdateEvents = new EventGroup<>();
    }


    public void onProfilesUpdate(Consumer<List<Profile>> profiles)
    {
        profilesUpdateEvents.subscribe(profiles);
    }

    @Override
    public String getContent() {
        return content;
    }

    @Override
    public void onContentChanged(String content) {
        var newProfiles = loadProfiles();
        profilesUpdateEvents.invoke(newProfiles);
    }

    public List<Profile> loadProfiles()
    {
        try {
            var yaml = new YamlConfiguration();
            yaml.loadFromString(content);
            var models = deserializer.getProfilesModel(yaml);
            var profiles = interpreter.getProfiles(models);
            return profiles;
        } catch (Exception e)
        {
          logger.error("Unable to load profiles",e);
        }
        return List.of();
    }


    @Override
    public String getFileName() {
        return "profiles.yml";
    }

    @Override
    public void setContent(String text) {
        this.content = text;
    }
}
