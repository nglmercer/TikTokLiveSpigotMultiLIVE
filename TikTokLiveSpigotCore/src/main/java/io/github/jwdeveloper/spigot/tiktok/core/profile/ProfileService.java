package io.github.jwdeveloper.spigot.tiktok.core.profile;

import io.github.jwdeveloper.ff.core.common.ActionResult;
import io.github.jwdeveloper.ff.core.common.java.StringUtils;
import io.github.jwdeveloper.ff.core.injector.api.annotations.Injection;
import io.github.jwdeveloper.ff.core.logger.plugin.FluentLogger;
import io.github.jwdeveloper.ff.core.spigot.events.implementation.EventGroup;
import io.github.jwdeveloper.ff.extension.files.api.fluent_files.FluentFile;
import io.github.jwdeveloper.ff.plugin.api.logger.PlayerLogger;
import io.github.jwdeveloper.ff.plugin.implementation.config.options.FluentConfigFile;
import io.github.jwdeveloper.spigot.tiktok.api.profiles.TikTokProfileService;
import io.github.jwdeveloper.spigot.tiktok.api.profiles.TikTokProfilesExecutor;
import io.github.jwdeveloper.spigot.tiktok.core.profiles.ProfileLoader;
import io.github.jwdeveloper.spigot.tiktok.api.profiles.models.Profile;
import io.github.jwdeveloper.spigot.tiktok.core.common.TikTokLiveSpigotConst;
import io.github.jwdeveloper.spigot.tiktok.core.common.TikTokLiveSpigotConfig;
import io.github.jwdeveloper.spigot.tiktok.profiles.common.exceptions.SymlEngineException;
import lombok.Getter;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

@Injection
public class ProfileService implements TikTokProfileService {

    @Getter
    private final List<Profile> profiles;
    private final FluentConfigFile<TikTokLiveSpigotConfig> config;
    private final FluentFile<ProfilesFileWatcher> fileWatcher;
    private final EventGroup<List<Profile>> onProfileUpdatedEvent;
    private final TikTokProfilesExecutor profilesExecutor;
    private final ProfileLoader profileLoader;
    private final PlayerLogger playerLogger;

    public ProfileService(FluentConfigFile<TikTokLiveSpigotConfig> config,
                          FluentFile<ProfilesFileWatcher> fileWatcher,
                          TikTokProfilesExecutor profilesExecutor,
                          ProfileLoader profileLoader, PlayerLogger playerLogger) {
        this.profilesExecutor = profilesExecutor;
        this.profileLoader = profileLoader;
        this.playerLogger = playerLogger;
        this.profiles = new ArrayList<>();
        this.config = config;
        this.fileWatcher = fileWatcher;
        this.fileWatcher.getTarget().getOnFileUpdatedEvent().subscribe(this::onProfileReoladed);
        this.onProfileUpdatedEvent = new EventGroup<>();
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

    public void reloadProfiles()
    {
       handleReload(fileWatcher.getTarget().getContent());
    }

    private void onProfileReoladed(String content)
    {
        if (!config.get().isReloadProfiles()) {
            return;
        }
        handleReload(content);
    }

    public void clearProfiles()
    {
        profiles.clear();
    }

    private void handleReload(String content)
    {

        try
        {
            FluentLogger.LOGGER.info("AAAAAAAAAAAAAA");
            var yaml = new YamlConfiguration();
            yaml.loadFromString(content);
            FluentLogger.LOGGER.info("BBBBBBBBBBBBBBBB");
            var profile = profileLoader.loadProfile("custom" ,content);
            FluentLogger.LOGGER.info("CCCCCCCCCCCCCCCCCCCCCC");
            updateProfiles(profile);

        }
        catch (SymlEngineException e)
        {
            FluentLogger.LOGGER.info("XXXXXXXXXXX");
            profiles.clear();
            FluentLogger.LOGGER.error("ERROR WHILE LOADING PROFILES",e);
        }
        catch (Exception e)
        {
            FluentLogger.LOGGER.info("CCCCCCCCCCCCCCCCCCCCCCCCC");
        }
    }

    private Profile getDefaultProfile()
    {
        if(profiles.isEmpty())
        {
            return Profile.EMPTY();
        }
        return profiles.get(0);
    }

    private void updateProfiles(Profile profile)
    {
        profiles.clear();
        profilesExecutor.execute(profile.getIntiBlock());
        profiles.add(profile);
        onProfileUpdatedEvent.invoke(profiles);

        playerLogger.success("Profiles loaded successfully").sendToAllPlayer();
        FluentLogger.LOGGER.success("==============================================");
        FluentLogger.LOGGER.success("Profiles updated successfully: ");
        for (var _profile : profiles) {
            FluentLogger.LOGGER.success("-", _profile.getName());
            FluentLogger.LOGGER.success("    events:");
            for (var event : _profile.getEventsBlocks().entrySet()) {
                FluentLogger.LOGGER.success("      -", event.getKey().getSimpleName(), "-> code lines "+event.getValue().getBody().size());
            }
        }
        FluentLogger.LOGGER.success("==============================================");
    }
}
