package profiles.interpreter;

import io.github.jwdeveloper.spigot.tiktok.profiles.code.blocks.functions.FunctionExpression;
import io.github.jwdeveloper.spigot.tiktok.profiles.common.exceptions.ProfileEngineException;
import io.github.jwdeveloper.spigot.tiktok.profiles.interpreter.Tokenizer;
import io.github.jwdeveloper.spigot.tiktok.profiles.interpreter.code.literals.LiteralInterpreter;
import io.github.jwdeveloper.spigot.tiktok.profiles.interpreter.code.functions.FunctionCallInterpreter;
import io.github.jwdeveloper.spigot.tiktok.profiles.interpreter.code.CodeLineInterpeter;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.assertThrows;

public class MethodInterpeterTest {


    public static FunctionExpression getMethod(String code) {
        var tokenizer = new Tokenizer(code);
        var methodInterpeter = new FunctionCallInterpreter(new LiteralInterpreter(tokenizer, new CodeLineInterpeter()), tokenizer);
        return methodInterpeter.getCodeBlock();
    }


    @Test
    public void shouldCreateMethod() {
        var method1 = (FunctionExpression)getMethod("random arg1 arg2");


        Assert.assertEquals("random", method1.getContent());
        Assert.assertEquals(2, method1.getParameters().size());
    }

    @Test
    public void shouldNotCreateMethod() {
        assertThrows(ProfileEngineException.class, () ->
        {
            getMethod("1 arg1 arg2");
        });
        assertThrows(ProfileEngineException.class, () ->
        {
            getMethod("event arg1 arg2");
        });
        assertThrows(ProfileEngineException.class, () ->
        {
            getMethod("true arg1 arg2");
        });
        assertThrows(ProfileEngineException.class, () ->
        {
            getMethod("'my name is jeff' arg1 arg2");
        });
    }

    @Test
    public void shouldNotCreateWith0Parameters() {
        assertThrows(ProfileEngineException.class, () ->
        {
            getMethod("random");
        });
    }
}
