package profiles.tokenizer.tests;

import org.junit.Test;
import profiles.tokenizer.TokenizerTestBase;

public class LiteralsTests extends TokenizerTestBase
{

    @Test
    public void numbersTet()
    {
        createTokenizer("""
                 1,2,412321,132,13123
                """).print();
    }


    @Test
    public void tokenizerTest()
    {
        createTokenizer("""
                "hello ${variable} world ${'nested'}"
                """).print();
    }

    @Test
    public void run()
    {
        createTokenizer("one.two.three  1.2, 23, 23 'siema' ${block  \"siema\" /dupa} /comment ${siema}").print();
    }

    @Test
    public void  multiLineTest()
    {
        createTokenizer("""
                1 
                dupa
                """).print();;
    }


    @Test
    public void  mutliLineAdvanced()
    {
        createTokenizer("""
                if a == b 
                then /say "hello ${world}" /
                else "hello ${a}"
                """).print();;
    }

    @Test
    public void methodTest()
    {
        createTokenizer("one.three(1,2,3,'232')").print();;
    }
}
