package io.github.jwdeveloper.spigot.tiktok.profiles.code;

import io.github.jwdeveloper.spigot.tiktok.profiles.common.definitions.ConstDefinition;
import io.github.jwdeveloper.spigot.tiktok.profiles.common.definitions.MethodDefinition;
import io.github.jwdeveloper.tiktok.events.TikTokEvent;
import lombok.Data;
import java.util.HashMap;
import java.util.Map;

@Data
public class ExecutorContext {
    TikTokEvent tikTokEvent;

    private final Map<String, MethodDefinition> methods;

    private final Map<String, ConstDefinition> constants;


    public ExecutorContext(TikTokEvent tikTokEvent, Map<String, MethodDefinition> methods, Map<String, ConstDefinition> constants) {
        this.tikTokEvent = tikTokEvent;
        this.methods = methods;
        this.constants = constants;
    }

    public ExecutorContext(TikTokEvent tikTokEvent) {
        this(tikTokEvent, new HashMap<>(), new HashMap<>());
    }
}
