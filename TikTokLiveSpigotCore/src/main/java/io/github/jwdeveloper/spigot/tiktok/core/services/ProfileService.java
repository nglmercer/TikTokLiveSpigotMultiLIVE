package io.github.jwdeveloper.spigot.tiktok.core.services;

import io.github.jwdeveloper.ff.core.common.ActionResult;
import io.github.jwdeveloper.ff.core.common.java.StringUtils;
import io.github.jwdeveloper.ff.core.logger.plugin.FluentLogger;
import io.github.jwdeveloper.ff.core.injector.api.annotations.Injection;
import io.github.jwdeveloper.ff.extension.files.api.fluent_files.FluentFile;
import io.github.jwdeveloper.ff.plugin.implementation.config.options.FluentConfigFile;
import io.github.jwdeveloper.spigot.tiktok.api.profiles.TikTokProfileService;
import io.github.jwdeveloper.spigot.tiktok.api.profiles.models.Profile;
import io.github.jwdeveloper.spigot.tiktok.core.common.TikTokLiveSpigotConst;
import io.github.jwdeveloper.spigot.tiktok.core.common.TikTokLiveSpigotConfig;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

@Injection
public class ProfileService implements TikTokProfileService {
    private final List<Profile> profiles;
    private final FluentConfigFile<TikTokLiveSpigotConfig> config;
    private final FluentFile<ProfilesFileWatcher> fileWatcher;

    public ProfileService(FluentConfigFile<TikTokLiveSpigotConfig> config,
                          FluentFile<ProfilesFileWatcher> fileWatcher) {
        this.profiles = new ArrayList<>();
        this.config = config;
        this.fileWatcher = fileWatcher;
        this.fileWatcher.getTarget().onProfilesUpdate(this::updateProfiles);
    }

    @Override
    public String getWebEditorUrl(Player player)
    {
        return TikTokLiveSpigotConst.PROFILE_EDITOR_URL;
    }

    public ActionResult<Profile> setCurrentProfile(String name) {
        var optional = profiles.stream().filter(e -> e.getName().equalsIgnoreCase(name)).findFirst();
        if (optional.isEmpty()) {
            return ActionResult.failed("Profile "+name+ " not found!");
        }
        var profile = optional.get();

        config.get().setProfile(name);
        config.save();
        return ActionResult.success(profile, "Set current profile as " + name);
    }

    public Profile getCurrentProfile() {
        var currentProfileName = config.get().getProfile();
        if (StringUtils.isNullOrEmpty(currentProfileName)) {
            return getDefaultProfile();
        }
        var optional = profiles.stream().filter(e -> e.getName().equalsIgnoreCase(currentProfileName)).findFirst();
        if (optional.isEmpty()) {
            return getDefaultProfile();
        }
        return optional.get();
    }

    public List<Profile> getProfiles() {
        return profiles;
    }


    public void reloadProfiles() {
        fileWatcher.load();
        var newProfiles = fileWatcher.getTarget().loadProfiles();
        updateProfiles(newProfiles);
    }

    private Profile getDefaultProfile()
    {
        if(profiles.isEmpty())
        {
            return Profile.EMPTY;
        }
        return profiles.get(0);
    }

    private void updateProfiles(List<Profile> newProfiles) {
        profiles.clear();
        profiles.addAll(newProfiles);


    }
}
