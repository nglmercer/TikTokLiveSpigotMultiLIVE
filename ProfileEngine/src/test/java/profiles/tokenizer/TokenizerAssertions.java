package profiles.tokenizer;

import io.github.jwdeveloper.spigot.tiktok.profiles.tokenizer.Token;
import io.github.jwdeveloper.spigot.tiktok.profiles.tokenizer.TokenType;
import org.junit.Assert;

import java.util.List;

public class TokenizerAssertions {
    private final List<Token> tokens;

    public TokenizerAssertions(List<Token> tokens) {
        this.tokens = tokens;
    }

    public TokenizerAssertions hasToken(TokenType tokenType, int count) {
        if (count == 0) {
            count = 1;
        }

        var token = tokens.stream().filter(e -> e.getTokenType().equals(tokenType)).toList();
        Assert.assertEquals(token.size(), count);
        return this;
    }

    public TokenizerAssertions hasToken(TokenType tokenType, String value, int index) {
        var token = tokens.stream().filter(e -> e.getTokenType().equals(tokenType) && e.getValue().equals(value)).findFirst().get();
        Assert.assertNotNull(token);
        if (index != -1) {
            Assert.assertEquals(tokens.get(index), token);
        }

        return this;
    }
}
