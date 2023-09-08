package io.github.jwdeveloper.spigot.tiktok.core.profiles.deserializer;

import io.github.jwdeveloper.ff.core.injector.api.annotations.Injection;
import io.github.jwdeveloper.ff.core.injector.api.enums.LifeTime;
import io.github.jwdeveloper.spigot.tiktok.core.profiles.deserializer.models.ProfileElementModel;
import io.github.jwdeveloper.spigot.tiktok.core.profiles.deserializer.models.ProfileModel;
import org.bukkit.configuration.ConfigurationSection;

import java.util.ArrayList;
import java.util.List;

@Injection(lifeTime = LifeTime.TRANSIENT)
public class ProfileDeserializer {
    public List<ProfileModel> getProfilesModel(ConfigurationSection configuration) {

        var profiles = new ArrayList<ProfileModel>();
        var keys = configuration.getKeys(false);
        for (var key : keys) {
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
        return profiles;
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
