package io.github.jwdeveloper.spigot.tiktok.core.common;

import io.github.jwdeveloper.ff.core.common.java.StringUtils;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class TikTokLiveSpigotMeta
{

    private String currentProfile = StringUtils.EMPTY;

    private String currentHost = StringUtils.EMPTY;

    private List<String> hosts = new ArrayList<>();

}
