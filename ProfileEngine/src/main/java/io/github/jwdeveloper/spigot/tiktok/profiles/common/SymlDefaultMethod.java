package io.github.jwdeveloper.spigot.tiktok.profiles.common;

import io.github.jwdeveloper.spigot.tiktok.profiles.common.definitions.SymlArray;
import io.github.jwdeveloper.spigot.tiktok.profiles.common.definitions.SymlMethod;

import java.util.Random;

public class SymlDefaultMethod
{

    @SymlMethod
    public int random()
    {
        return new Random().nextInt();
    }

    @SymlMethod
    public Object random(SymlArray array)
    {
        return array.random();
    }

    @SymlMethod
    public int random(int min, int max)
    {
        return new Random().nextInt(max)+min;
    }


    @SymlMethod
    public void print(String input)
    {
        System.out.println(input);
    }


    @SymlMethod
    public void wait(int seconds)
    {
        try
        {
            Thread.sleep(seconds);
        }
        catch (Exception e)
        {

        }
    }

}
