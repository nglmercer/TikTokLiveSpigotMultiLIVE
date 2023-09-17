package io.github.jwdeveloper.spigot.tiktok.profiles.interpreter;

import io.github.jwdeveloper.ff.core.common.java.StringUtils;
import io.github.jwdeveloper.ff.core.logger.plugin.PluginLogger;
import io.github.jwdeveloper.spigot.tiktok.profiles.common.Profile;
import io.github.jwdeveloper.spigot.tiktok.profiles.common.ProfileEventCommand;
import io.github.jwdeveloper.spigot.tiktok.profiles.deserializer.models.ProfileElementModel;
import io.github.jwdeveloper.spigot.tiktok.profiles.deserializer.models.ProfileModel;
import io.github.jwdeveloper.spigot.tiktok.profiles.interpreter.code.CodeLineInterpeter;
import io.github.jwdeveloper.tiktok.events.TikTokEvent;
import io.github.jwdeveloper.tiktok.events.messages.TikTokGiftMessageEvent;

import java.util.*;


public class ProfileInterpreter {
    CodeLineInterpeter stringLineInterpreter = new CodeLineInterpeter();

    PluginLogger pluginLogger;
    public ProfileInterpreter(PluginLogger pluginLogger) {
        this.pluginLogger = pluginLogger;
    }

    public List<Profile> getProfiles(List<ProfileModel> models) {
        var result = new ArrayList<Profile>();
        for (var model : models) {
            var profile = getSingleProfile(model);
            result.add(profile);
        }
        return result;
    }

    private Profile getSingleProfile(ProfileModel profileModel) {
        var profile = new Profile();
        profile.setName(profileModel.getName());
        profile.setEventsCommands(getEventCommands(profileModel.getElements()));
        return profile;
    }

    private Map<Class<?>, List<ProfileEventCommand>> getEventCommands(List<ProfileElementModel> elementModels) {
        var result = new HashMap<Class<?>, List<ProfileEventCommand>>();
        for (var element : elementModels) {
            var profileCommands = getEventCommands(element);
            if (profileCommands.isEmpty()) {
                continue;
            }
            var eventClass = profileCommands.get(0).getEventClass();
            result.put(eventClass, profileCommands);
        }
        return result;
    }

    private List<ProfileEventCommand> getEventCommands(ProfileElementModel elementModels) {
        var result = new ArrayList<ProfileEventCommand>();
        var optional = getEventClass(elementModels.getEventName());

        if(optional.isEmpty())
        {
            return result;
        }

        var classType = optional.get();

        for (var command : elementModels.getCommands()) {
            var codeBlock = stringLineInterpreter.getCodeBlock(command);
            result.add(new ProfileEventCommand(classType, codeBlock));
        }
        return result;
    }

    private Optional<Class<?>> getEventClass(String name) {
        if (name.equals("onGift")) {
            return Optional.of(TikTokGiftMessageEvent.class);
        }
        if (name.equals("onEvent")) {
            return Optional.of(TikTokEvent.class);
        }
        var classBase = "io.github.jwdeveloper.tiktok.events.messages.";
        var eventName = name.replace("On", StringUtils.EMPTY);
        eventName = name.replace("on", StringUtils.EMPTY);
        eventName = "TikTok" + eventName + "Event";
        var fullName = classBase + eventName;
        try {
            return Optional.of(Class.forName(fullName));
        } catch (Exception e) {
            pluginLogger.warning("Event with name " + name + " is invalid try something different");
        }
        return Optional.empty();
    }

}
