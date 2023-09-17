package profiles.interpreter;

import io.github.jwdeveloper.spigot.tiktok.profiles.code.ExecutorContext;
import io.github.jwdeveloper.spigot.tiktok.profiles.code.blocks.ExpressionBlock;
import io.github.jwdeveloper.spigot.tiktok.profiles.interpreter.Tokenizer;
import io.github.jwdeveloper.spigot.tiktok.profiles.interpreter.code.BlockInterpreter;
import io.github.jwdeveloper.spigot.tiktok.profiles.interpreter.code.statements.IfInterpreter;
import io.github.jwdeveloper.spigot.tiktok.profiles.interpreter.code.literals.LiteralInterpreter;
import io.github.jwdeveloper.spigot.tiktok.profiles.interpreter.code.CodeLineInterpeter;
import org.junit.Test;

public class IfInterpreterTest {
    public ExpressionBlock getIfBlock(String code) {
        var tokenizer = new Tokenizer(code);
        return new IfInterpreter(tokenizer, new BlockInterpreter(tokenizer), new LiteralInterpreter(tokenizer, new CodeLineInterpeter())).getCodeBlock();
    }


    @Test
    public void shouldIterpreteIf() {
        getIfBlock("if 1 != 1");
        getIfBlock("if 1 == 1");
        getIfBlock("if 1 > 1");
        getIfBlock("if 1 >= 1");
        getIfBlock("if 1 < 1");
        getIfBlock("if 1 <= 1");
        getIfBlock("if 1 is 1");
        getIfBlock("if 1 not 1");
        getIfBlock("if 1 and 1");
        getIfBlock("if 1 or 1");
    }

    @Test
    public void shouldInterpreteComplexIf() {
        var complexBlock =  getIfBlock("if name is mark and isValid is true or adam is false");
    }

    @Test
    public void shouldInterpreteWithBody() {
        var complexBlock =  getIfBlock("if 1 is 1 then \"/say true ${'event.text'}\" else 'sad'");
        var result = complexBlock.execute(new ExecutorContext(null ));
        System.out.println(result);
    }


}
