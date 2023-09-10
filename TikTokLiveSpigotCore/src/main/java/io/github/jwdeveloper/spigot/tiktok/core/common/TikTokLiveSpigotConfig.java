package io.github.jwdeveloper.spigot.tiktok.core.common;

import io.github.jwdeveloper.ff.core.common.java.StringUtils;
import io.github.jwdeveloper.ff.core.files.yaml.api.annotations.YamlSection;
import lombok.Data;

@Data
public class TikTokLiveSpigotConfig
{
    @YamlSection(name = "auto-reload-profiles", description = "Dynamic reloads profiles when `profile.yml` file got changed")
    private boolean reloadProfiles;

    @YamlSection(name = "auto-connect", description = "Connects to live when server starts")
    private boolean autoConnectOnStart;

    @YamlSection(name = "tiktok-user" , description = "Default tiktok user")
    private String tiktokUser;

    @YamlSection(name = "profile" , description = "Default  profile")
    private String profile;
}
