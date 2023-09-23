package profiles.parser;

import io.github.jwdeveloper.spigot.tiktok.profiles.ast.Node;

public class AssertNode
{
    public static <T> NodeAssertion<T> assertion(Node node)
    {
        return new NodeAssertion<T>(node);
    }
}
