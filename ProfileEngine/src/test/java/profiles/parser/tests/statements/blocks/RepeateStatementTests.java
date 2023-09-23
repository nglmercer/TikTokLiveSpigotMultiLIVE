package profiles.parser.tests.statements.blocks;

import org.junit.Test;
import profiles.parser.ParserTestBase;

public class RepeateStatementTests  extends ParserTestBase
{
    @Test
    public void RepeateTest()
    {
        createProgramTree("""
                repeat 3 /hello world
                """).print();
    }

}
