package io.github.jwdeveloper.spigot.tiktok.core.profile_editor_old;

import io.github.jwdeveloper.spigot.tiktok.profiles.common.definitions.SymlMethod;

import java.util.Random;

public class DefaultMethods
{
    @SymlMethod
    public int random(int min, int max)
    {
        var random = new Random();
        return random.nextInt(min) + max;
    }

    /*
    @SymlMethod
    public String spawn(String entityName)
    {
        var sb = new StringBuilder();
        sb.append("/execute at @p run summon minecraft:");
        sb.append(entityName);
        sb.append(" ~0 ~1 ~0");
        return sb.toString();
    }*/

    @SymlMethod
    public String heal()
    {
        return "/execute at @p run particle minecraft:heart ~1 ~1 ~";
    }

    @SymlMethod
    public String give(String itemName)
    {
        return "/give @p minecraft:"+itemName;
    }

    @SymlMethod
    public String drop(String itemName)
    {
        return "/execute as @p at @p run summon item ~1 ~ ~ {Item:{id:\"minecraft:"+itemName+"\",Count:1b}}";
    }

    @SymlMethod
    public String title(String title)
    {
        var sb = new StringBuilder();
        sb.append("/title @p title \"").append(title).append("\"");
        return sb.toString();
    }


    @SymlMethod
    public String clearEffect()
    {
        return "/effect clear @p";
    }

    @SymlMethod
    public String effect(String effectName)
    {
        var sb = new StringBuilder();
        sb.append("effect give @p minecraft:");
        sb.append(effectName);
        sb.append(" infinite ");
        return sb.toString();
    }

    @SymlMethod
    public String effect(String effectName, int a, int b)
    {
        var sb = new StringBuilder();
        sb.append("effect give @p minecraft:");
        sb.append(effectName);
        sb.append(" ");
        sb.append(a);
        sb.append(" ");
        sb.append(b);
        return sb.toString();
    }

    @SymlMethod
    public String particle(String name)
    {
        var sb = new StringBuilder();
        sb.append("/execute at @p run particle minecraft:");
        sb.append(name);
        sb.append(" ~1 ~1 ~1 ");
        return sb.toString();
    }

    @SymlMethod
    public String particle(String name, int x, int y, int z)
    {
        var sb = new StringBuilder();
        sb.append("/execute at @p run particle minecraft:");
        sb.append(name);
        sb.append(" ~").append(x).append(" ");
        sb.append(" ~").append(y).append(" ");
        sb.append(" ~").append(z).append(" ");
        return sb.toString();
    }


    public String sound(String name)
    {
        var sb = new StringBuilder();
        sb.append("/playsound minecraft:");
        sb.append(name);
        sb.append(" master @p ~ ~ ~ 1 1 1");
        return sb.toString();
    }

    public String sound(String name, String source)
    {
        var sb = new StringBuilder();
        sb.append("/playsound ");
        sb.append(source).append(":");
        sb.append(name);
        sb.append(" master @p ~ ~ ~ 1 1 1");
        return sb.toString();
    }

}
