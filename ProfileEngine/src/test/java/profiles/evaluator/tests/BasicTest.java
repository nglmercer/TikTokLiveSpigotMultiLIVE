package profiles.evaluator.tests;




import io.github.jwdeveloper.ff.core.files.FileUtility;
import io.github.jwdeveloper.ff.core.logger.plugin.FluentLogger;
import io.github.jwdeveloper.spigot.tiktok.profiles.parser.ParserFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import profiles.evaluator.EvaluatorTestBase;

import java.io.IOException;

public class BasicTest extends EvaluatorTestBase {


    @Test
    public void shouldParseCommand(){
        createEvaluatedProgram("/title @p title \"§2${event.sender.nickName}\"");
    }

    @Test
    public void generalTest() {
        createEvaluatedProgram("""
                
                """).print();
    }

    @Test
    public void shouldDeclareArray() {
        createEvaluatedProgram("""
                numbers = [1,2,3,4,3,2,3,4,2,1,2,3,4,3,3]
                repeat 5 /say ${index} ${random(numbers)}
                """).print();
    }

    @Test
    public void shouldHandleChain() {


        var evaluator = createEvaluator();
        evaluator.getContext().addVariable("example",new ExampleClass());
        evaluator.evaluate("""
                a = example.propert1
                b = example.propert1.length()
                c = example.number1
                d = example.method1("hello", "world")
                """);

        evaluator.print();
    }


    @Test
    public void binryExpressionTest() {
        var evaluator = createEvaluatedProgram("""
                
                result = "abc" contains "x"
                
                /say ${result}
                
                """).print();
    }

    @Test
    public void deleteTest() {
        var evaluator = createEvaluatedProgram("""
                str1 : "helllo"
                delete str1
                """).print();
    }

    @Test
    public void shouldMakeStrinkManipulation() {
        var evaluator = createEvaluatedProgram("""
                str1 : "helllo"
                str2 : " world"
                str3 =  str1 + str2 + " it works"
                /say ${str3}
                """).print();
    }

   @Test
    public void shouldMakeSimpleAssigment() {
        var evaluator = createEvaluatedProgram("""
                age : 1
                name = "my age is ${age}"
                isPlayer = false
                """).print();

        Assertions.assertEquals(3, evaluator.getContext().getVariables().size());
    }


    @Test
    public void shouldMakeIf() {
        FluentLogger.setLogger("asd");
        createEvaluatedProgram("""
                if 1 is 1  then /siema  else /elo
                """).print();
    }


    @Test
    public void shouldMakeSwitch() {
        createEvaluatedProgram("""
                                
                age = 1
                                
                switch age
                case 1 then /siema
                case 2 then /elo
                default then /not problem
                """).print();
    }


    @Test
    public void shouldMakeRepeat() {
        createEvaluatedProgram("""
                repeat 5 /this is command ${index}
                """).print();
    }


    @Test
    public void shouldInvokeMethod() {
        createEvaluatedProgram("""
                a = 12               
                number = random(1,10)
                """).print();
    }

    @Test
    public void shoudMakeout() {
        createEvaluatedProgram("""
                /say hello ${"this guy"} is ${1}
                """).print();
    }


    @Test
    public void shoudMakeout2()
    {
        var inti = ParserFactory.createParser("""
                 getName:  (input) =>  "string connected "+input;
                 getValue: () =>  2 ^ 2;
                """).parse();

       var eveluator = createEvaluator();

       eveluator.evaluate(inti);
       eveluator.evaluate("""
                /say  ${getName('siema')}
               """).print();

    }

    @Test
    public void siema() {
        createEvaluatedProgram("""
                repeat 5 /say ${2} ${index}
                """).print();
    }





}
