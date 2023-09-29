package profiles.assertions;


import io.github.jwdeveloper.spigot.tiktok.profiles.ast.Node;
import io.github.jwdeveloper.spigot.tiktok.profiles.ast.ProgramSYML;
import org.junit.Assert;

import java.util.function.Consumer;

public class NodeAssertion<T> {

    public static <T> NodeAssertion<T> assertion(Node node)
    {
        return new NodeAssertion<T>(node);
    }
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

    public <K> NodeAssertion<T> hasChild(int index, Class<K> childClazz, Consumer<NodeAssertion<K>> onChild)
    {
        if(node instanceof ProgramSYML program)
        {
            var child = program.getBody().get(index);
            onChild.accept(new NodeAssertion<K>(child));
        }
        return this;
    }

    public NodeAssertion<T> hasChild(int index, Class<T> clazz) {
        return hasChild(index, clazz, (x) -> {
        });
    }

    public NodeAssertion<T> hasChildrenCount(int count) {
        if(node instanceof ProgramSYML program)
        {
            Assert.assertEquals(count, program.getBody().size());
        }
        return this;
    }

    public NodeAssertion<T> hasChildrenCount(int count, Class<?> type)
    {
        if(node instanceof ProgramSYML program)
        {
            var result = program.getBody().stream().filter(e -> e.getClass().equals(type)).toList();
            Assert.assertEquals(count,result.size());
        }
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