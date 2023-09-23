package profiles.evaluator;

import io.github.jwdeveloper.spigot.tiktok.profiles.evaluator.Evaliator;
import io.github.jwdeveloper.spigot.tiktok.profiles.evaluator.EvaluatorFactory;

public class EvaluatorTestBase {


    public Evaliator createEvaluatedProgram(String input) {
        var evaluator = createEvaluator();
        evaluator.evaluate(input);
        return evaluator;
    }


    public Evaliator createEvaluator() {
        return EvaluatorFactory.create();
    }

}
