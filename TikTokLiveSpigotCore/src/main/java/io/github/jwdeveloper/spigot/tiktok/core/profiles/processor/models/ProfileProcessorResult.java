package io.github.jwdeveloper.spigot.tiktok.core.profiles.processor.models;

import io.github.jwdeveloper.spigot.tiktok.api.profiles.models.Profile;
import io.github.jwdeveloper.tiktok.events.TikTokEvent;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ProfileProcessorResult
{
    private TikTokEvent event;

    private Profile profile;

    private List<String> processedCommands;


    public ProfileProcessorResult(TikTokEvent event, Profile profile, List<String> processedCommands) {
        this.event = event;
        this.profile = profile;
        this.processedCommands = processedCommands;
    }

    public ProfileProcessorResult(TikTokEvent event, Profile profile) {
        this.event = event;
        this.profile = profile;
        this.processedCommands = new ArrayList<>();
    }
}
