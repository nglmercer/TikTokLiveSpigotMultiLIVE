package io.github.jwdeveloper.spigot.tiktok.profiles.common.definitions;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SymlArray
{
    public int size;
    private final List<Object> values;

    public SymlArray(List<Object> values) {
        this.values = values;
        size = values.size();
    }

    public void add(Object o)
    {
        values.add(o);
        size = values.size();
    }

    public Object get(int index)
    {
        return values.get(index);
    }

    public Object random()
    {
        var index = new Random().nextInt(values.size()-1);
        return values.get(index);
    }

    public void remove(Object o)
    {
        values.remove(o);
        size = values.size();
    }

    public boolean contains(Object o)
    {
        return values.contains(o);
    }


    @Override
    public String toString() {
        var sb = new StringBuilder();
        sb.append("[");
        for(var value : values)
        {
            sb.append(value).append(",");
        }
        sb.append("]");
        return sb.toString();
    }
}
