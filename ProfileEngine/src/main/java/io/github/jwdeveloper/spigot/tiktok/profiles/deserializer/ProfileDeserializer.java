package io.github.jwdeveloper.spigot.tiktok.profiles.deserializer;

import io.github.jwdeveloper.spigot.tiktok.profiles.common.definitions.ConstDefinition;
import io.github.jwdeveloper.spigot.tiktok.profiles.deserializer.models.ProfileElementModel;
import io.github.jwdeveloper.spigot.tiktok.profiles.deserializer.models.ProfileModel;
import io.github.jwdeveloper.spigot.tiktok.profiles.deserializer.models.ProfilesDeserialization;
import org.bukkit.configuration.ConfigurationSection;

import java.util.ArrayList;
import java.util.List;


public class ProfileDeserializer
{

    private final String CONSTS = "const";

    public List<ConstDefinition> getConstances(ConfigurationSection configuration)
    {
        if(!configuration.isConfigurationSection(CONSTS))
        {
            return List.of();
        }

        var result = new ArrayList<ConstDefinition>();
        var constsSections = configuration.getConfigurationSection(CONSTS);
        var keys = constsSections.getKeys(false);
        for(var key : keys)
        {
            var value = constsSections.get(key);
            result.add(new ConstDefinition(key,value));
        }
        return result;
    }
    public ProfilesDeserialization getProfilesModel(ConfigurationSection configuration) {

        var profiles = new ArrayList<ProfileModel>();
        var keys = configuration.getKeys(false);
        for (var key : keys)
        {
            if(key.equals(CONSTS))
            {
                continue;
            }
            if (!configuration.isConfigurationSection(key)) {
                continue;
            }

            var section = configuration.getConfigurationSection(key);
            if(section == null)
            {
                continue;
            }

            var profile = getSingleProfile(key, section);
            profiles.add(profile);
        }
        var constances = getConstances(configuration);
        return new ProfilesDeserialization(profiles, constances);
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

    private ProfileElementModel getProfileElement(String elementName, ConfigurationSection configuration)
    {

        var commands = configuration.getStringList(elementName);

        var profileElementModel = new ProfileElementModel();

        profileElementModel.setEventName(elementName);
        profileElementModel.setCommands(commands);

        return profileElementModel;
    }
}
