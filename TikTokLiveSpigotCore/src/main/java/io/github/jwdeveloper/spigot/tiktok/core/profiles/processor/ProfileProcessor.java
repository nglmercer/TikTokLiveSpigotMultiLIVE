package io.github.jwdeveloper.spigot.tiktok.core.profiles.processor;

import io.github.jwdeveloper.ff.core.common.java.StringUtils;
import io.github.jwdeveloper.ff.core.injector.api.annotations.Injection;
import io.github.jwdeveloper.ff.core.injector.api.enums.LifeTime;
import io.github.jwdeveloper.spigot.tiktok.core.profiles.interpreter.blocks.IfExpressionBlock;
import io.github.jwdeveloper.spigot.tiktok.core.profiles.interpreter.blocks.RepeatBlock;
import io.github.jwdeveloper.spigot.tiktok.core.profiles.processor.models.ProfileProcessorResult;
import io.github.jwdeveloper.spigot.tiktok.api.profiles.models.Profile;
import io.github.jwdeveloper.spigot.tiktok.api.profiles.models.ProfileEventCommand;
import io.github.jwdeveloper.tiktok.events.TikTokEvent;

import java.util.ArrayList;
import java.util.List;

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
        for (var command : commands)
        {
            var processedCmd = processCommand(tikTokEvent, command);
            processedCommands.addAll(processedCmd);
        }
        return new ProfileProcessorResult(tikTokEvent, profile, processedCommands);
    }

    private List<String> processCommand(TikTokEvent event, ProfileEventCommand command) {
        var processedParameters = new Object[command.getCodeBlocks().size()];
        var repetitions = 1;
        for (var codeBlock : command.getCodeBlocks())
        {
            if(codeBlock instanceof IfExpressionBlock)
            {
                var result = (boolean) codeBlock.execute(event);
                if(!result)
                {
                    return List.of();
                }
                processedParameters[codeBlock.getBlockIndex()] = StringUtils.EMPTY;
                continue;
            }
            if(codeBlock instanceof RepeatBlock)
            {
                repetitions = (int)codeBlock.execute(event);
                processedParameters[codeBlock.getBlockIndex()] = StringUtils.EMPTY;
                continue;
            }
            processedParameters[codeBlock.getBlockIndex()] = codeBlock.execute(event);
        }

        var commandStr = command.getCommandString();

        var cmdOutput = String.format(commandStr, processedParameters);
        if (cmdOutput.contains("/")) {
            cmdOutput = cmdOutput.replace("/", StringUtils.EMPTY);
        }

        var output = new ArrayList<String>();
        for(var i =0;i<repetitions;i++)
        {
            output.add(cmdOutput);
        }
        return output;
    }





}
