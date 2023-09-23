package profiles.parser.tests.statements;

import org.junit.Test;
import profiles.parser.ParserTestBase;

public class MethodDefinition extends ParserTestBase
{

    @Test
    public void definitionMetho2d()
    {
        createProgramTree("""
                name : 1
                """).print();
    }

    @Test
    public void definitionMethod()
    {
        createProgramTree("""
                name : (a,b,c) => /hello world
                """).print();
    }


    @Test
    public void definitionMethod2()
    {
        createProgramTree("""
                name : (a,b,c) => ${/hello world}
                """).print();
    }
}
