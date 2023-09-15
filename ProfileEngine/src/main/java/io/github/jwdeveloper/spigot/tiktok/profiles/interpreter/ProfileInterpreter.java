package io.github.jwdeveloper.spigot.tiktok.profiles.interpreter;

import io.github.jwdeveloper.ff.core.common.java.StringUtils;
import io.github.jwdeveloper.spigot.tiktok.profiles.common.Profile;
import io.github.jwdeveloper.spigot.tiktok.profiles.common.ProfileEventCommand;
import io.github.jwdeveloper.spigot.tiktok.profiles.deserializer.models.ProfileElementModel;
import io.github.jwdeveloper.spigot.tiktok.profiles.deserializer.models.ProfileModel;
import io.github.jwdeveloper.spigot.tiktok.profiles.interpreter.code.literals.TextLineInterpeter;

import java.util.*;


public class ProfileInterpreter
{
     TextLineInterpeter stringLineInterpreter = new TextLineInterpeter();

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
        var classType = getEventClass(elementModels.getEventName());
        for (var command : elementModels.getCommands()) {
            var codeBlock = stringLineInterpreter.getCodeBlock(command);
            var commandEvent =  new ProfileEventCommand(classType, codeBlock);
            result.add(commandEvent);
        }
        return result;
    }

    private Class<?> getEventClass(String name) {
        var classBase = "io.github.jwdeveloper.tiktok.events.messages.";
        var eventName = name.replace("On", StringUtils.EMPTY);
        eventName = name.replace("on", StringUtils.EMPTY);
        eventName = "TikTok" + eventName + "Event";
        var fullName = classBase + eventName;
        try {
            return Class.forName(fullName);
        } catch (Exception e) {
            throw new RuntimeException("Class not found for event name: " + name, e);
        }

    }

}
