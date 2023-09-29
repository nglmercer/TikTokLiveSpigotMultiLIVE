package profiles.assertions;

import io.github.jwdeveloper.spigot.tiktok.profiles.tokenizer.TokenType;
import io.github.jwdeveloper.spigot.tiktok.profiles.tokenizer.Tokenizer;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;

public class TokenizerAssert {
    public static TokenizerAssert assertion(Tokenizer tokenizer) {
        return new TokenizerAssert(tokenizer);
    }

    private final Tokenizer tokenizer;

    public TokenizerAssert(Tokenizer tokenizer) {
        this.tokenizer = tokenizer;
    }


    public TokenizerAssert hasToken(TokenType tokenType) {
        var token = tokenizer.getTokens().stream().filter(e -> e.getTokenType() == tokenType).findFirst();
        Assertions.assertTrue(token.isPresent());
        return this;
    }

    public TokenizerAssert hasToken(TokenType tokenType, String value, int index) {
        var token = tokenizer.getTokens().stream().filter(e -> e.getTokenType().equals(tokenType) && e.getValue().equals(value)).findFirst().get();
        Assert.assertNotNull(token);
        if (index != -1) {
            Assert.assertEquals(tokenizer.getTokens().get(index), token);
        }

        return this;
    }

    public TokenizerAssert hasNoToken(TokenType tokenType) {
        var token = tokenizer.getTokens().stream().filter(e -> e.getTokenType() == tokenType).findFirst();
        Assertions.assertFalse(token.isPresent());
        return this;
    }

    public TokenizerAssert hasToken(TokenType tokenType, int index) {

        var tokens = tokenizer.getTokens();
        var token = tokens.get(index);
        Assertions.assertEquals(tokenType, token.getTokenType());
        return this;
    }

    public TokenizerAssert hasTokenCount(int count) {
        Assertions.assertEquals(count, tokenizer.getTokens().size());
        return this;
    }

    public TokenizerAssert hasTokenCount(int count, TokenType tokenType)
    {
        var tokens = tokenizer.getTokens().stream().filter(e -> e.getTokenType() == tokenType).toList();
        Assertions.assertEquals(count, tokens.size());
        return this;
    }
}
