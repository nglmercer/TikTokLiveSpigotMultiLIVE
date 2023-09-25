package profiles.tokenizer.tests;

import io.github.jwdeveloper.spigot.tiktok.profiles.common.StringCodeBlockParser;
import org.junit.Assert;
import org.junit.Test;
import profiles.tokenizer.TokenizerTestBase;

public class StringsTests extends TokenizerTestBase
{
    @Test
    public void numbersTet()
    {
        var input = """
                "{{{${m}}}}"
                """;
        var codeBlockParser = new StringCodeBlockParser();

        var result = codeBlockParser.findCodeBlock(input);

        var a = result.getMarkContent();


        Assert.assertEquals("{{{%s}}}",a);
    }


    @Test
    public void test232()
    {
        var inpuit = """
                 /execute at @p run summon minecraft:${mobType} ~ ~ ~ {CustomName:'"${mobName}"',CustomNameVisible:1}
                """;
        var codeBlockParser = new StringCodeBlockParser();
        var result = codeBlockParser.findCodeBlock(inpuit);
        var ab = result.getMarkContent();
        var i =0;
    }
}
