package io.github.jwdeveloper.spigot.tiktok.profiles.ast;

import com.google.gson.GsonBuilder;

public abstract class Node implements CodeExecutor
{
    protected String type = getClass().getSimpleName();

    public String toJson() {
        var gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();
        return gson.toJson(this);
    }

    public Node print()
    {
        System.out.println(toJson());
        return this;
    }

    public String getType() {
        return type;
    }



}
