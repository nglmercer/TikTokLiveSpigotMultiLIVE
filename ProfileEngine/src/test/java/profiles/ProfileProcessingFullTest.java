package profiles;

import io.github.jwdeveloper.ff.core.logger.plugin.SimpleLogger;
import io.github.jwdeveloper.spigot.tiktok.profiles.code.ExecutorContext;
import io.github.jwdeveloper.spigot.tiktok.profiles.common.definitions.ConstDefinition;
import io.github.jwdeveloper.spigot.tiktok.profiles.common.definitions.MethodDefinition;
import io.github.jwdeveloper.spigot.tiktok.profiles.deserializer.ProfileDeserializer;
import io.github.jwdeveloper.spigot.tiktok.profiles.interpreter.ProfileInterpreter;
import io.github.jwdeveloper.spigot.tiktok.profiles.processor.ProfileProcessor;
import io.github.jwdeveloper.tiktok.events.TikTokEvent;
import io.github.jwdeveloper.tiktok.events.messages.TikTokCommentEvent;
import io.github.jwdeveloper.tiktok.events.messages.TikTokGiftMessageEvent;
import io.github.jwdeveloper.tiktok.events.messages.TikTokJoinEvent;
import io.github.jwdeveloper.tiktok.messages.User;
import io.github.jwdeveloper.tiktok.messages.WebcastGiftMessage;
import io.github.jwdeveloper.tiktok.messages.WebcastMemberMessage;
import io.github.jwdeveloper.tiktok.messages.WebcastRoomPinMessage;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class ProfileProcessingFullTest {

    public static String TEST_STRING = "hello world";

    public static String TESTING_METHOD(String input)
    {
        return input+" hello world";
    }

    public static void TESTING_METHOD_VOID(String input)
    {
       System.out.println("VOID METHOD RUN "+input);
    }

    @Test
    public void Process() throws InvalidConfigurationException {

        var input = getInput();
        var yaml = new YamlConfiguration();
        yaml.loadFromString(input);
        var deserializer = new ProfileDeserializer();
        var deserialization = deserializer.getProfilesModel(yaml);

        var interpreter = new ProfileInterpreter(new SimpleLogger());
        var profiles = interpreter.getProfiles(deserialization.getProfileModels());

        var processor = new ProfileProcessor();

        var profile = profiles.get(0);

        // var event = getJoinEvent();
        //var event = getGiftEvent();
        var event = getCommentEvent();
        var constsMap = deserialization
                .getConstances()
                .stream()
                .collect(Collectors.toMap(ConstDefinition::getName, i -> i));

        var methodsMap = deserialization
                .getMethodDefinitions()
                .stream()
                .collect(Collectors.toMap(MethodDefinition::getName, i -> i));

        methodsMap.putAll(getMethods());
        var context = new ExecutorContext(event,methodsMap, constsMap);
        var output = processor.processProfile(context, profile);
        var outputCommands = output.getProcessedCommands();

        for (var commands : outputCommands) {
            System.out.println("OUTPUT:" + commands);
        }
    }


    public Map<String, MethodDefinition> getMethods()
    {
        var method1 = new MethodDefinition("random",(e)->
        {
            var from = (int)e.get(0);
            var to = (int)e.get(1);
            var random = new Random();
            var randomValue = random.nextInt(to)+from;
            return randomValue;
        },false);

        var method2 = new MethodDefinition("say",(e)->
        {
            System.out.println(e.get(0));
            return null;
        },false);

        var method3 = new MethodDefinition("regex",(e)->
        {
            var pattern = Pattern.compile((String)e.get(0));
            return  pattern.matcher((String)e.get(1)).find();
        },false);

        var result = new HashMap<String,MethodDefinition>();
        result.put(method1.getName(),method1);
        result.put(method2.getName(),method2);
        result.put(method3.getName(),method3);
        return result;
    }

    public TikTokEvent getJoinEvent() {
        return new TikTokJoinEvent(WebcastMemberMessage
                .newBuilder()
                .setTotalViewers(2)
                .setUser(User.newBuilder().setNickname("Mark"))
                .build());
    }

    public TikTokEvent getCommentEvent() {
        return new TikTokCommentEvent(WebcastRoomPinMessage.RoomPinMessageData
                .newBuilder()
                .setComment("hi guys")
                .setSender(User.newBuilder().setNickname("Mark"))
                .build());
    }

    public TikTokEvent getGiftEvent() {
        return new TikTokGiftMessageEvent(WebcastGiftMessage
                .newBuilder()
                .setRepeatEnd(1)
                .setRepeatCount(2)
                .setComboCount(4)
                .setUser(User.newBuilder().setNickname("Mark"))
                .build());
    }

    private String getInput() {
        return """
                
                Array: [1,"123",3,"1213",5]
                NAME: "mark"
                POWER: 200
                sayHello: (a) => /say I will say ${a}
                ACCEPT_EVENT: true
                GIFT_NAME: event.gift.name
                
                profiles:
                 default:
                   onComment:
                    - ${sayHello("hello world")}
                    - ${repeat 1} /say siema
                    - ${if 1 == 2} /say siemka
                    - ${java.profiles.ProfileProcessingFullTest.TEST_STRING}
                    - ${java.profiles.ProfileProcessingFullTest.TESTING_METHOD("siema")}
                    - ${java.profiles.ProfileProcessingFullTest.TESTING_METHOD_VOID("siema 2")}
                    - ${random(0,20)}
                    - ${say("MOWIE Z KOMENDY")}
                    - ${if ACCEPT_EVENT then
                         "/say mowie TAK"
                        else
                          "/say mowie nie!"
                       }
                    - ${switch event.user.nickName
                         case "Mark" then "/say siema Marek"
                         case "adam" then  "/say siema Adam"
                        }
                    - ${repeat 2} ${
                         wait(2)
                         run("siema marek")
                         wait(5)
                         run("siema eniu tutaj dzwoni debilek")
                         "adasd"
                        }
                   onGiftMessage:
                     - ${if event.streakFinished then
                            "/title @p subtitle FINISHED "§7Thank you for ${giftName} ${GIFT_NAME}"
                         else
                            "/title @p subtitle COMBO "§7x ${event.comboCount}"
                         }
                      - ${wait(1)} ${repeat 2} /say my name
                   onJoin:
                    - ${if event.totalViewers == 123} /execute at @p run summon minecraft:sheep ~ ~ ~ {CustomName:"{\\"text\\":\\"${event.user.nickName}\\"}"}
                    - ${if event.user.nickName == "Mark"} /execute at @p run summon minecraft:sheep ~ ~ ~ {CustomName:"{\\"text\\":\\"${event.user.nickName}\\"}"}
                    - ${repeat(10,10)} /say Siema from ${event.user.nickName}  
                    - ${repeat event.totalViewers} /say repeting viewers
                """;
    }
}
