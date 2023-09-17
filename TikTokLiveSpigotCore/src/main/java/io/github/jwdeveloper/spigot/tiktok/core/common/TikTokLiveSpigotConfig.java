package io.github.jwdeveloper.spigot.tiktok.core.common;


import io.github.jwdeveloper.ff.core.files.yaml.api.annotations.YamlSection;
import lombok.Data;

@Data
public class TikTokLiveSpigotConfig
{
    @YamlSection(name = "auto-reload-profiles", description = "Dynamic reloads profiles when `profile.yml` file got changed")
    private boolean reloadProfiles;

    @YamlSection(name = "auto-connect", description = "Connects to live when server starts")
    private boolean autoConnectOnStart;

    @YamlSection(name = "active-tiktok-user" , description = "Active tiktok user")
    private String tiktokUser;

    @YamlSection(name = "active-profile" , description = "Active profile")
    private String activeProfile;
}
