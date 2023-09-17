package io.github.jwdeveloper.spigot.tiktok.profiles.deserializer;

import io.github.jwdeveloper.spigot.tiktok.profiles.common.definitions.ConstDefinition;
import io.github.jwdeveloper.spigot.tiktok.profiles.common.definitions.MethodDefinition;
import io.github.jwdeveloper.spigot.tiktok.profiles.deserializer.models.ProfileElementModel;
import io.github.jwdeveloper.spigot.tiktok.profiles.deserializer.models.ProfileModel;
import io.github.jwdeveloper.spigot.tiktok.profiles.deserializer.models.ProfilesDeserialization;
import io.github.jwdeveloper.spigot.tiktok.profiles.interpreter.Tokenizer;
import io.github.jwdeveloper.spigot.tiktok.profiles.interpreter.code.functions.FunctionBlockInterpreter;
import io.github.jwdeveloper.spigot.tiktok.profiles.interpreter.code.literals.LiteralInterpreter;
import io.github.jwdeveloper.spigot.tiktok.profiles.interpreter.code.CodeLineInterpeter;
import org.bukkit.configuration.ConfigurationSection;

import java.util.ArrayList;
import java.util.List;


public class ProfileDeserializer {

    private String PROFILES = "profiles";
    private String INCLUDES = "include";

    public ProfilesDeserialization getProfilesModel(ConfigurationSection configuration) {

        var profiles = new ArrayList<ProfileModel>();
        var includes = new ArrayList<String>();
        var consts = new ArrayList<ConstDefinition>();
        var methods = new ArrayList<MethodDefinition>();
        var keys = configuration.getKeys(false);


        for (var key : keys) {
            if (!configuration.isConfigurationSection(key)) {
                var value = configuration.get(key);
                if (value instanceof String va && va.contains("=>")) {

                    methods.add(getFunctions(key, va));
                    continue;
                } else {
                    consts.add(new ConstDefinition(key, value));
                }
            }
            var section = configuration.getConfigurationSection(key);
            if (key.equals(PROFILES)) {
                profiles.addAll(handleProfiles(section));
            }
            if (key.equals(INCLUDES)) {
                var includeElements = configuration.getStringList(key);
                includes.addAll(includeElements);
            }

        }
        return new ProfilesDeserialization(profiles, methods, consts);
    }

    private List<ProfileModel> handleProfiles(ConfigurationSection configurationSection) {
        var reuslt = new ArrayList<ProfileModel>();
        for (var key : configurationSection.getKeys(false)) {
            var section = configurationSection.getConfigurationSection(key);
            var profiel = getSingleProfile(key, section);
            reuslt.add(profiel);
        }
        return reuslt;
    }


    private MethodDefinition getFunctions(String name, String content) {
        var tokenizer = new Tokenizer(content);
        var textLine = new CodeLineInterpeter();
        var literal = new LiteralInterpreter(tokenizer, textLine);
        var interpreter = new FunctionBlockInterpreter(literal, tokenizer, textLine, content, name);
        var definition = interpreter.getMethodDefinition();
        return definition;
    }

    private ProfileModel getSingleProfile(String profileName, ConfigurationSection configuration) {
        var elements = new ArrayList<ProfileElementModel>();
        var keys = configuration.getKeys(false);
        for (var key : keys) {
            if (configuration.isConfigurationSection(key)) {
                continue;
            }

            var event = getProfileElement(key, configuration);
            elements.add(event);
        }

        var profile = new ProfileModel();
        profile.setName(profileName);
        profile.setElements(elements);
        return profile;
    }

    private ProfileElementModel getProfileElement(String elementName, ConfigurationSection configuration) {

        var commands = configuration.getStringList(elementName);

        var profileElementModel = new ProfileElementModel();

        profileElementModel.setEventName(elementName);
        profileElementModel.setCommands(commands);

        return profileElementModel;
    }
}
