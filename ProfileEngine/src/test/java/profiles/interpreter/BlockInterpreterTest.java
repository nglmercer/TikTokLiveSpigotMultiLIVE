package profiles.interpreter;

import io.github.jwdeveloper.spigot.tiktok.profiles.code.CodeBlock;
import io.github.jwdeveloper.spigot.tiktok.profiles.interpreter.Tokenizer;
import io.github.jwdeveloper.spigot.tiktok.profiles.interpreter.code.BlockInterpreter;
import org.junit.Test;

public class BlockInterpreterTest
{
    public CodeBlock getBlock(String code) {
        var tokenizer = new Tokenizer(code);
        return new BlockInterpreter(tokenizer).getCodeBlock();
    }



    @Test
    public void shouldParseCodeBlock()
    {

        var block = getBlock("""
                if name == 'jack' and  age == 12 then random 1 2 else notthing
                """);
    }

}
