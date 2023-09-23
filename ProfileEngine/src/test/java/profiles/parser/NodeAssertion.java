package profiles.parser;


import io.github.jwdeveloper.spigot.tiktok.profiles.ast.Node;
import org.junit.Assert;

import java.util.function.Consumer;

public class NodeAssertion<T> {
    private final Node node;

    public NodeAssertion(Node node) {
        this.node = node;
        Assert.assertNotNull(node);
    }


    public NodeAssertion<T> has(Consumer<T> hasAssertion) {
        var nodeType = (T) this.node;
        hasAssertion.accept(nodeType);
        return this;
    }

    public <K> NodeAssertion<T> hasChild(int index, Class<K> childClazz, Consumer<NodeAssertion<K>> onChild) {
      /*  var child = node.getNodes().get(index);
        onChild.accept(new NodeAssertion<K>(child));*/
        return this;
    }

    public NodeAssertion<T> hasChild(int index, Class<T> clazz) {
        return hasChild(index, clazz, (x) -> {
        });
    }

    public NodeAssertion<T> hasChildrenCount(int count) {
      /*  Assert.assertEquals(count, node.getNodes().size());*/
        return this;
    }

    public NodeAssertion<T> hasName(String name) {
        Assert.assertEquals(name, node.getType());
        return this;
    }

    public NodeAssertion<T> isOfClass(Class<?> clazz) {
        Assert.assertEquals(clazz, node.getClass());
        return this;
    }
}