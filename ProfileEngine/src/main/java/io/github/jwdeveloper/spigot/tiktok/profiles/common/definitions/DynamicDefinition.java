package io.github.jwdeveloper.spigot.tiktok.profiles.common.definitions;

import io.github.jwdeveloper.ff.core.common.TextBuilder;

import java.util.HashMap;
import java.util.Map;

public class DynamicDefinition {
    private final Map<String, Object> properties;
    private DynamicDefinition parent;
    private Object value = VariableDefinition.NULL;

    public DynamicDefinition(DynamicDefinition parent) {
        this.parent = parent;
        this.properties = new HashMap<>();
    }

    public DynamicDefinition() {
        this.properties = new HashMap<>();
    }

    public void set(String variable, Object value) {
        properties.put(variable, value);
    }

    public Object get(String variable) {
        if (!has(variable)) {
            set(variable, new DynamicDefinition(this));
        }

        return properties.get(variable);
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public boolean has(String variable) {
        return properties.containsKey(variable);
    }


    @Override
    public String toString() {
        var sb = new TextBuilder<>();
        sb.text("{ ");
        if (!value.equals(VariableDefinition.NULL)) {
            sb.text("value: ", value.toString());

        }
        if (properties.size() != 0) {

            sb.text("props: { ");
            for(var prop : properties.entrySet())
            {
                sb.text(prop.getKey(),": ",prop.getValue().toString());
            }
            sb.text("}");
        }
        sb.text("}");
        return sb.toString();
    }
}
