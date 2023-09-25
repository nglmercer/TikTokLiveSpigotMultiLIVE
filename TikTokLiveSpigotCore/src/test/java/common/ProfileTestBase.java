package common;

import io.github.jwdeveloper.ff.core.logger.plugin.SimpleLogger;
import io.github.jwdeveloper.spigot.tiktok.core.profile.ProfileLoader;
import io.github.jwdeveloper.spigot.tiktok.profiles.evaluator.Evaliator;
import io.github.jwdeveloper.spigot.tiktok.profiles.evaluator.EvaluatorFactory;

import java.util.function.Consumer;

public class ProfileTestBase
{
    public Evaliator runProfile(String content)
    {
        var loader = new ProfileLoader(new SimpleLogger("Loader: "));
        var profiles = loader.loadProfile("default", content);

        var evaluator = EvaluatorFactory.create();
        evaluator.evaluate(profiles.getIntiBlock());
        for (var eventBlocks : profiles.getEventsBlocks().entrySet()) {
            evaluator.evaluate(eventBlocks.getValue());
        }
        evaluator.print();
        return evaluator;
    }

    public Evaliator runProfile(String content, Consumer<Evaliator> before)
    {
        var loader = new ProfileLoader(new SimpleLogger("Loader: "));
        var profiles = loader.loadProfile("default", content);

        var evaluator = EvaluatorFactory.create();
        before.accept(evaluator);
        evaluator.evaluate(profiles.getIntiBlock());
        for (var eventBlocks : profiles.getEventsBlocks().entrySet()) {
            evaluator.evaluate(eventBlocks.getValue());
        }
        evaluator.print();
        return evaluator;
    }
}
