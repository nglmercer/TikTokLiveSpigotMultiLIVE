package io.github.jwdeveloper.spigot.tiktok.core.profiles;


import common.EvaluatorAssert;
import common.ProfileTestBase;
import io.github.jwdeveloper.ff.core.files.FileUtility;
import io.github.jwdeveloper.ff.core.logger.plugin.SimpleLogger;
import io.github.jwdeveloper.spigot.tiktok.core.profile.ProfileLoader;
import io.github.jwdeveloper.spigot.tiktok.profiles.evaluator.EvaluatorFactory;
import org.bukkit.configuration.InvalidConfigurationException;
import org.junit.Test;

import java.io.IOException;

public class GeneralTest extends ProfileTestBase {

    @Test
    public void getFromFile() throws IOException, InvalidConfigurationException {
        var path = "D:\\Git\\TikTokLiveSpigot\\TikTokLiveSpigotCore\\src\\main\\resources\\default.sy";
        var content = FileUtility.loadFileContent(path);

        var loader = new ProfileLoader(new SimpleLogger("Loader: "));

        var profiles = loader.loadProfile("default", content);
        var evaluator = EvaluatorFactory.create();

        evaluator.evaluate(profiles.getIntiBlock());
        for (var eventBlocks : profiles.getEventsBlocks().entrySet()) {
            evaluator.evaluate(eventBlocks.getValue());
        }
        evaluator.print();
    }


    @Test
    public void generalTest() {
        var input = """
                name: 'jacek'
                getName:  (input) => "string connected "+input
                getValue: () => 11 + 11               
                number: 123
                                
                onJoin: 
                   /say someone join! ${name}
                onComment:
                   /say someone just comment! ${getName("adam")}
                onFollow:
                   /say someone just follow! ${getValue()} ${1+1}
                """;


        var result = runProfile(input);

        EvaluatorAssert.assertion(result)
                .shouldHasVariable("name", "jacek")
                .shouldHasVariable("number", 123)
                .shouldHasOutput("say someone join! jacek")
                .shouldHasOutput("say someone just comment! string connected adam")
                .shouldHasOutput("say someone just follow! 22 2");

    }


    @Test
    public void test2()
    {
        var input = """
                spawn: (mobName) =>  /execute at @p run summon minecraft:${mobName} ~ ~ ~ {CustomName:'"${customName}"',CustomNameVisible:1}
                                
             
                onGift:\s
                     /title @p clear
                     /title @p title "§2${event.sender.nickName}"
                     /title @p subtitle "§7Thank you for ${event.comboCount} §2${event.gift.name}"
                     if event.streakFinished is true
                     then repeat event.comboCount spawn("creeper")
                     \s
             
                """;

        var result = runProfile(input).print();

    }





}