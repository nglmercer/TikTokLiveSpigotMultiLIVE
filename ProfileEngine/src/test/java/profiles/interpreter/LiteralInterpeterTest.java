package profiles.interpreter;

import io.github.jwdeveloper.spigot.tiktok.profiles.code.blocks.literals.LiteralBlock;
import io.github.jwdeveloper.spigot.tiktok.profiles.interpreter.Tokenizer;
import io.github.jwdeveloper.spigot.tiktok.profiles.interpreter.code.literals.LiteralInterpreter;
import io.github.jwdeveloper.spigot.tiktok.profiles.interpreter.code.CodeLineInterpeter;
import io.github.jwdeveloper.tiktok.events.TikTokEvent;
import org.junit.Assert;
import org.junit.Test;

public class LiteralInterpeterTest {


    public LiteralBlock getLiteral(String code)
    {
        var tokenizer = new Tokenizer(code);
        return new LiteralInterpreter(tokenizer, new CodeLineInterpeter()).getCodeBlock();
    }
    
    
    @Test
    public void shouldFindStrings() {
        var block1 = getLiteral("\"My name is jeff\"");
        var block2 = getLiteral("\'My name is jeff2\'");

        shouldBe(block1, String.class);
        shouldBe(block2, String.class);
    }

    @Test
    public void shouldFindNumbers() {
        var block1 = getLiteral("1");
        var block2 = getLiteral("2.234");
        var block3 = getLiteral(".123");
        var block4 = getLiteral("01");

        shouldBe(block1, Number.class);
        shouldBe(block2, Number.class);
        shouldBe(block3, Number.class);
        shouldBe(block4, Number.class);
    }

    @Test
    public void shouldFindBool() {
        var block1 = getLiteral("true");
        var block2 = getLiteral("false");
        shouldBe(block1, Boolean.class);
        shouldBe(block2, Boolean.class);
    }

    @Test
    public void shouldBeTikTokEvent() {
        var block1 = getLiteral("event");
        var block2 = getLiteral("event.");
        var block3 = getLiteral("event.one");
        shouldBe(block1, TikTokEvent.class);
        shouldBe(block2, TikTokEvent.class);
        shouldBe(block3, TikTokEvent.class);
    }

    @Test
    public void shouldBeObject() {
        var block1 = getLiteral("NAME");
        var block2 = getLiteral("value");
        var block3 = getLiteral("value.name.event");
        shouldBe(block1, Object.class);
        shouldBe(block2, Object.class);
        shouldBe(block3, Object.class);
    }

    public LiteralInterpeterTest shouldBe(LiteralBlock block, Class<?> clazz) {
        Assert.assertEquals(clazz, block.getClazz());
        return this;
    }


}
