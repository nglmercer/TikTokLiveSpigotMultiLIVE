package profiles.interpreter;

import io.github.jwdeveloper.spigot.tiktok.profiles.code.blocks.switch_blocks.SwitchBlock;
import io.github.jwdeveloper.spigot.tiktok.profiles.interpreter.Tokenizer;
import io.github.jwdeveloper.spigot.tiktok.profiles.interpreter.code.literals.LiteralInterpreter;
import io.github.jwdeveloper.spigot.tiktok.profiles.interpreter.code.statements.SwitchInterpeter;
import io.github.jwdeveloper.spigot.tiktok.profiles.interpreter.code.CodeLineInterpeter;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.assertThrows;

public class SwitchInterpreterTest
{


    public static SwitchBlock getSwitch(String code) {
        var tokenizer = new Tokenizer(code);
        var methodInterpeter = new SwitchInterpeter( tokenizer,new LiteralInterpreter(tokenizer, new CodeLineInterpeter()));
        return methodInterpeter.getCodeBlock();
    }


    @Test
    public void shouldSwitch() {
        var method1 = getSwitch("""
                switch number
                   case 1 then "hello"
                   case 2 then  "world"
                   default then "war is coming"
                """);


        Assert.assertEquals(3, method1.getCases().size());
    }


}
