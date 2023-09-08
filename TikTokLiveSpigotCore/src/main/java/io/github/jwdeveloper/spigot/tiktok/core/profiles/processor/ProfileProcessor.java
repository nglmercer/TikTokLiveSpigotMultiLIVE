package io.github.jwdeveloper.spigot.tiktok.core.profiles.processor;

import io.github.jwdeveloper.ff.core.common.java.StringUtils;
import io.github.jwdeveloper.ff.core.injector.api.annotations.Injection;
import io.github.jwdeveloper.ff.core.injector.api.enums.LifeTime;
import io.github.jwdeveloper.spigot.tiktok.core.profiles.processor.models.ProfileProcessorResult;
import io.github.jwdeveloper.spigot.tiktok.api.profiles.models.Profile;
import io.github.jwdeveloper.spigot.tiktok.api.profiles.models.ProfileCommandParameter;
import io.github.jwdeveloper.spigot.tiktok.api.profiles.models.ProfileEventCommand;
import io.github.jwdeveloper.tiktok.events.TikTokEvent;

import java.util.ArrayList;
import java.util.StringTokenizer;

@Injection(lifeTime = LifeTime.TRANSIENT)
public class ProfileProcessor {
    public ProfileProcessorResult processProfile(TikTokEvent tikTokEvent, Profile profile) {
        var eventClass = tikTokEvent.getClass();
        var events = profile.getEventsCommands();

        if (!events.containsKey(eventClass)) {
            return new ProfileProcessorResult(tikTokEvent, profile);
        }

        var commands = events.get(eventClass);
        var processedCommands = new ArrayList<String>();
        for (var command : commands) {
            var processedCmd = processCommand(tikTokEvent, command);
            processedCommands.add(processedCmd);
        }
        return new ProfileProcessorResult(tikTokEvent, profile, processedCommands);
    }

    private String processCommand(TikTokEvent event, ProfileEventCommand command) {
        var processedParameters = new Object[command.getParameters().size()];
        for (var parameter : command.getParameters()) {
            var processedParameter = processEventParameter(event, parameter);
            processedParameters[parameter.getIndex()] = processedParameter;
        }

        var commandStr = command.getCommandString();
        if (commandStr.startsWith("/")) {
            commandStr = commandStr.replace("/", StringUtils.EMPTY);
        }

        return String.format(commandStr, processedParameters);
    }


    private String processEventParameter(TikTokEvent tikTokEvent, ProfileCommandParameter parameter) {
        var value = getFieldValueByPath(tikTokEvent, parameter.getPath(), true);
        return value;
    }


    private String getFieldValueByPath(Object object, String path, boolean skipFirst) {
        var tokenizer = new StringTokenizer(path, ".");
        var currentObject = object;

        var isSkipped = false;
        while (tokenizer.hasMoreTokens()) {
            if (skipFirst && !isSkipped) {
                tokenizer.nextToken();
                isSkipped = true;
                continue;
            }

            try {
                var currentField = tokenizer.nextToken();
                var field = currentObject.getClass().getDeclaredField(currentField);
                field.setAccessible(true);
                currentObject = field.get(currentObject);

                if (currentObject == null) {
                    return "NULL";
                }
            } catch (Exception e) {
                return "NULL";
            }
        }

        return currentObject.toString();
    }
}
