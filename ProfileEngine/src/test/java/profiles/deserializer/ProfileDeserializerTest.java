package profiles.deserializer;

import io.github.jwdeveloper.spigot.tiktok.profiles.deserializer.ProfileDeserializer;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.junit.Assert;
import org.junit.Test;

public class ProfileDeserializerTest
{

    @Test
    public void shouldDeserialize() throws InvalidConfigurationException {

        var interpeter = new ProfileDeserializer();
        var yaml = new YamlConfiguration();
        yaml.loadFromString(getInput());

        var result =  interpeter.getProfilesModel(yaml);
        Assert.assertEquals(4,result.getConstances().size());
        Assert.assertEquals(1,result.getMethodDefinitions().size());
        Assert.assertEquals(2,result.getProfileModels().size());
        Assert.assertEquals(2,result.getProfileModels().get(0).getElements().size());
    }

    private String getInput() {
        return """
                
                NAME: "mark"
                POWER: 200
                ACCEPT_EVENT: true
                GIFT_NAME: event.gift.name
                sayHello: (a) => /say I will say ${a}
                
                profiles:
                 default:
                   onComment:
                    - ${sayHello("hello world")}
                    - ${repeat 1} /say siema
                    - ${if 1 == 2} /say siemka
                   onGiftMessage:
                    - ${wait(1)} ${repeat 2} /say my name
                 test:
                   onEvent:
                     - /say connected
                """;
    }
}