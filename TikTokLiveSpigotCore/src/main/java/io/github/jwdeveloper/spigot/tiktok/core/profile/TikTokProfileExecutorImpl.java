package io.github.jwdeveloper.spigot.tiktok.core.profile;

import io.github.jwdeveloper.spigot.tiktok.api.profiles.TikTokProfileEditor;
import io.github.jwdeveloper.spigot.tiktok.profiles.code.ExecutorContext;
import io.github.jwdeveloper.spigot.tiktok.profiles.common.Profile;
import io.github.jwdeveloper.spigot.tiktok.profiles.common.definitions.ConstDefinition;
import io.github.jwdeveloper.spigot.tiktok.profiles.common.definitions.MethodDefinition;
import io.github.jwdeveloper.spigot.tiktok.profiles.processor.ProfileProcessor;
import io.github.jwdeveloper.tiktok.events.TikTokEvent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class TikTokProfileExecutorImpl implements TikTokProfileEditor {
    private final ProfileProcessor profileExecutor;
    public Map<String, MethodDefinition> methods;
    public Map<String, ConstDefinition> constants;

    public TikTokProfileExecutorImpl(ProfileProcessor profileExecutor)
    {
        this.profileExecutor = profileExecutor;
        methods = new HashMap<>();
        constants = new HashMap<>();
    }

    @Override
    public void addConstance(ConstDefinition constDefinition) {
        constants.put(constDefinition.getName(),constDefinition);
    }

    @Override
    public void addConstance(String name, Object value) {
        constants.put(name,new ConstDefinition(name,value));
    }

    @Override
    public void addMethod(String name, Function<List<Object>, Object> onInvoke) {
        methods.put(name,new MethodDefinition(name,onInvoke, false));
    }

    @Override
    public void addDefaultMethod(String name, Function<List<Object>, Object> onInvoke) {
        methods.put(name,new MethodDefinition(name,onInvoke, true));
    }

    @Override
    public void addMethod(MethodDefinition methodDefinition) {
        methods.put(methodDefinition.getName(),methodDefinition);
    }



    public List<String> execute(TikTokEvent tikTokEvent, Profile profile)
    {
        var context = new ExecutorContext(tikTokEvent,methods,constants);
        return profileExecutor.processProfile(context, profile).getProcessedCommands();
    }

}
