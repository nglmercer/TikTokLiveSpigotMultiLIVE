package io.github.jwdeveloper.spigot.tiktok.core.profiles.interpreter;

import io.github.jwdeveloper.ff.core.common.java.StringUtils;
import io.github.jwdeveloper.ff.core.injector.api.annotations.Injection;
import io.github.jwdeveloper.ff.core.injector.api.enums.LifeTime;
import io.github.jwdeveloper.spigot.tiktok.core.profiles.deserializer.models.ProfileElementModel;
import io.github.jwdeveloper.spigot.tiktok.core.profiles.deserializer.models.ProfileModel;
import io.github.jwdeveloper.spigot.tiktok.api.profiles.models.ParameterType;
import io.github.jwdeveloper.spigot.tiktok.api.profiles.models.Profile;
import io.github.jwdeveloper.spigot.tiktok.api.profiles.models.ProfileCommandParameter;
import io.github.jwdeveloper.spigot.tiktok.api.profiles.models.ProfileEventCommand;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

@Injection(lifeTime = LifeTime.TRANSIENT)
public class ProfileInterpreter {
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
            var profileEventCommand = getSingleCommand(classType, command);
            result.add(profileEventCommand);
        }
        return result;
    }

    private ProfileEventCommand getSingleCommand(Class<?> clazz, String stringCommand) {


        var parametersString = extractBetweenDelimiters(stringCommand);

        var parameters = new ArrayList<ProfileCommandParameter>();
        var index = 0;
        for (var parameterString : parametersString) {
            var parameter = new ProfileCommandParameter();

            var type = ParameterType.Unknown;
            if (parameterString.startsWith("tiktok")) {
                type = ParameterType.TikTok;
            }
            if (parameterString.startsWith("event")) {
                type = ParameterType.Event;
            }

            parameter.setType(type);
            parameter.setIndex(index);
            parameter.setPath(parameterString);

            parameters.add(parameter);
            index++;
        }


        var formattedCommand = replaceWithIncreasingNumbers(stringCommand);
        var command = new ProfileEventCommand();
        command.setCommandString(formattedCommand);
        command.setEventClass(clazz);
        command.setParameters(parameters);

        return command;
    }

    private List<String> extractBetweenDelimiters(String text) {
        var matches = new ArrayList<String>();

        var pattern = Pattern.compile("\\$\\{([^}]*)\\}");
        var matcher = pattern.matcher(text);

        while (matcher.find()) {
            matches.add(matcher.group(1));
        }

        return matches.stream().toList();
    }

    private String replaceWithIncreasingNumbers(String text) {
        Pattern pattern = Pattern.compile("\\$\\{[^}]*\\}");
        return pattern.matcher(text).replaceAll("%s");

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
