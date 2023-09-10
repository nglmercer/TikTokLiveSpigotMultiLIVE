package io.github.jwdeveloper.spigot.tiktok.core.profiles.interpreter;

import io.github.jwdeveloper.ff.core.common.java.StringUtils;
import io.github.jwdeveloper.ff.core.injector.api.annotations.Injection;
import io.github.jwdeveloper.ff.core.injector.api.enums.LifeTime;
import io.github.jwdeveloper.spigot.tiktok.api.profiles.models.CodeBlock;
import io.github.jwdeveloper.spigot.tiktok.api.profiles.models.Profile;
import io.github.jwdeveloper.spigot.tiktok.api.profiles.models.ProfileEventCommand;
import io.github.jwdeveloper.spigot.tiktok.core.profiles.deserializer.models.ProfileElementModel;
import io.github.jwdeveloper.spigot.tiktok.core.profiles.deserializer.models.ProfileModel;
import io.github.jwdeveloper.spigot.tiktok.core.profiles.interpreter.blocks.Expression;
import io.github.jwdeveloper.spigot.tiktok.core.profiles.interpreter.blocks.IfExpressionBlock;
import io.github.jwdeveloper.spigot.tiktok.core.profiles.interpreter.blocks.PrimitiveExpression;
import io.github.jwdeveloper.spigot.tiktok.core.profiles.interpreter.blocks.RepeatBlock;
import io.github.jwdeveloper.spigot.tiktok.core.profiles.interpreter.blocks.switchh.CaseSwitchBlock;
import io.github.jwdeveloper.spigot.tiktok.core.profiles.interpreter.blocks.switchh.SwitchExpressionBlock;
import io.github.jwdeveloper.tiktok.events.TikTokEvent;

import java.util.*;
import java.util.regex.Matcher;
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


        var codeContent = extractBetweenDelimiters(stringCommand);

        var blocks = new ArrayList<CodeBlock>();
        var index = 0;
        for (var content : codeContent) {
            var block = getCodeBlock(content);
            block.setBlockIndex(index);
            blocks.add(block);
            index++;
        }


        var formattedCommand = replaceWithIncreasingNumbers(stringCommand);
        var command = new ProfileEventCommand();
        command.setCommandString(formattedCommand);
        command.setEventClass(clazz);
        command.setCodeBlocks(blocks);

        return command;
    }


    private CodeBlock getCodeBlock(String content) {
        if (content.startsWith("if")) {
            return getIfExpression(content);
        }
        if(content.startsWith("repeat"))
        {
            return getRepeatBlock(content);
        }
        return getPrimitive(content);
    }

    private Expression getPrimitive(String content)
    {
        if(content.startsWith("event"))
        {
            return new PrimitiveExpression(content, TikTokEvent.class);
        }

        if(Objects.equals(content, "true") || Objects.equals(content, "false"))
        {
            return new PrimitiveExpression(content, boolean.class);
        }
        var regex = "-?\\d+(\\.\\d+)?";
        var pattern = Pattern.compile(regex);
        var matcher = pattern.matcher(content);
        if(matcher.find())
        {
            return new PrimitiveExpression(content, float.class);
        }

        return new PrimitiveExpression(content, String.class);
    }

    private CodeBlock getRepeatBlock(String content)
    {
        String regex = "\\brepeat\\s+(\\d+)\\b";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(content);

        if (!matcher.find())
        {
            return new RepeatBlock(1);
        }

        String numberStr = matcher.group(1);
        var value =  Integer.parseInt(numberStr);
        return new RepeatBlock(value);
    }

    private IfExpressionBlock getIfExpression(String content) {
        var regex = "if\\s+(.+)\\s+(==|>=|<=|>|<)\\s+(.+)";
        var pattern = Pattern.compile(regex);

        var matcher = pattern.matcher(content);

        if (!matcher.find()) {
            throw new RuntimeException("Bad If implementation: " + content);
        }
        var valueLeft = matcher.group(1);
        var operator = matcher.group(2);
        var valueRight = matcher.group(3);
        return new IfExpressionBlock(getPrimitive(valueLeft), operator, getPrimitive(valueRight));
    }

    private SwitchExpressionBlock getSwitchExpression(String content)
    {
        String regex = "switch\\s+([^\\s]+)\\s+(.+?)(?=(?:\\s*case\\s+\"[^\"]+\")|\\s*default|$)";
        var pattern = Pattern.compile(regex);

        var matcher = pattern.matcher(content);

        if (!matcher.find()) {
            throw new RuntimeException("Bad Switch implementation: " + content);
        }

        var inputValue = matcher.group(1);
        var switchBodyValue = matcher.group(2);
        return new SwitchExpressionBlock(getPrimitive(inputValue), getSwitchCaseses(switchBodyValue));
    }


    private List<CaseSwitchBlock> getSwitchCaseses(String content)
    {
        var result = new ArrayList<CaseSwitchBlock>();
        var regex = "case\\s+\"(.+)\"\\s+([^]+?)(?=\\bcase\\b|\\bdefault\\b|$)";

        // Compile the regex pattern
        var pattern = Pattern.compile(regex, Pattern.DOTALL);

        // Create a matcher for the input
        var matcher = pattern.matcher(content);

        while (matcher.find()) {
            var caseValue = matcher.group(1).trim();
            var caseCommand = matcher.group(2).trim();
            result.add(new CaseSwitchBlock(false, caseValue,getCodeBlock(caseCommand)));
        }
        return result;
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
