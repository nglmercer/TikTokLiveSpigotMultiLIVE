package profiles.tokenizer;

import io.github.jwdeveloper.spigot.tiktok.profiles.tokenizer.Token;

import java.util.List;

public class AssertTokenizer
{
    public static TokenizerAssertions asserts(List<Token> tokens)
    {
        return new TokenizerAssertions(tokens);
    }
}
