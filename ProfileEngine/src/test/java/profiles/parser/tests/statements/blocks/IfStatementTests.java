package profiles.parser.tests.statements.blocks;

import org.junit.Test;
import profiles.parser.ParserTestBase;

public class IfStatementTests extends ParserTestBase
{


    @Test
    public void simpleIf()
    {
        createProgramTree("if a is b").print();
    }

    @Test
    public void ifThen()
    {
        createProgramTree("if a is b then 1").print();
    }

    @Test
    public void ifThenElse()
    {
        createProgramTree("if a is b then 1 else 2").print();
    }
}
