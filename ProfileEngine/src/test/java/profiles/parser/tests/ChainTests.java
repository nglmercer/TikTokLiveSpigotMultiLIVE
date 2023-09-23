package profiles.parser.tests;

import io.github.jwdeveloper.spigot.tiktok.profiles.ast.ProgramSYML;
import org.junit.Test;
import profiles.parser.AssertNode;
import profiles.parser.ParserTestBase;

public class ChainTests extends ParserTestBase
{
    @Test
    public void shouldParseChain()
    {
        var program =  createProgramTree("one.two.three").print();
        AssertNode.assertion(program).isOfClass(ProgramSYML.class);
    }

    @Test
    public void shouldParseChainWithMethod()
    {
        var program =  createProgramTree("one.three()").print();
        AssertNode.assertion(program).isOfClass(ProgramSYML.class);
    }

    @Test
    public void shouldParseChainWithMethodWithParams()
    {
        var program =  createProgramTree("one.three(1,2,3,'232')").print();
        AssertNode.assertion(program).isOfClass(ProgramSYML.class);
    }

    @Test
    public void shouldParseJustMethodCall()
    {
        var program =  createProgramTree("three(1,2,3,'232')").print();
        AssertNode.assertion(program).isOfClass(ProgramSYML.class);
    }
}

