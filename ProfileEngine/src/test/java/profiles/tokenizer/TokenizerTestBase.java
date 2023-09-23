package profiles.tokenizer;

import io.github.jwdeveloper.spigot.tiktok.profiles.tokenizer.Token;
import io.github.jwdeveloper.spigot.tiktok.profiles.tokenizer.Tokenizer;
import io.github.jwdeveloper.spigot.tiktok.profiles.tokenizer.TokenizerFactory;

import java.util.List;

public class TokenizerTestBase
{
    protected Tokenizer createTokenizer(String content)
    {
        return TokenizerFactory.create(content);
    }

    protected List<Token> createTokens(String content)
{
    var tokenizer = createTokenizer(content);
    return tokenizer.getTokens();
}

}
