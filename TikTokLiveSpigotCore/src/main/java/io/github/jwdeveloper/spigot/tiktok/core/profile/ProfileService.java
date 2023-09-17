package io.github.jwdeveloper.spigot.tiktok.core.profile;

import io.github.jwdeveloper.ff.core.common.ActionResult;
import io.github.jwdeveloper.ff.core.common.java.StringUtils;
import io.github.jwdeveloper.ff.core.injector.api.annotations.Injection;
import io.github.jwdeveloper.ff.extension.files.api.fluent_files.FluentFile;
import io.github.jwdeveloper.ff.plugin.implementation.config.options.FluentConfigFile;
import io.github.jwdeveloper.spigot.tiktok.api.profiles.TikTokProfileEditor;
import io.github.jwdeveloper.spigot.tiktok.api.profiles.TikTokProfileService;
import io.github.jwdeveloper.spigot.tiktok.profiles.common.Profile;
import io.github.jwdeveloper.spigot.tiktok.core.common.TikTokLiveSpigotConst;
import io.github.jwdeveloper.spigot.tiktok.core.common.TikTokLiveSpigotConfig;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

@Injection
public class ProfileService implements TikTokProfileService {
    private final List<Profile> profiles;
    private final FluentConfigFile<TikTokLiveSpigotConfig> config;
    private final FluentFile<ProfilesFileWatcher> fileWatcher;
    private Consumer<List<Profile>> onUpdated;
    private final TikTokProfileEditor tikTokProfileEditor;

    public ProfileService(FluentConfigFile<TikTokLiveSpigotConfig> config,
                          FluentFile<ProfilesFileWatcher> fileWatcher,
                          TikTokProfileEditor tikTokProfileEditor) {
        this.tikTokProfileEditor = tikTokProfileEditor;
        this.profiles = new ArrayList<>();
        this.config = config;
        this.fileWatcher = fileWatcher;
        this.fileWatcher.getTarget().onProfilesUpdate(this::updateProfiles);
        this.onUpdated = (e)-> {};
    }

    @Override
    public String getWebEditorUrl(Player player)
    {
        return TikTokLiveSpigotConst.PROFILE_EDITOR_URL;
    }

    public ActionResult<Profile> setCurrentProfile(String name) {
        var optional = profiles.stream().filter(e -> e.getName().equalsIgnoreCase(name)).findFirst();
        if (optional.isEmpty()) {
            return ActionResult.failed("Profile not found! "+ChatColor.YELLOW+name);
        }
        var profile = optional.get();

        config.get().setActiveProfile(name);
        config.save();
        return ActionResult.success(profile, "Active profile changed to " + ChatColor.DARK_GREEN+ name);
    }

    public Profile getCurrentProfile() {
        var currentProfileName = config.get().getActiveProfile();
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

    private void updateProfiles(ProfilesFileWatcher.ProfileUpdateDto updateDto)
    {
        profiles.clear();
        profiles.addAll(updateDto.getProfiles());

        for(var cons : updateDto.getConstances())
        {
            tikTokProfileEditor.addConstance(cons);
        }
        for(var method : updateDto.getMethodDefinitions())
        {
            tikTokProfileEditor.addMethod(method);
        }
        onUpdated.accept(profiles);
    }

    public void onProfilesUpdated(Consumer<List<Profile>> profiles)
    {
        onUpdated = profiles;
    }
}
