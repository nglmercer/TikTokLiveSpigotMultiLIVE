package profiles.parser.tests.literals;

import org.junit.Test;
import profiles.parser.ParserTestBase;

public class LiteralTests  extends ParserTestBase
{
    @Test
    public void stringTest()
    {
        createProgramTree("""
                "hello ${variable} world ${'nested'}"
                """).print();


    }
}
