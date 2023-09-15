package io.github.jwdeveloper.spigot.tiktok.core.profile;

import io.github.jwdeveloper.spigot.tiktok.api.profiles.TikTokProfileExecutor;
import io.github.jwdeveloper.spigot.tiktok.profiles.code.ExecutorContext;
import io.github.jwdeveloper.spigot.tiktok.profiles.common.Profile;
import io.github.jwdeveloper.spigot.tiktok.profiles.processor.ProfileProcessor;
import io.github.jwdeveloper.tiktok.events.TikTokEvent;

import java.util.List;

public class TikTokProfileExecutorImpl implements TikTokProfileExecutor {
    private final ProfileProcessor profileExecutor;

    public TikTokProfileExecutorImpl(ProfileProcessor profileExecutor)
    {
        this.profileExecutor = profileExecutor;
    }


    public void addConstance()
    {

    }

    @Override
    public void addMethod() {

    }

    public List<String> execute(TikTokEvent tikTokEvent, Profile profile)
    {
        var context = new ExecutorContext(tikTokEvent);
        return profileExecutor.processProfile(context, profile).getProcessedCommands();
    }

}
