package io.github.jwdeveloper.spigot.tiktok.core.profile;

import io.github.jwdeveloper.ff.core.spigot.events.implementation.EventGroup;
import io.github.jwdeveloper.ff.plugin.api.logger.PlayerLogger;
import io.github.jwdeveloper.ff.extension.files.api.fluent_files.FileWatcher;
import io.github.jwdeveloper.ff.plugin.implementation.config.options.FluentConfigFile;
import io.github.jwdeveloper.spigot.tiktok.core.common.TikTokLiveSpigotConfig;
import io.github.jwdeveloper.spigot.tiktok.profiles.common.Profile;
import io.github.jwdeveloper.spigot.tiktok.profiles.common.definitions.ConstDefinition;
import io.github.jwdeveloper.spigot.tiktok.profiles.common.definitions.MethodDefinition;
import io.github.jwdeveloper.spigot.tiktok.profiles.deserializer.ProfileDeserializer;
import io.github.jwdeveloper.spigot.tiktok.profiles.interpreter.ProfileInterpreter;
import lombok.Value;
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class ProfilesFileWatcher implements FileWatcher {
    private String content;
    private final ProfileDeserializer deserializer;
    private final ProfileInterpreter interpreter;
    private final PlayerLogger logger;
    private final EventGroup<ProfileUpdateDto> profilesUpdateEvents;
    private final FluentConfigFile<TikTokLiveSpigotConfig> config;

    public ProfilesFileWatcher(ProfileDeserializer deserializer,
                               ProfileInterpreter interpreter,
                               PlayerLogger logger,
                               FluentConfigFile<TikTokLiveSpigotConfig> config) {
        this.deserializer = deserializer;
        this.interpreter = interpreter;
        this.logger = logger;
        this.config = config;
        this.profilesUpdateEvents = new EventGroup<>();
    }


    public void onProfilesUpdate(Consumer<ProfileUpdateDto> profiles)
    {
        profilesUpdateEvents.subscribe(profiles);
    }

    @Override
    public String getContent() {
        return content;
    }

    @Override
    public void onContentChanged(String content)
    {
        if(!config.get().isReloadProfiles())
        {
            return;
        }
        var newProfiles = loadProfiles();
        profilesUpdateEvents.invoke(newProfiles);
    }

    public ProfileUpdateDto loadProfiles()
    {
        try {
            var yaml = new YamlConfiguration();
            yaml.loadFromString(content);
            var models = deserializer.getProfilesModel(yaml);
            var profiles = interpreter.getProfiles(models.getProfileModels());
            return new ProfileUpdateDto(profiles,models.getConstances(), models.getMethodDefinitions());
        } catch (Exception e)
        {
          logger.error(e,"Unable to reload profiles");
        }
        return new ProfileUpdateDto(new ArrayList<>(),new ArrayList<>(), new ArrayList<>());
    }




    @Override
    public String getFileName() {
        return "profiles.yml";
    }

    @Override
    public void setContent(String text) {
        this.content = text;
    }


    @Value
    public class ProfileUpdateDto
    {
        List<Profile> profiles;

        List<ConstDefinition> constances;

        List<MethodDefinition> methodDefinitions;
    }
}
