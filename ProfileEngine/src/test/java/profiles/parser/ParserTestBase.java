package profiles.parser;

import io.github.jwdeveloper.spigot.tiktok.profiles.ast.Node;
import io.github.jwdeveloper.spigot.tiktok.profiles.parser.ParserFactory;
import io.github.jwdeveloper.spigot.tiktok.profiles.tokenizer.Tokenizer;
import io.github.jwdeveloper.spigot.tiktok.profiles.tokenizer.TokenizerFactory;

public class ParserTestBase {

    protected Node createProgramTree(String content) {
        var lexer = createLexer(content);
        return ParserFactory.createParser(lexer).parse();
    }

    protected Tokenizer createLexer(String content)
    {
        return TokenizerFactory.create(content);
    }
}
