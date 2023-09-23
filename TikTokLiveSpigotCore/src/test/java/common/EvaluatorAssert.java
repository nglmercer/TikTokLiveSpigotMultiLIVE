package common;

import io.github.jwdeveloper.spigot.tiktok.profiles.evaluator.Evaliator;
import org.junit.Assert;

public class EvaluatorAssert
{
    private final Evaliator evaliator;

    public EvaluatorAssert(Evaliator evaliator) {
        this.evaliator = evaliator;
    }

    public static EvaluatorAssert assertion(Evaliator evaliator)
    {
        return new EvaluatorAssert(evaliator);
    }

    public EvaluatorAssert shouldHasVariable(String name)
    {
        Assert.assertTrue(evaliator.getContext().hasVariable(name));
        return this;
    }

    public EvaluatorAssert shouldHasVariable(String name, Object value)
    {
        shouldHasVariable(name);
        Assert.assertEquals(evaliator.getContext().getVariable(name),value);
        return this;
    }

    public EvaluatorAssert shouldHasOutput(String value, int index)
    {
       var output =  evaliator.getContext().getOutput();
       Assert.assertEquals(value, output.get(index));
        return this;
    }


    public EvaluatorAssert shouldHasOutput(String name)
    {
        var output =  evaliator.getContext().getOutput();
        Assert.assertTrue(output.contains(name));
        return this;
    }
}
