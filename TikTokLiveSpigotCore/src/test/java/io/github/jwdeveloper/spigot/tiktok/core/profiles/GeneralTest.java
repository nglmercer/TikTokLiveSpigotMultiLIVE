package io.github.jwdeveloper.spigot.tiktok.core.profiles;


import common.EvaluatorAssert;
import common.ProfileTestBase;
import io.github.jwdeveloper.ff.core.files.FileUtility;
import io.github.jwdeveloper.ff.core.logger.plugin.SimpleLogger;
import io.github.jwdeveloper.spigot.tiktok.profiles.evaluator.EvaluatorFactory;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.junit.Test;

import java.io.IOException;

public class GeneralTest extends ProfileTestBase {

    @Test
    public void getFromFile() throws IOException, InvalidConfigurationException {
        var path = "D:\\MC\\spigot_1.19.4\\plugins\\TikTokLiveSpigot\\test.syml";
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





}