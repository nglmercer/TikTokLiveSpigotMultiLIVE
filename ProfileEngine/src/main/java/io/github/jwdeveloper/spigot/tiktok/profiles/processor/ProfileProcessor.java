package io.github.jwdeveloper.spigot.tiktok.profiles.processor;

import io.github.jwdeveloper.ff.core.common.java.StringUtils;
import io.github.jwdeveloper.spigot.tiktok.profiles.code.ExecutorContext;
import io.github.jwdeveloper.spigot.tiktok.profiles.code.blocks.if_blocks.IfBlock;
import io.github.jwdeveloper.spigot.tiktok.profiles.common.Profile;
import io.github.jwdeveloper.spigot.tiktok.profiles.common.ProfileEventCommand;

import java.util.ArrayList;
import java.util.List;


public class ProfileProcessor
{
    public ProfileProcessorResult processProfile(ExecutorContext context, Profile profile) {
        var eventClass = context.getTikTokEvent().getClass();
        var events = profile.getEventsCommands();

        if (!events.containsKey(eventClass)) {
            return new ProfileProcessorResult(context.getTikTokEvent(), profile);
        }

        var commands = events.get(eventClass);
        var processedCommands = new ArrayList<String>();
        var warnings = new ArrayList<String>();
        for (var command : commands)
        {
            try
            {
                var processedCmd = processCommand(context, command);
                processedCommands.addAll(processedCmd);
            }
            catch (Exception e)
            {
                warnings.add(e.getMessage());
            }
        }

        System.out.println("Warnings: ");
        for(var msg : warnings)
        {
            System.out.println(" - "+msg);
        }
        System.out.println("");
        return new ProfileProcessorResult(context.getTikTokEvent(), profile, processedCommands);
    }

    private List<String> processCommand(ExecutorContext context, ProfileEventCommand command) {
       return command.getTextLineBlock().execute(context);
    }





}
