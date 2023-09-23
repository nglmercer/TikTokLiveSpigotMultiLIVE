package profiles.parser.tests;

import io.github.jwdeveloper.spigot.tiktok.profiles.ast.ProgramSYML;
import org.junit.Test;
import profiles.parser.AssertNode;
import profiles.parser.ParserTestBase;

public class AssigmentTests extends ParserTestBase
{

    @Test
    public void ShouldAssign()
    {
        var program =  createProgramTree("name : 1 is 1").print();
        AssertNode.assertion(program).isOfClass(ProgramSYML.class);
    }

    @Test
    public void ShouldAssign2()
    {
        var program =  createProgramTree("name = 1 == 1").print();
        AssertNode.assertion(program).isOfClass(ProgramSYML.class);
    }
}
