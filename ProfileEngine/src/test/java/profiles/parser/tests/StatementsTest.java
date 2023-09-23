package profiles.parser.tests;

import io.github.jwdeveloper.spigot.tiktok.profiles.ast.ProgramSYML;
import org.junit.Test;
import profiles.parser.AssertNode;
import profiles.parser.ParserTestBase;

public class StatementsTest extends ParserTestBase
{


    @Test
    public void shouldParseStatemetns()
    {
        var program =  createProgramTree("""
                
                name = "mark"
                age = 12
                isValid = true
                reference = age + 1
                
                
                
                """).print();
        AssertNode.assertion(program).isOfClass(ProgramSYML.class);
    }
}
