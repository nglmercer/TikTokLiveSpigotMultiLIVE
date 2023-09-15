package profiles.tokenizer;

import io.github.jwdeveloper.spigot.tiktok.profiles.interpreter.Tokenizer;
import org.junit.Test;

public class TokenazierTest {
    @Test
    public void run() {

        var input = """
                if name is adam and age is 12
                        then "/say true ${'event.text'}"
                        else "/say false"
                """;
        var tokenizer = new Tokenizer(input);

        var x =0;
    }
}
