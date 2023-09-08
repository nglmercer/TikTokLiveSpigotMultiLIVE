package io.github.jwdeveloper.spigot.tiktok.core.common;

import io.github.jwdeveloper.ff.core.common.java.StringUtils;
import io.github.jwdeveloper.ff.core.files.yaml.api.annotations.YamlSection;
import lombok.Data;

@Data
public class TikTokLiveSpigotConfig
{
    @YamlSection(name = "auto-reload-profiles")
    private boolean reloadProfiles = true;

    @YamlSection(name = "auto-connect")
    private boolean autoConnectOnStart = false;

    @YamlSection(name = "tiktok-user")
    private String tiktokUser = StringUtils.EMPTY;

    @YamlSection(name = "profile")
    private String profile  = "default";
}
