package io.github.jwdeveloper.spigot.tiktok.core.profile;

import io.github.jwdeveloper.ff.core.common.TextBuilder;
import io.github.jwdeveloper.ff.core.common.java.StringUtils;
import io.github.jwdeveloper.ff.core.injector.api.annotations.Injection;
import io.github.jwdeveloper.ff.core.logger.plugin.PluginLogger;
import io.github.jwdeveloper.spigot.tiktok.api.profiles.models.Profile;
import io.github.jwdeveloper.spigot.tiktok.profiles.ast.ProgramSYML;
import io.github.jwdeveloper.spigot.tiktok.profiles.common.exceptions.SymlEngineException;
import io.github.jwdeveloper.spigot.tiktok.profiles.parser.ParserFactory;
import io.github.jwdeveloper.tiktok.events.TikTokEvent;
import io.github.jwdeveloper.tiktok.events.messages.TikTokGiftMessageEvent;
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;


@Injection
public class ProfileLoader {
    private final PluginLogger pluginLogger;

    public ProfileLoader(PluginLogger pluginLogger) {
        this.pluginLogger = pluginLogger;
    }

    public Profile loadProfile(String name, String content) {
        var yaml = getYaml(content);
        var initBlockBuilder = new TextBuilder();
        var eventsBlocks = new HashMap<Class<?>, ProgramSYML>();
        for (var key : yaml.getKeys(false)) {
            var eventOptional = isTikTokEvent(key);
            if (eventOptional.isPresent()) {
                var codeString = getSectionContentString(key, yaml);
                var code = ParserFactory.createParser(codeString).parse();
                eventsBlocks.put(eventOptional.get(), code);
                continue;
            }

            var codeChunk = getSectionContentString(key, yaml);
            initBlockBuilder.text(key, "=", codeChunk).newLine();
        }

        var intiBlock = ParserFactory.createParser(initBlockBuilder.toString()).parse();
        return new Profile(name, intiBlock, eventsBlocks);
    }




    private YamlConfiguration getYaml(String content)
    {
        var yaml = new YamlConfiguration();
        try {
            content = preProcessContent(content);
            yaml.loadFromString(content);

        }
        catch (Exception e)
        {
            throw new SymlEngineException(e);
        }
        return yaml;
    }


    private String preProcessContent(String content)
    {
        String regex = "(^[a-zA-Z0-9_-]+:\\s*)";
        var pattern = Pattern.compile(regex, Pattern.MULTILINE);
        var matcher = pattern.matcher(content);
        StringBuffer result = new StringBuffer();
        while (matcher.find()) {
            String replacement = matcher.group(1) + "|\n   ";
            matcher.appendReplacement(result, replacement);
        }
        matcher.appendTail(result);
        return result.toString();
    }


    private Optional<Class<? extends TikTokEvent>> isTikTokEvent(String name)
    {
        if(!name.startsWith("on"))
        {
            return Optional.empty();
        }

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
            var clazz = (Class<? extends TikTokEvent>) Class.forName(fullName);
            return Optional.of(clazz);
        } catch (Exception e)
        {
            pluginLogger.warning("Event with name " + name + " is invalid try something different");
        }
        return Optional.empty();
    }


    private String getSectionContentString(String name, YamlConfiguration yamlConfiguration) {
        var value = yamlConfiguration.get(name);

        if (value == null) {
            return StringUtils.EMPTY;
        }
        if (value instanceof List list) {
            var sb = new TextBuilder();
            for (var v : list) {
                sb.textNewLine(v);
            }
            return sb.toString();
        }
        return (String) value;
    }


}
