package profiles.parser.tests.statements.blocks;

import org.junit.Test;
import profiles.parser.ParserTestBase;

public class SwitchStatementTests extends ParserTestBase
{
    @Test
    public void SwitchCase()
    {
        createProgramTree("""
                switch event
                case 1 then "hello"
                case 2 then "bye"
                """).print();
    }


    @Test
    public void SwitchCaseDefault()
    {
        createProgramTree("""
                switch event
                case 1 then "hello"
                case 2 then "bye"
                default then "world"
                """).print();
    }
}
