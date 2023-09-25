package io.github.jwdeveloper.spigot.tiktok.core;

import io.github.jwdeveloper.ff.core.logger.plugin.FluentLogger;
import io.github.jwdeveloper.spigot.tiktok.api.profiles.TikTokProfilesExecutor;
import io.github.jwdeveloper.spigot.tiktok.api.profiles.models.Profile;
import io.github.jwdeveloper.spigot.tiktok.profiles.ast.ProgramSYML;
import io.github.jwdeveloper.spigot.tiktok.profiles.evaluator.Evaliator;
import io.github.jwdeveloper.spigot.tiktok.profiles.evaluator.EvaluatorFactory;
import io.github.jwdeveloper.tiktok.events.TikTokEvent;

import java.util.List;

public class TikTokProfileExecutorImpl implements TikTokProfilesExecutor {
    private final Evaliator evaliator;

    public TikTokProfileExecutorImpl() {
        evaliator = EvaluatorFactory.create();
    }

    public List<String> execute(TikTokEvent tikTokEvent, Profile profile) {
        if (!profile.getEventsBlocks().containsKey(tikTokEvent.getClass())) {
            return List.of();
        }

        try {
            var eventProgram = profile.getEventsBlocks().get(tikTokEvent.getClass());
            evaliator.getContext().clearOutput();
            evaliator.getContext().addVariable("event", tikTokEvent);
            evaliator.evaluate(eventProgram);
        } catch (Exception e)
        {
            throw e;
        }
        return evaliator.getContext().getOutput();
    }

    public List<String> execute(ProgramSYML programSYML) {
        return evaliator.evaluate(programSYML).getContext().getOutput();
    }

}
